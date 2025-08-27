package nz.duncy.first_steps.client.gui.screen.ingame;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CyclingSlotIcon;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.item.custom.MannequinItem;
import nz.duncy.first_steps.screen.MannequinScreenHandler;

@Environment(EnvType.CLIENT)
public class MannequinScreen extends HandledScreen<MannequinScreenHandler> implements ScreenHandlerListener {
	private final static Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/gui/container/mannequin.png");

	private final static int BASE_SLOT_INDEX = 4;

	private static final Identifier EMPTY_SLOT_SMITHING_TEMPLATE_ARMOR_TRIM_TEXTURE = Identifier.ofVanilla("container/slot/smithing_template_armor_trim");
	private static final Identifier EMPTY_SLOT_SMITHING_TEMPLATE_NETHERITE_UPGRADE_TEXTURE = Identifier.ofVanilla(
		"container/slot/smithing_template_netherite_upgrade"
	);

	private static final List<Identifier> EMPTY_SLOT_TEXTURES = List.of(
		EMPTY_SLOT_SMITHING_TEMPLATE_ARMOR_TRIM_TEXTURE, EMPTY_SLOT_SMITHING_TEMPLATE_NETHERITE_UPGRADE_TEXTURE
	);

	private static final Text MISSING_TEMPLATE_TOOLTIP = Text.translatable(FirstSteps.MOD_ID + ".container.mannequin.missing_armor");

	private final CyclingSlotIcon SlotIcon0 = new CyclingSlotIcon(0);
	private final CyclingSlotIcon SlotIcon1 = new CyclingSlotIcon(1);
	private final CyclingSlotIcon SlotIcon2 = new CyclingSlotIcon(2);
	private final CyclingSlotIcon SlotIcon3 = new CyclingSlotIcon(3);
	private final CyclingSlotIcon SlotIcon4 = new CyclingSlotIcon(BASE_SLOT_INDEX);
	private final CyclingSlotIcon SlotIcon5 = new CyclingSlotIcon(5);
	private final CyclingSlotIcon SlotIcon6 = new CyclingSlotIcon(6);
	private final CyclingSlotIcon SlotIcon7 = new CyclingSlotIcon(7);
	private final CyclingSlotIcon SlotIcon8 = new CyclingSlotIcon(8);

	private static final Quaternionf ARMOR_STAND_ROTATION = new Quaternionf().rotationXYZ(0.43633232F, 0.0F, (float) Math.PI);
	private static final Vector3f ARMOR_STAND_VECTOR = new Vector3f();
	@Nullable
	private ArmorStandEntity armorStand;


	public MannequinScreen(MannequinScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	protected void setup() {
		this.armorStand = new ArmorStandEntity(this.client.world, 0.0, 0.0, 0.0);
		this.armorStand.setHideBasePlate(true);
		this.armorStand.setShowArms(true);
		this.armorStand.bodyYaw = 210.0F;
		this.armorStand.setPitch(25.0F);
		this.armorStand.headYaw = this.armorStand.getYaw();
		this.armorStand.prevHeadYaw = this.armorStand.getYaw();
		this.equipArmorStand(this.handler.getSlot(3).getStack());
	}

	@Override
	protected void init() {
		super.init();
		this.setup();
		this.handler.addListener(this);
	}

	@Override
	public void removed() {
		super.removed();
		this.handler.removeListener(this);
	}
	
	@Override
	public void handledScreenTick() {
		super.handledScreenTick();
		Optional<MannequinItem> optional = this.getSmithingTemplate();
		this.SlotIcon4.updateTexture(EMPTY_SLOT_TEXTURES);
		this.SlotIcon1.updateTexture((List<Identifier>)optional.map(MannequinItem::getEmptyBaseSlotTextures).orElse(List.of()));
		this.SlotIcon3.updateTexture((List<Identifier>)optional.map(MannequinItem::getEmptyAdditionsSlotTextures).orElse(List.of()));
	}

	private Optional<MannequinItem> getSmithingTemplate() {
		ItemStack itemStack = this.handler.getSlot(BASE_SLOT_INDEX).getStack();
		return !itemStack.isEmpty() && itemStack.getItem() instanceof nz.duncy.first_steps.item.custom.MannequinItem mannequinItem
			? Optional.of(mannequinItem)
			: Optional.empty();
	}

	@Override
	public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
		if (slotId == BASE_SLOT_INDEX) {
			this.equipArmorStand(stack);
		}
	}

	private void equipArmorStand(ItemStack stack) {
		if (this.armorStand != null) {
			for (EquipmentSlot equipmentSlot : EquipmentSlot.VALUES) {
				this.armorStand.equipStack(equipmentSlot, ItemStack.EMPTY);
			}

			if (!stack.isEmpty()) {
				EquippableComponent equippableComponent = stack.get(DataComponentTypes.EQUIPPABLE);
				EquipmentSlot equipmentSlot = equippableComponent != null ? equippableComponent.slot() : EquipmentSlot.OFFHAND;
				this.armorStand.equipStack(equipmentSlot, stack.copy());
			}
		}
	}

	private void renderSlotTooltip(DrawContext context, int mouseX, int mouseY) {
		Optional<Text> optional = Optional.empty();
		if (this.focusedSlot != null) {
			ItemStack itemStack = this.handler.getSlot(BASE_SLOT_INDEX).getStack();
			ItemStack itemStack2 = this.focusedSlot.getStack();
			if (itemStack.isEmpty()) {
				if (this.focusedSlot.id == BASE_SLOT_INDEX) {
					optional = Optional.of(MISSING_TEMPLATE_TOOLTIP);
				}
			} else if (itemStack.getItem() instanceof nz.duncy.first_steps.item.custom.MannequinItem mannequinItem && itemStack2.isEmpty()) {
				if (this.focusedSlot.id == 1) {
					optional = Optional.of(mannequinItem.getBaseSlotDescription());
				} else if (this.focusedSlot.id == 2) {
					optional = Optional.of(mannequinItem.getAdditionsSlotDescription());
				}
			}
		}

		optional.ifPresent(text -> context.drawOrderedTooltip(this.textRenderer, this.textRenderer.wrapLines(text, 115), mouseX, mouseY));
	}

	@Override
	public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		this.renderForeground(context, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(context, mouseX, mouseY);
		this.renderSlotTooltip(context, mouseX, mouseY);
	}

	protected void renderForeground(DrawContext context, int mouseX, int mouseY, float delta) {
	}

	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
		context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, this.x, this.y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);
		this.SlotIcon0.render(this.handler, context, delta, this.x, this.y);
		this.SlotIcon2.render(this.handler, context, delta, this.x, this.y);
		this.SlotIcon3.render(this.handler, context, delta, this.x, this.y);
		this.SlotIcon4.render(this.handler, context, delta, this.x, this.y);
		this.SlotIcon5.render(this.handler, context, delta, this.x, this.y);
		this.SlotIcon6.render(this.handler, context, delta, this.x, this.y);
		this.SlotIcon7.render(this.handler, context, delta, this.x, this.y);
		this.SlotIcon8.render(this.handler, context, delta, this.x, this.y);

		InventoryScreen.drawEntity(context, (float)(this.x + 141), (float)(this.y + 70), 25.0F, ARMOR_STAND_VECTOR, ARMOR_STAND_ROTATION, null, this.armorStand);
	}
}

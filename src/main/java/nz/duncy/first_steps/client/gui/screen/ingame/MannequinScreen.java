package nz.duncy.first_steps.client.gui.screen.ingame;

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
import nz.duncy.first_steps.item.custom.ModularArmorItem;
import nz.duncy.first_steps.screen.MannequinScreenHandler;
import nz.duncy.first_steps.screen.slot.MannequinSlot;

@Environment(EnvType.CLIENT)
public class MannequinScreen extends HandledScreen<MannequinScreenHandler> implements ScreenHandlerListener {
	private final static Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/gui/container/mannequin.png");

	private static final Text MISSING_ARMOR_TOOLTIP = Text.translatable("container." + FirstSteps.MOD_ID + ".mannequin.missing_armor");
	private static final Text MISSING_SHOULDER_TOOLTIP = Text.translatable("container." + FirstSteps.MOD_ID + ".mannequin.missing_shoulder");
	private static final Text MISSING_HAND_TOOLTIP = Text.translatable("container." + FirstSteps.MOD_ID + ".mannequin.missing_hand");
	private static final Text MISSING_MID_LAYER_TOOLTIP = Text.translatable("container." + FirstSteps.MOD_ID + ".mannequin.missing_mid_layer");
	private static final Text MISSING_TOP_LAYER_TOOLTIP = Text.translatable("container." + FirstSteps.MOD_ID + ".mannequin.missing_top_layer");

	private final CyclingSlotIcon BaseSlotIcon = new CyclingSlotIcon(MannequinSlot.BASE_LAYER_SLOT_INDEX);

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
		this.equipArmorStand(this.handler.getSlot(MannequinSlot.BASE_LAYER_SLOT_INDEX).getStack());
	}

	@Override
	protected void init() {
		super.init();
		this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
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
		this.BaseSlotIcon.updateTexture(ModularArmorItem.getBaseArmorEmptyBaseSlotTextures());
	}

	@Override
	public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
		if (slotId == MannequinSlot.BASE_LAYER_SLOT_INDEX) {
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
			// ItemStack itemStack = this.handler.getSlot(MannequinSlot.BASE_LAYER_SLOT_INDEX).getStack();
			ItemStack itemStack2 = this.focusedSlot.getStack();
			if (itemStack2.isEmpty()) {
				switch (this.focusedSlot.id) {
					case MannequinSlot.LEFT_SHOULDER_SLOT_INDEX -> {
						optional = Optional.of(MISSING_SHOULDER_TOOLTIP);
						break;
					}
					case MannequinSlot.TOP_LAYER_SLOT_INDEX -> {
						optional = Optional.of(MISSING_TOP_LAYER_TOOLTIP);
						break;
					}
					case MannequinSlot.RIGHT_SHOULDER_SLOT_INDEX -> {
						optional = Optional.of(MISSING_SHOULDER_TOOLTIP);
						break;
					}
					case MannequinSlot.LEFT_HAND_SLOT_INDEX -> {
						optional = Optional.of(MISSING_HAND_TOOLTIP);
						break;
					}
					case MannequinSlot.BASE_LAYER_SLOT_INDEX -> {
						optional = Optional.of(MISSING_ARMOR_TOOLTIP);
						break;
					}
					case MannequinSlot.RIGHT_HAND_SLOT_INDEX -> {
						optional = Optional.of(MISSING_HAND_TOOLTIP);
						break;
					}
					case MannequinSlot.MID_LAYER_SLOT_INDEX -> {
						optional = Optional.of(MISSING_MID_LAYER_TOOLTIP);
						break;
					}
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
		this.BaseSlotIcon.render(this.handler, context, delta, this.x, this.y);

		InventoryScreen.drawEntity(context, (float)(this.x + 141), (float)(this.y + 66), 25.0F, ARMOR_STAND_VECTOR, ARMOR_STAND_ROTATION, null, this.armorStand);
	}
}

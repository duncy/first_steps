package nz.duncy.first_steps.item.custom;

import java.util.List;
import java.util.Map;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class ModularArmorItem extends Item {
	private static final Formatting TITLE_FORMATTING = Formatting.GRAY;

	private static final Text INGREDIENTS_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.ofVanilla("smithing_template.ingredients")))
		.formatted(TITLE_FORMATTING);
	private static final Text APPLIES_TO_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.ofVanilla("smithing_template.applies_to")))
		.formatted(TITLE_FORMATTING);
	private static final Text SMITHING_TEMPLATE_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.ofVanilla("smithing_template")))
		.formatted(TITLE_FORMATTING);

    public static final Identifier EMPTY_HELMET_SLOT_TEXTURE = Identifier.ofVanilla("container/slot/helmet");
	public static final Identifier EMPTY_CHESTPLATE_SLOT_TEXTURE = Identifier.ofVanilla("container/slot/chestplate");
	public static final Identifier EMPTY_LEGGINGS_SLOT_TEXTURE = Identifier.ofVanilla("container/slot/leggings");
	public static final Identifier EMPTY_BOOTS_SLOT_TEXTURE = Identifier.ofVanilla("container/slot/boots");
	public static final Identifier EMPTY_OFF_HAND_SLOT_TEXTURE = Identifier.ofVanilla("container/slot/shield");

	private static final Map<EquipmentSlot, Identifier> EMPTY_ARMOR_SLOT_TEXTURES = Map.of(
		EquipmentSlot.FEET,
		EMPTY_BOOTS_SLOT_TEXTURE,
		EquipmentSlot.LEGS,
		EMPTY_LEGGINGS_SLOT_TEXTURE,
		EquipmentSlot.CHEST,
		EMPTY_CHESTPLATE_SLOT_TEXTURE,
		EquipmentSlot.HEAD,
		EMPTY_HELMET_SLOT_TEXTURE
	);
	private static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER = new EquipmentSlot[]{
		EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
	};

	private final Text appliesToText;
	private final Text ingredientsText;
	private final Text baseSlotDescriptionText;
	private final Text additionsSlotDescriptionText;
	private final List<Identifier> emptyBaseSlotTextures;
	private final List<Identifier> emptyAdditionsSlotTextures;

	public ModularArmorItem(
		Text appliesToText,
		Text ingredientsText,
		Text baseSlotDescriptionText,
		Text additionsSlotDescriptionText,
		List<Identifier> emptyBaseSlotTextures,
		List<Identifier> emptyAdditionsSlotTextures,
		Item.Settings settings
	) {
		super(settings);
		this.appliesToText = appliesToText;
		this.ingredientsText = ingredientsText;
		this.baseSlotDescriptionText = baseSlotDescriptionText;
		this.additionsSlotDescriptionText = additionsSlotDescriptionText;
		this.emptyBaseSlotTextures = emptyBaseSlotTextures;
		this.emptyAdditionsSlotTextures = emptyAdditionsSlotTextures;
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(SMITHING_TEMPLATE_TEXT);
		tooltip.add(ScreenTexts.EMPTY);
		tooltip.add(APPLIES_TO_TEXT);
		tooltip.add(ScreenTexts.space().append(this.appliesToText));
		tooltip.add(INGREDIENTS_TEXT);
		tooltip.add(ScreenTexts.space().append(this.ingredientsText));
	}

	public Text getBaseSlotDescription() {
		return this.baseSlotDescriptionText;
	}

	public Text getAdditionsSlotDescription() {
		return this.additionsSlotDescriptionText;
	}

	public List<Identifier> getEmptyBaseSlotTextures() {
		return this.emptyBaseSlotTextures;
	}

	public List<Identifier> getEmptyAdditionsSlotTextures() {
		return this.emptyAdditionsSlotTextures;
	}

    public static List<Identifier> getBaseArmorEmptyBaseSlotTextures() {
		return List.of(
            EMPTY_HELMET_SLOT_TEXTURE,
            EMPTY_CHESTPLATE_SLOT_TEXTURE,
            EMPTY_LEGGINGS_SLOT_TEXTURE,
            EMPTY_BOOTS_SLOT_TEXTURE
		);
	}
}

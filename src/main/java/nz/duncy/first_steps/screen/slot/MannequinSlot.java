package nz.duncy.first_steps.screen.slot;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class MannequinSlot extends Slot {
	@Nullable
	private final Identifier backgroundSprite;

	public final static int LEFT_SHOULDER_SLOT_INDEX = 0;
	public final static int TOP_LAYER_SLOT_INDEX = 1;
	public final static int RIGHT_SHOULDER_SLOT_INDEX = 2;
	public final static int LEFT_HAND_SLOT_INDEX = 3;
	public final static int BASE_LAYER_SLOT_INDEX = 4;
	public final static int RIGHT_HAND_SLOT_INDEX = 5;
	public final static int MID_LAYER_SLOT_INDEX = 6;

	private boolean enabled = false;
	private final TagKey<Item> expectedTagKey;

	public MannequinSlot(Inventory inventory, int index, int x, int y, TagKey<Item> expectedTagKey, @Nullable Identifier backgroundSprite) {
		super(inventory, index, x, y);
		this.backgroundSprite = backgroundSprite;
		this.expectedTagKey = expectedTagKey;
	}

	@Override
	public int getMaxItemCount() {
		return 1;
	}

	@Override
	public boolean canTakeItems(PlayerEntity playerEntity) {
		ItemStack itemStack = this.getStack();
		return !itemStack.isEmpty()
				&& !playerEntity.isCreative()
				&& EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, EnchantmentEffectComponentTypes.PREVENT_ARMOR_CHANGE)
			? false
			: super.canTakeItems(playerEntity);
	}

	@Override
	public boolean canInsert(ItemStack stack) {
		return isEnabled() && stack.isIn(this.expectedTagKey);
	}

	@Nullable
	@Override
	public Identifier getBackgroundSprite() {
		return this.backgroundSprite;
	}

	public void setEnabled() {
		this.enabled = true;
	}

	public void setDisabled() {
		this.enabled = false;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}

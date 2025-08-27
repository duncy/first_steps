package nz.duncy.first_steps.screen.slot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.item.ItemStack;

public class MannequinSlotsManager {
    private final List<MannequinSlotsManager.MannequinSlot> inputSlots;

	MannequinSlotsManager(List<MannequinSlotsManager.MannequinSlot> inputSlots) {
		this.inputSlots = inputSlots;
	}

	public static MannequinSlotsManager.Builder builder() {
		return new MannequinSlotsManager.Builder();
	}

	public MannequinSlotsManager.MannequinSlot getInputSlot(int index) {
		return (MannequinSlotsManager.MannequinSlot)this.inputSlots.get(index);
	}

	public List<MannequinSlotsManager.MannequinSlot> getInputSlots() {
		return this.inputSlots;
	}

	public int getInputSlotCount() {
		return this.inputSlots.size();
	}

	public static class Builder {
		private final List<MannequinSlotsManager.MannequinSlot> inputs = new ArrayList();

		public MannequinSlotsManager.Builder input(int slotId, int x, int y, Predicate<ItemStack> mayPlace) {
			this.inputs.add(new MannequinSlotsManager.MannequinSlot(slotId, x, y, mayPlace));
			return this;
		}

		public MannequinSlotsManager build() {
			int i = this.inputs.size();

			for (int j = 0; j < i; j++) {
				MannequinSlotsManager.MannequinSlot mannequinSlot = (MannequinSlotsManager.MannequinSlot)this.inputs.get(j);
				if (mannequinSlot.slotId != j) {
					throw new IllegalArgumentException("Expected input slots to have continous indexes");
				}
			}

			return new MannequinSlotsManager(this.inputs);
		}
	}

	public static record MannequinSlot(int slotId, int x, int y, Predicate<ItemStack> mayPlace) {
		static final MannequinSlotsManager.MannequinSlot DEFAULT = new MannequinSlotsManager.MannequinSlot(0, 0, 0, stack -> true);
	}
}

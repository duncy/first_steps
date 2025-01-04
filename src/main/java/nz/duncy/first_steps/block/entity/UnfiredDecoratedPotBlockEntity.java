package nz.duncy.first_steps.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.Sherds;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.entity.DecoratedPotBlockEntity.WobbleType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

public class UnfiredDecoratedPotBlockEntity extends BlockEntity {
   public static final String SHERDS_NBT_KEY = "sherds";
   public static final String ITEM_NBT_KEY = "item";
   public static final int field_46660 = 1;
   public long lastWobbleTime;
   @Nullable
   public WobbleType lastWobbleType;
   private Sherds sherds;
   @Nullable
   protected Identifier lootTableId;
   protected long lootTableSeed;

   public UnfiredDecoratedPotBlockEntity(BlockPos pos, BlockState state) {
      super(ModBlockEntities.UNFIRED_DECORATED_POT_BLOCK_ENTITY, pos, state);
      this.sherds = Sherds.DEFAULT;
   }

   protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
      super.writeNbt(nbt, registryLookup);
      this.sherds.toNbt(nbt);

   }

   public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
      super.readNbt(nbt, registryLookup);
      this.sherds = Sherds.fromNbt(nbt);

   }

   public BlockEntityUpdateS2CPacket toUpdatePacket() {
      return BlockEntityUpdateS2CPacket.create(this);
   }

   public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
      return this.createNbt(registryLookup);
   }

   public Direction getHorizontalFacing() {
      return (Direction)this.getCachedState().get(Properties.HORIZONTAL_FACING);
   }

   public Sherds getSherds() {
      return this.sherds;
   }

   public void setSherd(ItemStack stack, int index, PlayerEntity player) {
      FirstSteps.LOGGER.info(String.valueOf(stack) + " " + String.valueOf(index));
      switch (index) {
         case 0: // north
            this.sherds = new Sherds(this.sherds.back(), this.sherds.left(), this.sherds.right(), Optional.of(stack.getItem()));
            break;
      
         case 1: // east
            this.sherds = new Sherds(this.sherds.back(), Optional.of(stack.getItem()), this.sherds.right(), this.sherds.front());
            break;
      
         case 2: // south
            this.sherds = new Sherds(Optional.of(stack.getItem()), this.sherds.left(), this.sherds.right(), this.sherds.front());
            break;
      
         case 3: // west
            this.sherds = new Sherds(this.sherds.back(), this.sherds.left(), Optional.of(stack.getItem()), this.sherds.front());
            break;
      
         default:
            break;
      }

      if (!player.isCreative()) {
         stack.decrement(1);
      }
   }

   public void readFrom(ItemStack stack) {
		this.readComponents(stack);
	}

   public ItemStack asStack() {
      ItemStack itemStack = ModBlocks.UNFIRED_DECORATED_POT.asItem().getDefaultStack();
		itemStack.applyComponentsFrom(this.createComponentMap());
		return itemStack;
   }

   public static ItemStack getStackWith(Sherds sherds) {
      ItemStack itemStack = ModBlocks.UNFIRED_DECORATED_POT.asItem().getDefaultStack();
		itemStack.set(DataComponentTypes.POT_DECORATIONS, sherds);
		return itemStack;
   }


   @Override
	protected void addComponents(ComponentMap.Builder componentMapBuilder) {
		super.addComponents(componentMapBuilder);
		componentMapBuilder.add(DataComponentTypes.POT_DECORATIONS, this.sherds);
	}

	@Override
	protected void readComponents(BlockEntity.ComponentsAccess components) {
		super.readComponents(components);
		this.sherds = components.getOrDefault(DataComponentTypes.POT_DECORATIONS, Sherds.DEFAULT);
	}

	@Override
	public void removeFromCopiedStackNbt(NbtCompound nbt) {
		super.removeFromCopiedStackNbt(nbt);
		nbt.remove("sherds");
	}

	public BlockEntity asBlockEntity() {
		return this;
	}
}

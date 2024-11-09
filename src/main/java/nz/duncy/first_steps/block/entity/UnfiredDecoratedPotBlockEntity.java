package nz.duncy.first_steps.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.Sherds;
import net.minecraft.block.entity.DecoratedPotBlockEntity.WobbleType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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

   protected void writeNbt(NbtCompound nbt) {
      super.writeNbt(nbt);
      this.sherds.toNbt(nbt);

   }

   public void readNbt(NbtCompound nbt) {
      super.readNbt(nbt);
      this.sherds = Sherds.fromNbt(nbt);

   }

   public BlockEntityUpdateS2CPacket toUpdatePacket() {
      return BlockEntityUpdateS2CPacket.create(this);
   }

   public NbtCompound toInitialChunkDataNbt() {
      return this.createNbt();
   }

   public Direction getHorizontalFacing() {
      return (Direction)this.getCachedState().get(Properties.HORIZONTAL_FACING);
   }

   public Sherds getSherds() {
      return this.sherds;
   }

   public void setSherd(ItemStack stack, int index) {
      switch (index) {
         case 0: // north
            this.sherds = new Sherds(this.sherds.back(), this.sherds.left(), this.sherds.right(), stack.getItem());
            break;
      
         case 1: // east
            this.sherds = new Sherds(this.sherds.back(), stack.getItem(), this.sherds.right(), this.sherds.front());
            break;
      
         case 2: // south
            this.sherds = new Sherds(stack.getItem(), this.sherds.left(), this.sherds.right(), this.sherds.front());
            break;
      
         case 3: // west
            this.sherds = new Sherds(this.sherds.back(), this.sherds.left(), stack.getItem(), this.sherds.front());
            break;
      
         default:
            break;
      }

      stack.decrement(1);
   }

   public void readNbtFromStack(ItemStack stack) {
      this.sherds = Sherds.fromNbt(BlockItem.getBlockEntityNbt(stack));
   }

   public ItemStack asStack() {
      return getStackWith(this.sherds);
   }

   public static ItemStack getStackWith(Sherds sherds) {
      ItemStack itemStack = Items.DECORATED_POT.getDefaultStack();
      NbtCompound nbtCompound = sherds.toNbt(new NbtCompound());
      BlockItem.setBlockEntityNbt(itemStack, ModBlockEntities.UNFIRED_DECORATED_POT_BLOCK_ENTITY, nbtCompound);
      return itemStack;
   }

   @Nullable
   public Identifier getLootTableId() {
      return this.lootTableId;
   }

   public void setLootTableId(@Nullable Identifier lootTableId) {
      this.lootTableId = lootTableId;
   }

   public long getLootTableSeed() {
      return this.lootTableSeed;
   }

   public void setLootTableSeed(long lootTableSeed) {
      this.lootTableSeed = lootTableSeed;
   }


   public BlockEntity asBlockEntity() {
      return this;
   }

   public void wobble(WobbleType wobbleType) {
      if (this.world != null && !this.world.isClient()) {
         this.world.addSyncedBlockEvent(this.getPos(), this.getCachedState().getBlock(), 1, wobbleType.ordinal());
      }
   }

   public boolean onSyncedBlockEvent(int type, int data) {
      if (this.world != null && type == 1 && data >= 0 && data < net.minecraft.block.entity.DecoratedPotBlockEntity.WobbleType.values().length) {
         this.lastWobbleTime = this.world.getTime();
         this.lastWobbleType = net.minecraft.block.entity.DecoratedPotBlockEntity.WobbleType.values()[data];
         return true;
      } else {
         return super.onSyncedBlockEvent(type, data);
      }
   }
}

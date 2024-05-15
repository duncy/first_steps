package nz.duncy.first_steps.block.entity;

import java.util.BitSet;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import nz.duncy.first_steps.block.custom.RockBlock;
import nz.duncy.first_steps.item.ModItems;

public class RockBlockEntity extends BlockEntity {
    private BitSet KNAPPED_VOXELS;
    private VoxelShape SHAPE;
    private Identifier TEXTURE_ID;
    private int VOXEL_COUNT = 64;
    private static final BitSet AXE_SHAPE = BitSet.valueOf(new long[] {0b110011110001110000100000000111001111L}); 
    private static final BitSet SHOVEL_SHAPE = BitSet.valueOf(new long[] {0b1101110001000001000011000}); 
    private static final BitSet KNIFE_SHAPE = BitSet.valueOf(new long[] {0b1011100111100111110011110}); 
    private static final BitSet SPEAR_SHAPE = BitSet.valueOf(new long[] {0b1101110011000011100111110}); 

    public RockBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROCK_BLOCK_ENTITY, pos, state);
        KNAPPED_VOXELS = new BitSet();
        SHAPE = Block.createCuboidShape(4, 0, 4, 12, 1, 12);
        RockBlock block = (RockBlock) state.getBlock();
        TEXTURE_ID = block.getTextureId();
    }
    
    public Identifier getTextureId() {
        return this.TEXTURE_ID;
    }

    public BitSet getKnappedVoxels() {
        return this.KNAPPED_VOXELS;
    }

    public boolean edgeVoxel(int pos) {
        if (pos % 8 == 7 || pos % 8 == 0 || (pos >= 1 && pos <=6 ) || (pos >= 57 && pos <= 62)) {
            return true;
        } else {
            return (
                    (this.KNAPPED_VOXELS.get(pos - 8) && 
                     (
                      (this.KNAPPED_VOXELS.get(pos - 7) && this.KNAPPED_VOXELS.get(pos - 9)) || 
                      (this.KNAPPED_VOXELS.get(pos - 9) && this.KNAPPED_VOXELS.get(pos - 1))
                     )
                    ) ||

                    (this.KNAPPED_VOXELS.get(pos - 1) && 
                     (
                      (this.KNAPPED_VOXELS.get(pos - 9) && this.KNAPPED_VOXELS.get(pos + 7)) || 
                      (this.KNAPPED_VOXELS.get(pos + 7) && this.KNAPPED_VOXELS.get(pos + 8))
                     )
                    ) || 

                    (this.KNAPPED_VOXELS.get(pos + 1) &&
                     (
                      (this.KNAPPED_VOXELS.get(pos + 9) && this.KNAPPED_VOXELS.get(pos - 7)) || 
                      (this.KNAPPED_VOXELS.get(pos - 7) && this.KNAPPED_VOXELS.get(pos - 8))
                     )
                    ) || 

                    (this.KNAPPED_VOXELS.get(pos + 8) &&
                     (
                      (this.KNAPPED_VOXELS.get(pos + 9) && this.KNAPPED_VOXELS.get(pos + 7)) || 
                      (this.KNAPPED_VOXELS.get(pos + 9) && this.KNAPPED_VOXELS.get(pos + 1))
                     )
                    )
                   );
        }
    }

    public void knappVoxel(int pos) {
        this.KNAPPED_VOXELS.set(pos);
        this.VOXEL_COUNT--;

        checkShapes();
    }

    public void checkShapes() {
        switch(this.VOXEL_COUNT) {
            case 19:
                if (checkAxe()) {
                    ItemStack itemStack = new ItemStack(ModItems.STONE_HEAD_AXE);
                    world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack));
                    destroyBlock(false);
                }
                break;
            case 16:
                if (checkShovel()) {
                    ItemStack itemStack = new ItemStack(ModItems.STONE_HEAD_SHOVEL);
                    world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack));
                    destroyBlock(false);
                }
                break;
            case 10:
                if (checkKnife()) {
                    ItemStack itemStack = new ItemStack(ModItems.STONE_HEAD_KNIFE);
                    world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack));
                    destroyBlock(false);
                }else if (checkSpear()) {
                    ItemStack itemStack = new ItemStack(ModItems.STONE_HEAD_SPEAR);
                    world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack));
                    destroyBlock(false);
                }else {
                    destroyBlock(true);
                }
                
                break;
        }
    }

    private boolean checkShape(BitSet desiredShape, int width, int length) {
        int canvasPos0;
        int canvasPos90;
        int canvasPos180;
        int canvasPos270;

        int shapePos0 = 0;
        int shapePos90 = 0;
        int shapePos180 = 0;
        int shapePos270 = 0;

        int offset0 = 0;
        int offset90 = 0;
        int offset180 = 0;
        int offset270 = 0;
    
        //for (int a = 0; a < length; a++) {
        //    int b = (a % width); 
        //    System.out.print(desiredShape.get(a) ? 0 : 1);
        //    if (b == 0) {
        //        System.out.print("\n");
        //    }
        //}

        //for (int a = 0; a < 64; a++) {
        //    int b = (a % 8); 
        //    System.out.print(this.KNAPPED_VOXELS.get(a) ? 0 : 1);
        //    if (b == 0) {
        //        System.out.print("\n");
        //    }
        //}

        for (int z = 0; z < 8; z++) {
            for (int x = 0; x < 8; x++) {
                // Because check for shapes is only triggered when the amount of active bits is known, we can stop loop once the shape is found.
                if (shapePos0 == length || shapePos90 == length || shapePos180 == length || shapePos270 == length) {
                    return true;
                }

                canvasPos0 = x + (z * 8);
                canvasPos90 = 7 - x + (z * 8);
                canvasPos180 = x + ((7 - z) * 8);
                canvasPos270 = 7 - x + ((7 - z) * 8);
              
                // Check that canvas bit is inside expected shape area
                // Current canvas bit is inside the shape if it is past the offset and before the sum of the offset + width
                if ((x >= offset0) && (x < (offset0 + width))) {
                    if (this.KNAPPED_VOXELS.get(canvasPos0) == desiredShape.get(shapePos0)) {
                        shapePos0++;
                        // If first matching bit in current streak, calculate offset from edge
                        if (shapePos0 == 1) {
                            offset0 = x;
                        }
                    } else {
                        shapePos0 = 0;
                        offset0 = 0;
                    }
                }

                if ((x >= offset90) && (x < (offset90 + width))) {
                    if (this.KNAPPED_VOXELS.get(canvasPos90) == desiredShape.get(shapePos90)) {
                        shapePos90++;
                        if (shapePos90 == 1) {
                            offset90 = x;
                        } 
                    } else {
                        shapePos90 = 0;
                        offset90 = 0;
                    }
                }

                if ((x >= offset180) && (x < (offset180 + width))) {
                    if (this.KNAPPED_VOXELS.get(canvasPos180) == desiredShape.get(shapePos180)) {
                        shapePos180++;
                        if (shapePos180 == 1) {
                            offset180 = x;
                        }
                    } else {
                        shapePos180 = 0;
                        offset180 = 0;
                    }
                }

                if ((x >= offset270) && (x < (offset270 + width))) {
                    if (this.KNAPPED_VOXELS.get(canvasPos270) == desiredShape.get(shapePos270)) {
                        shapePos270++;
                        if (shapePos270 == 1) {
                            offset270 = x;
                        }
                    } else {
                        shapePos270 = 0;
                        offset270 = 0;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkShovel() {
        System.out.print("Shovel check");
        return checkShape(SHOVEL_SHAPE, 5, 25);
    }

    private boolean checkAxe() {
        System.out.print("Axe check");
        return checkShape(AXE_SHAPE, 6, 36);
    }

    private boolean checkKnife() {
        System.out.print("Knife check");
        return checkShape(KNIFE_SHAPE, 5, 25);
    }

    private boolean checkSpear() {
        System.out.print("Spear check");
        return checkShape(SPEAR_SHAPE, 5, 25);
    }

    private void destroyBlock(boolean drop) {
        if (world != null && !world.isClient) {
            world.breakBlock(pos, drop);
        }
    }

    public void updateShape() {
        VoxelShape newShape  = VoxelShapes.empty();
        for (int x = 0; x < 8; x++) {
            for (int z = 0; z < 8; z++) {
                if (!this.KNAPPED_VOXELS.get(x + (z * 8))) {
                    newShape = VoxelShapes.union(newShape, VoxelShapes.cuboid(0.25 + x * 0.0625, 0, 0.25 + z * 0.0625, 0.75 - (7 - x) * 0.0625, 0.0625, 0.75 - (7 - z) * 0.0625));
                }
            }
        }
        this.SHAPE = newShape;
    }

    public VoxelShape getShape() {
        return this.SHAPE;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        long[] longArray = getKnappedVoxels().toLongArray();
        nbt.putInt("count", this.VOXEL_COUNT);
        nbt.putLongArray("bitset", longArray);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        long[] longArray = nbt.getLongArray("bitset");
        this.KNAPPED_VOXELS = BitSet.valueOf(longArray);
        this.VOXEL_COUNT = nbt.getInt("count");
        updateShape();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

}

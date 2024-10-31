package nz.duncy.first_steps.block.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShulkerBoxBlockEntity.AnimationStage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import nz.duncy.first_steps.FirstSteps;

public class PottersWheelBlockEntity extends BlockEntity {
    public float spin;
    private float previousSpin;
    private boolean spinning;

    public PottersWheelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.POTTERS_WHEEL_BLOCK_ENTITY, pos, state);
        this.spinning = false;
        this.spin = 0.0F;
        this.previousSpin = 0.0F;
    }

    public static void tick(World world, BlockPos pos, BlockState state, PottersWheelBlockEntity blockEntity) {
        blockEntity.updateAnimation(world, pos, state);
    }

    private void updateAnimation(World world, BlockPos pos, BlockState state) {
        if (isSpinning()) {
            this.previousSpin = this.spin;
            if (this.previousSpin >= 1.0F) {
                this.spin = 1.0F;
                this.spinning = false;
                markDirty();
                updateNeighborStates(world, pos, state);
            } 
            else {
                this.spin += 0.1F;         
            }
        } else {
            if (this.spin == 1.0F) {
                this.previousSpin = 0.0F;
                this.spin = 0.0F;
                markDirty();
            }
        }
    }

    public boolean isSpinning() {
        return this.spinning;
    }

    private static void updateNeighborStates(World world, BlockPos pos, BlockState state) {
        state.updateNeighbors(world, pos, 3);
        world.updateNeighborsAlways(pos, state.getBlock());
    }

    public float getAnimationProgress(PottersWheelBlockEntity blockEntity, float delta) {
        return MathHelper.lerp(delta, blockEntity.previousSpin, blockEntity.spin);
    }

    public void startSpinning() {
        if (!isSpinning()) {
            this.spinning = true;
            updateNeighborStates(world, pos, getCachedState());
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.spinning = nbt.getBoolean("spinning");
        this.spin = nbt.getFloat("spin");
        this.previousSpin = nbt.getFloat("previousSpin");
    }
  
    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putBoolean("spinning", this.spinning);
        nbt.putFloat("spin", this.spin);
        nbt.putFloat("previousSpin", this.previousSpin);
        super.writeNbt(nbt);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    
}

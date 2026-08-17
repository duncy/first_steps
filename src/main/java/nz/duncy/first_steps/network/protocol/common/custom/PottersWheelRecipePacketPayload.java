package nz.duncy.first_steps.network.protocol.common.custom;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.world.item.crafting.PottersWheelRecipe;

public record PottersWheelRecipePacketPayload(SingleInputSet<PottersWheelRecipe> recipes) implements CustomPacketPayload {
    public static final Identifier RECIPE_SYNC_PACKET_ID = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "potters_wheel_recipe_sync");
    public static final CustomPacketPayload.Type<PottersWheelRecipePacketPayload> TYPE = new CustomPacketPayload.Type<>(RECIPE_SYNC_PACKET_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, PottersWheelRecipePacketPayload> CODEC = StreamCodec.composite(SingleInputSet.noRecipeCodec(), PottersWheelRecipePacketPayload::recipes, PottersWheelRecipePacketPayload::new);
    
    @Override
    public Type<PottersWheelRecipePacketPayload> type() {
        return TYPE;
    }
    
}

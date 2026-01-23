package nz.duncy.first_steps.network.protocol.common.custom;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import nz.duncy.first_steps.FirstSteps;

public record KnappingRecipePacketPayload(SingleInputSet<StonecutterRecipe> recipes) implements CustomPacketPayload {
    public static final Identifier RECIPE_SYNC_PACKET_ID = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "knapping_recipe_sync");
    public static final CustomPacketPayload.Type<KnappingRecipePacketPayload> TYPE = new CustomPacketPayload.Type<>(RECIPE_SYNC_PACKET_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, KnappingRecipePacketPayload> CODEC = StreamCodec.composite(SingleInputSet.noRecipeCodec(), KnappingRecipePacketPayload::recipes, KnappingRecipePacketPayload::new);
    
    @Override
    public Type<KnappingRecipePacketPayload> type() {
        return TYPE;
    }
    
}

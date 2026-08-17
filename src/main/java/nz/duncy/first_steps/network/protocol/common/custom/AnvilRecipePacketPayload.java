package nz.duncy.first_steps.network.protocol.common.custom;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.world.item.crafting.AnvilRecipe;

public record AnvilRecipePacketPayload(SingleInputSet<AnvilRecipe> recipes) implements CustomPacketPayload {
    public static final Identifier RECIPE_SYNC_PACKET_ID = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "anvil_recipe_sync");
    public static final CustomPacketPayload.Type<AnvilRecipePacketPayload> TYPE = new CustomPacketPayload.Type<>(RECIPE_SYNC_PACKET_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, AnvilRecipePacketPayload> CODEC = StreamCodec.composite(SingleInputSet.noRecipeCodec(), AnvilRecipePacketPayload::recipes, AnvilRecipePacketPayload::new);
    
    @Override
    public Type<AnvilRecipePacketPayload> type() {
        return TYPE;
    }
    
}

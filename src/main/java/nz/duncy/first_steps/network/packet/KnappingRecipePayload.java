package nz.duncy.first_steps.network.packet;

import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.recipe.display.CuttingRecipeDisplay.Grouping;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record KnappingRecipePayload(Grouping<StonecuttingRecipe> recipes) implements CustomPayload {
    public static final Identifier RECIPE_SYNC_PACKET_ID = Identifier.of(FirstSteps.MOD_ID, "recipe_sync");
    public static final CustomPayload.Id<KnappingRecipePayload> ID = new CustomPayload.Id<>(RECIPE_SYNC_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, KnappingRecipePayload> CODEC = PacketCodec.tuple(Grouping.codec(), KnappingRecipePayload::recipes, KnappingRecipePayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    
}

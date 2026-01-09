package nz.duncy.first_steps.data.models.model;

import java.util.Optional;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class ModModelTemplates {
    public static final ModelTemplate ROCK = create("template_rock", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate TWO_ROCKS = create("template_two_rocks", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate THREE_ROCKS = create("template_three_rocks", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate FOUR_ROCKS = create("template_four_rocks", TextureSlot.ALL, TextureSlot.PARTICLE);

    private static ModelTemplate create(String string, TextureSlot... textureSlots) {
        return new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "block/" + string)), Optional.empty(), textureSlots);
   }
}

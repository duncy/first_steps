package nz.duncy.first_steps.model;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class ModEntityModelLayers {

    private static final String MAIN = "main";
    private static final Set<EntityModelLayer> LAYERS = Sets.newHashSet();
    public static final EntityModelLayer SPEAR = registerMain("spear_3d");

    private static EntityModelLayer registerMain(String id) {
        return register(id, MAIN);
    }

    private static EntityModelLayer register(String id, String layer) {
        EntityModelLayer entityModelLayer = create(id, layer);
        if (!LAYERS.add(entityModelLayer)) {
            throw new IllegalStateException("Duplicate registration for " + entityModelLayer);
        }
        return entityModelLayer;
    }

    private static EntityModelLayer create(String id, String layer) {
        return new EntityModelLayer(new Identifier(FirstSteps.MOD_ID, id), layer);
    }
}
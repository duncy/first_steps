package nz.duncy.first_steps.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class SpearEntityModel extends Model {

    public SpearEntityModel(ModelPart root) {
        super(root, RenderLayer::getEntitySolid);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild("spearhead1", ModelPartBuilder.create().uv(0, 6).cuboid(0.0f, 0.0f, 0.0f, 2.0f, 3.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("spearhead2", ModelPartBuilder.create().uv(0, 10).cuboid(2.0f, 1.0f, 0.0f, 3.0f, 5.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("spearhead3", ModelPartBuilder.create().uv(0, 16).cuboid(2.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("spearhead4", ModelPartBuilder.create().uv(0, 18).cuboid(1.0f, 3.0f, 0.0f, 1.0f, 2.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("spearhead5", ModelPartBuilder.create().uv(0, 20).cuboid(5.0f, 2.0f, 0.0f, 1.0f, 3.0f, 1.0f), ModelTransform.NONE);

        modelPartData2.addChild("handle2", ModelPartBuilder.create().uv(4, 0).cuboid(5.0f, 5.0f, 0.0f, 2.0f, 2.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("handle2-1", ModelPartBuilder.create().uv(0, 3).cuboid(6.0f, 7.0f, 0.0f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("handle2-2", ModelPartBuilder.create().uv(0, 0).cuboid(7.0f, 6.0f, 0.0f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);

        modelPartData2.addChild("handle3", ModelPartBuilder.create().uv(4, 0).cuboid(7.0f, 7.0f, 0.0f, 2.0f, 2.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("handle3-1", ModelPartBuilder.create().uv(0, 3).cuboid(8.0f, 9.0f, 0.0f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("handle3-2", ModelPartBuilder.create().uv(0, 0).cuboid(9.0f, 8.0f, 0.0f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);

        modelPartData2.addChild("handle4", ModelPartBuilder.create().uv(4, 0).cuboid(9.0f, 9.0f, 0.0f, 2.0f, 2.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("handle4-1", ModelPartBuilder.create().uv(0, 3).cuboid(10.0f, 11.0f, 0.0f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("handle4-2", ModelPartBuilder.create().uv(0, 0).cuboid(11.0f, 10.0f, 0.0f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);

        modelPartData2.addChild("handle5", ModelPartBuilder.create().uv(4, 0).cuboid(11.0f, 11.0f, 0.0f, 2.0f, 2.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("handle5-1", ModelPartBuilder.create().uv(0, 3).cuboid(12.0f, 13.0f, 0.0f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("handle5-2", ModelPartBuilder.create().uv(0, 0).cuboid(13.0f, 12.0f, 0.0f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);

        modelPartData2.addChild("handle6", ModelPartBuilder.create().uv(4, 0).cuboid(13.0f, 13.0f, 0.0f, 2.0f, 2.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("handle6-1", ModelPartBuilder.create().uv(0, 3).cuboid(14.0f, 15.0f, 0.0f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("handle6-2", ModelPartBuilder.create().uv(0, 0).cuboid(15.0f, 14.0f, 0.0f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);

        modelPartData2.addChild("handle7", ModelPartBuilder.create().uv(4, 0).cuboid(15.0f, 15.0f, 0.0f, 2.0f, 2.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("handle7-1", ModelPartBuilder.create().uv(0, 3).cuboid(16.0f, 17.0f, 0.0f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);
        modelPartData2.addChild("handle7-2", ModelPartBuilder.create().uv(0, 0).cuboid(17.0f, 16.0f, 0.0f, 1.0f, 1.0f, 1.0f), ModelTransform.NONE);

        modelPartData2.addChild("handle8", ModelPartBuilder.create().uv(4, 3).cuboid(17.0f, 17.0f, 0.0f, 2.0f, 2.0f, 1.0f), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 32, 32);
    }
}
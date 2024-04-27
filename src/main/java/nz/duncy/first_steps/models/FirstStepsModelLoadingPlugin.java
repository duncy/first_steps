//package nz.duncy.first_steps.models;
//
//import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
//import net.minecraft.client.util.ModelIdentifier;
//import net.minecraft.client.util.SpriteIdentifier;
//import net.minecraft.screen.PlayerScreenHandler;
//import net.minecraft.util.Identifier;
//
//public class FirstStepsModelLoadingPlugin implements ModelLoadingPlugin {
//    public static final ModelIdentifier STONE_ROCK_BLOCK_MODEL = new ModelIdentifier("first_steps", "stone_rock", "");
    //private static final SpriteIdentifier STONE_ROCK_SPRITE_ID = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier("minecraft:block/stone"));

    //@Override
    //public void onInitializeModelLoader(Context pluginContext) {
    //    pluginContext.modifyModelOnLoad().register((original, context) -> {
    //        if (context.id().equals(STONE_ROCK_BLOCK_MODEL)) {
    //            return new RockBlockModel(STONE_ROCK_SPRITE_ID);
    //        } else {
    //            return original;
    //        }
    //    });
    //}
//}

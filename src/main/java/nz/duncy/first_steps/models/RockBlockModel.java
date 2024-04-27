//package nz.duncy.first_steps.models;
//
//import java.util.BitSet;
//import java.util.Collection;
//import java.util.List;
//import java.util.function.Function;
//import java.util.function.Supplier;
//
//import net.fabricmc.fabric.api.renderer.v1.Renderer;
//import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
//import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
//import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
//import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
//import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
//import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
//import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
//import net.minecraft.block.BlockState;
//import net.minecraft.client.render.model.BakedModel;
//import net.minecraft.client.render.model.BakedQuad;
//import net.minecraft.client.render.model.Baker;
//import net.minecraft.client.render.model.ModelBakeSettings;
//import net.minecraft.client.render.model.UnbakedModel;
//import net.minecraft.client.render.model.json.ModelOverrideList;
//import net.minecraft.client.render.model.json.ModelTransformation;
//import net.minecraft.client.texture.Sprite;
//import net.minecraft.client.util.SpriteIdentifier;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.Identifier;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Direction;
//import net.minecraft.world.BlockRenderView;
//import net.minecraft.util.math.random.Random;
//
//public class RockBlockModel implements UnbakedModel, BakedModel, FabricBakedModel {
//    private static SpriteIdentifier SPRITE_ID;
//    private static Sprite SPRITE;
//    private Mesh MESH;
//
//    private static BitSet knappedVoxels = new BitSet();
//
//    public RockBlockModel(SpriteIdentifier spriteId) {
//        setSpriteId(spriteId);
//        knappedVoxels.set(15);
//    }
//
//    public void setKnappedVoxels(BitSet entityKnappedVoxels) {
//        knappedVoxels = entityKnappedVoxels;
//    }
//
//    public BitSet getKnappedVoxels() {
//        return knappedVoxels;
//    }
//
//    public void setSpriteId(SpriteIdentifier spriteId) {
//        SPRITE_ID = spriteId;
//    }
//
//    @Override
//    public Collection<Identifier> getModelDependencies() {
//        return List.of();
//    }
//
//    @Override
//    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {}
//
//    @Override
//    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
//        SPRITE = textureGetter.apply(SPRITE_ID);
//
//        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
//        MeshBuilder builder = renderer.meshBuilder();
//        QuadEmitter emitter = builder.getEmitter();
//        
//        Direction[] faces = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
//
//        if (getKnappedVoxels().isEmpty()) {
//            for (Direction face : faces) {
//                emitter.square(face, 0.25f, 0.0f, 0.75f, 0.0625f, 0.25f);
//                emitter.spriteBake(SPRITE, MutableQuadView.BAKE_LOCK_UV);
//                emitter.color(-1, -1, -1, -1);
//                emitter.emit();
//            }
//
//            emitter.square(Direction.UP, 0.25f, 0.25f, 0.75f, 0.75f, 0.9375f);
//            emitter.spriteBake(SPRITE, MutableQuadView.BAKE_LOCK_UV);
//            emitter.color(-1, -1, -1, -1);
//            emitter.emit();
//            
//        } else {
//            for (int x = 0; x < 8; x++ ) {
//                for (int z = 0; z < 8; z++) {
//                    if (!getKnappedVoxels().get(x + (z * 8))) {
//                        for (Direction face : faces) {
//                            emitter.square(face, (0.25f + x * 0.0625f), 0.0f, (0.25f + (x + 1) * 0.0625f), 0.0625f, (0.25f + z * 0.0625f));
//                            emitter.spriteBake(SPRITE, MutableQuadView.BAKE_LOCK_UV);
//                            emitter.color(-1, -1, -1, -1);
//                            emitter.emit();
//                        }
//                        emitter.square(Direction.UP, (0.25f + x * 0.0625f), (0.25f + z * 0.0625f), (0.25f + (x + 1) * 0.0625f), (0.25f + (z + 1) * 0.0625f), 0.9375f);
//                        emitter.spriteBake(SPRITE, MutableQuadView.BAKE_LOCK_UV);
//                        emitter.color(-1, -1, -1, -1);
//                        emitter.emit();
//                    }
//                }
//            }
//        }
//
//        MESH = builder.build();
//
//        return this;
//    }
//    
//   // private void emitTop(QuadEmitter emitter) {
//   //     emitter.square(Direction.UP, 0.25f, 0.25f, 0.75f, 0.75f, 0.9375f);
//   //     emitter.spriteBake(SPRITE, MutableQuadView.BAKE_LOCK_UV);
//   //     emitter.color(-1, -1, -1, -1);
//   //     emitter.emit();
//   // }
//
//    @Override
//    public List<BakedQuad> getQuads(BlockState state, Direction face, Random random) {
//        return List.of();
//    }
//
//    @Override
//    public boolean useAmbientOcclusion() {
//        return true;
//    }
//
//    @Override
//    public boolean isBuiltin() {
//        return false;
//    }
//
//    @Override
//    public boolean hasDepth() {
//        return false;
//    }
//
//    @Override
//    public boolean isSideLit() {
//        return false;
//    }
//
//    @Override
//    public Sprite getParticleSprite() {
//        return SPRITE; 
//    }
//
//    @Override
//    public ModelTransformation getTransformation() {
//        return null;
//    }
//
//    @Override
//    public ModelOverrideList getOverrides() {
//        return null;
//    }
//    
//    @Override
//    public boolean isVanillaAdapter() {
//        return false;
//    }
//
//    @Override
//    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
//        MESH.outputTo(renderContext.getEmitter());
//    }
//
//    @Override
//    public void emitItemQuads(ItemStack itemStack, Supplier<Random> supplier, RenderContext renderContext) {}
//}

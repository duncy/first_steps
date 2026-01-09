package nz.duncy.first_steps.data.tags;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.Items;
import nz.duncy.first_steps.tags.ModItemTags;
import nz.duncy.first_steps.world.level.block.ModBlocks;

public class ModItemTagProvider extends ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(Provider wrapperLookup) {
        valueLookupBuilder(ModItemTags.IS_ROCK)
            .add(ModBlocks.STONE_ROCK.asItem())
            .add(Items.FLINT)
            .add(ModBlocks.BASALT_ROCK.asItem())
            .add(ModBlocks.OBSIDIAN_ROCK.asItem());
    }
    
}

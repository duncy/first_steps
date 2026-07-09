package nz.duncy.first_steps.world.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import nz.duncy.first_steps.world.item.component.CrucibleContainerContents;
import nz.duncy.first_steps.world.item.component.ModDataComponents;

public class CrucibleBlockItem extends BlockItem {
    public CrucibleBlockItem(Block block, Properties properties) {
        super(block, properties.component(ModDataComponents.CRUCIBLE_CONTAINER_CONTENTS, new CrucibleContainerContents(0, null)));
    }
}

package lol.erobuki.fastmathplus.lib;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockCustomModelData extends Block {

    public static final IUnlistedProperty<Boolean> HAS_CUSTOM_MODEL_DATA = new IUnlistedProperty<Boolean>() {

        @Override
        public String getName() {
            return "has_custom_model_data";
        }

        @Override
        public boolean isValid(Boolean value) {
            return true;
        }

        @Override
        public Class<Boolean> getType() {
            return Boolean.class;
        }

        @Override
        public String valueToString(Boolean value) {
            return value.toString();
        }
    };

    public BlockCustomModelData(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IBlockState extendedState = super.getExtendedState(state, world, pos);
        boolean hasCustomModelData = false;
        return ((IExtendedBlockState) extendedState).withProperty(HAS_CUSTOM_MODEL_DATA, hasCustomModelData);
    }
}
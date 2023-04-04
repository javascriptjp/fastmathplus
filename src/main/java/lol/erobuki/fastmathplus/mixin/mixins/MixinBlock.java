package lol.erobuki.fastmathplus.mixin.mixins;

import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

@Mixin(Block.class)
public abstract class MixinBlock {

    @Inject(method = "onBlockAdded", at = @At("RETURN"))
    private void onBlockAdded(World worldIn, BlockPos pos, IBlockState state, CallbackInfo ci) {
        if (!worldIn.isRemote && state.getLightValue() > 0) {
            worldIn.checkLightFor(EnumSkyBlock.BLOCK, pos);
        }
    }

    @Inject(method = "breakBlock", at = @At("RETURN"))
    private void breakBlock(World worldIn, BlockPos pos, IBlockState state, CallbackInfo ci) {
        if (!worldIn.isRemote && state.getLightValue() > 0) {
            worldIn.checkLightFor(EnumSkyBlock.BLOCK, pos);
        }
    }

    @Inject(method = "onNeighborChange", at = @At("HEAD"), remap = false)
    private void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor, CallbackInfo ci) {
        if (world instanceof World && !((World) world).isRemote) {
            IBlockState state = world.getBlockState(pos);
            if (state.getLightValue() > 0 && neighbor.getY() >= pos.getY()) {
                ((World) world).checkLightFor(EnumSkyBlock.BLOCK, pos);
            }
        }
    }
}
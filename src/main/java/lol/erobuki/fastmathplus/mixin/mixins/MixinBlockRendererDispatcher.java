package lol.erobuki.fastmathplus.mixin.mixins;

import lol.erobuki.fastmathplus.lib.BlockCustomModelData;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRendererDispatcher.class)
public abstract class MixinBlockRendererDispatcher {
    @Inject(method = "renderBlock", at = @At("HEAD"), cancellable = true)
    private void onRenderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, BufferBuilder bufferBuilderIn, CallbackInfoReturnable<Boolean> cir) {
        if (blockAccess instanceof World && !((World) blockAccess).isRemote) {
            IExtendedBlockState extendedState = (IExtendedBlockState) state.getActualState(blockAccess, pos);
            boolean hasCustomModelData = extendedState.getValue(BlockCustomModelData.HAS_CUSTOM_MODEL_DATA);
            if (hasCustomModelData) {
                cir.cancel();
            }
        }
    }

}
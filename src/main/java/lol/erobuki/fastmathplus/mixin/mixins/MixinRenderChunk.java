package lol.erobuki.fastmathplus.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.RenderChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderChunk.class)
public abstract class MixinRenderChunk {
    private final Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "rebuildChunk", at = @At("HEAD"), cancellable = true)
    private void onRebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator, CallbackInfo ci) {
        if (mc.player != null && Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16 < mc.player.getPosition().distanceSq((int) x, (int) y, (int) z)) {
            ci.cancel();
        }
    }
}
package lol.erobuki.fastmathplus.mixin.mixins;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Mixin(Chunk.class)
public abstract class MixinChunk {
    private final EnumFacing[] VALID_FACING = {EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST};
    @Shadow private boolean isGapLightingUpdated;
    @Shadow @Final private ExtendedBlockStorage[] storageArrays;
    @Shadow @Final public int x;
    @Shadow @Final public int z;
    @Shadow @Final private World world;

    @Inject(method = "recheckGaps", at = @At("HEAD"), cancellable = true)
    private void recheckGapsHead(boolean p_177441_1_, CallbackInfo ci) {
        if (!this.isGapLightingUpdated && p_177441_1_) {
            ci.cancel();
            ExecutorService executor = Executors.newFixedThreadPool(VALID_FACING.length);
            for (EnumFacing facing : VALID_FACING) {
                executor.execute(() -> this.recheckGaps(facing));
            }
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                // 例外処理
            }
            this.isGapLightingUpdated = true;
        }
    }

    private void recheckGaps(EnumFacing facing) {
        int i = facing.getXOffset();
        int j = facing.getYOffset();
        int k = facing.getZOffset();
        int xStart = i < 0 ? 0 : 15;
        int xEnd = i < 0 ? 16 : -1;
        int yStart = j < 0 ? 0 : 15;
        int yEnd = j < 0 ? 16 : -1;
        int zStart = k < 0 ? 0 : 15;
        int zEnd = k < 0 ? 16 : -1;
        for (ExtendedBlockStorage storage : this.storageArrays) {
            if (storage != null && storage.needsRandomTick()) {
                int storageY = storage.getYLocation();
                for (int x = xStart; x != xEnd; x -= i) {
                    for (int y = yStart; y != yEnd; y -= j) {
                        for (int z = zStart; z != zEnd; z -= k) {
                            int blockX = (x + this.x * 16);
                            int blockY = (y + storageY);
                            int blockZ = (z + this.z * 16);
                            int blockLight = storage.getBlockLight(x, y, z);
                            int skyLight = storage.getSkyLight(x, y, z);
                            int newBlockLight = Math.max(0, blockLight - 1);
                            int newSkyLight = Math.max(0, skyLight - 1);
                            storage.setBlockLight(x, y, z, newBlockLight);
                            storage.setSkyLight(x, y, z, newSkyLight);
                            if (newBlockLight != blockLight || newSkyLight != skyLight) {
                                this.world.markBlockRangeForRenderUpdate(blockX, blockY, blockZ, blockX, blockY, blockZ);
                                IBlockState state = storage.get(x, y, z);
                                if (state != null) {
                                    BlockPos pos = new BlockPos(x + this.x * 16, y + storageY, z + this.z * 16);
                                    this.world.notifyBlockUpdate(pos, state, state, 3);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
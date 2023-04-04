package lol.erobuki.fastmathplus.mixin.mixins;

import net.minecraft.util.math.Vec3d;
import org.apache.commons.math3.util.FastMath;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Vec3d.class)
public abstract class MixinVec3d {
    @Inject(method = "fromPitchYaw(FF)Lnet/minecraft/util/math/Vec3d;", at = @At("HEAD"), cancellable = true)
    private static void fromPitchYaw(float p_189986_0_, float p_189986_1_, CallbackInfoReturnable<Vec3d> cir) {
        float f = (float) FastMath.cos(-p_189986_1_ * 0.017453292F - (float)FastMath.PI);
        float f1 = (float) FastMath.sin(-p_189986_1_ * 0.017453292F - (float)FastMath.PI);
        float f2 = (float) -FastMath.cos(-p_189986_0_ * 0.017453292F);
        float f3 = (float) FastMath.sin(-p_189986_0_ * 0.017453292F);
        cir.setReturnValue(new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2)));
    }
}
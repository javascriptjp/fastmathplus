package lol.erobuki.fastmathplus.mixin.mixins;

import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.apache.commons.math3.util.FastMath;

@Mixin(MathHelper.class)
public abstract class MixinMathHelper {

    @Inject(method = "sin", at = @At("HEAD"), cancellable = true)
    private static void sin(float angle, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue((float) FastMath.sin(angle));
    }

    @Inject(method = "cos", at = @At("HEAD"), cancellable = true)
    private static void cos(float angle, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue((float) FastMath.cos(angle));
    }

    @Inject(method = "sqrt(F)F", at = @At("HEAD"), cancellable = true)
    private static void sqrt(float value, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue((float) FastMath.sqrt(value));
    }

    @Inject(method = "sqrt(D)F", at = @At("HEAD"), cancellable = true)
    private static void sqrt(double value, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue((float) FastMath.sqrt(value));
    }

    @Inject(method = "floor(F)I", at = @At("HEAD"), cancellable = true)
    private static void floor(float value, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) FastMath.floor(value));
    }

    @Inject(method = "floor(D)I", at = @At("HEAD"), cancellable = true)
    private static void floor(double value, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) FastMath.floor(value));
    }

    @Inject(method = "fastFloor", at = @At("HEAD"), cancellable = true)
    private static void fastFloor(double value, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) FastMath.floor(value));
    }

    @Inject(method = "lfloor", at = @At("HEAD"), cancellable = true)
    private static void lfloor(double value, CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue((long) FastMath.floor(value));
    }

    @Inject(method = "abs(I)I", at = @At("HEAD"), cancellable = true)
    private static void abs(int value, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(FastMath.abs(value));
    }

    @Inject(method = "abs(F)F", at = @At("HEAD"), cancellable = true)
    private static void abs(float value, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(FastMath.abs(value));
    }

    @Inject(method = "ceil(F)I", at = @At("HEAD"), cancellable = true)
    private static void ceil(float value, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) FastMath.ceil(value));
    }

    @Inject(method = "ceil(D)I", at = @At("HEAD"), cancellable = true)
    private static void ceil(double value, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) FastMath.ceil(value));
    }

    @Inject(method = "atan2", at = @At("HEAD"), cancellable = true)
    private static void atan2(double y, double x, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(FastMath.atan2(y, x));
    }

    @Inject(method = "log2", at = @At("HEAD"), cancellable = true)
    private static void log2(int value, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(FastMath.getExponent((double) value) - 1);
    }
}
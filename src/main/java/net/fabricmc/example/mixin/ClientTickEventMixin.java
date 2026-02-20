package net.fabricmc.example.mixin;


import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ClientTickEventMixin {

    @Shadow
    private Timer timer;

    //ClientTickEvent.END
    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Profiler;endSection()V", ordinal = 0))
    private void forge$clientTickEventEnd(CallbackInfo ci) {
        var event = new TickEvent.ClientTickEvent(TickEvent.Phase.END);
        TickEvent.ClientTickEvent.EVENT.invoker().onTick(event);
    }

    @Inject(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityRenderer;updateCameraAndRender(F)V"))
    private void forge$clientRenderTickPre(CallbackInfo ci) {
        TickEvent.RenderTickEvent.EVENT.invoker().onTick(new TickEvent.RenderTickEvent(TickEvent.Phase.START, this.timer.renderPartialTicks));
    }

    @Inject(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityRenderer;updateCameraAndRender(F)V", shift = At.Shift.AFTER))
    private void forge$clientRenderTickPost(CallbackInfo ci) {
        TickEvent.RenderTickEvent.EVENT.invoker().onTick(new TickEvent.RenderTickEvent(TickEvent.Phase.END, this.timer.renderPartialTicks));
    }
}


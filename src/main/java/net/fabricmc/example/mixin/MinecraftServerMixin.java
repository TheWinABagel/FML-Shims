package net.fabricmc.example.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.fabricmc.api.EnvType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "updateTimeLightAndEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityTracker;updateTrackedEntities()V"))
    private void forge$onWorldTick(CallbackInfo ci, @Local WorldServer worldServer) {
        TickEvent.WorldTickEvent.EVENT.invoker().onTick(new TickEvent.WorldTickEvent(EnvType.SERVER, TickEvent.Phase.END, worldServer));
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void forge$onPostServerTick(CallbackInfo ci) {
        TickEvent.ServerTickEvent.EVENT.invoker().onServerTick(new TickEvent.ServerTickEvent(TickEvent.Phase.END));
    }
}

package net.fabricmc.example.mixin;

import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.src.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {
    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void forge$onTickPre(CallbackInfo ci) {
        TickEvent.PlayerTickEvent.EVENT.invoker().onTick(new TickEvent.PlayerTickEvent(TickEvent.Phase.START, (EntityPlayer) (Object) this));
    }

    @Inject(method = "onUpdate", at = @At("TAIL"))
    private void forge$onTickPost(CallbackInfo ci) {
        TickEvent.PlayerTickEvent.EVENT.invoker().onTick(new TickEvent.PlayerTickEvent(TickEvent.Phase.END, (EntityPlayer) (Object) this));
    }
}

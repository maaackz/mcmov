package com.maaackz.mcmov.mixin.client;

import com.maaackz.mcmov.sound.SoundManager;
import net.minecraft.item.ElytraItem;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraItem.class)
public abstract class ElytraItemMixin {

    @Inject(at = @At("HEAD"), method = "getEquipSound")
    private void onEquipSound(CallbackInfoReturnable<SoundEvent> cir) {
        SoundManager.playItemSound("item.minecraft.elytra");
    }

}

package com.maaackz.mcmov.mixin.client;

import com.maaackz.mcmov.sound.SoundManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {

    @Shadow @Nullable protected abstract Slot getSlotAt(double x, double y);

    private Slot previousSlot = null;

    @Inject(at = @At("RETURN"), method = "drawMouseoverTooltip")
    private void onMouseDragged(DrawContext context, int x, int y, CallbackInfo ci) {
        Slot currentSlot = this.getSlotAt(x, y);

        if (currentSlot != null && currentSlot != previousSlot) {
            ItemStack stack = currentSlot.getStack();
            if (!stack.isEmpty()) {
                SoundManager.playItemSound(stack.getTranslationKey());
            }
        }

        previousSlot = currentSlot;
    }
}

package com.maaackz.mcmov.mixin.client;

import com.maaackz.mcmov.sound.SoundManager;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.ItemRenderContext;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.util.List;
import java.util.Objects;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract String getTranslationKey();

    @Shadow public abstract Item getItem();

    @Inject(method = "use", at = @At("RETURN"))
    private void onUseReturn(CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        TypedActionResult<ItemStack> result = cir.getReturnValue();

        // Don't play sound if the use was unsuccessful or if it's a water bucket
        if (
                !result.getResult().isAccepted()
                || this.getTranslationKey().equals("item.minecraft.water_bucket")
                || this.getTranslationKey().equals("item.minecraft.elytra")
        ) {
            return;
        }

        SoundManager.playItemSound(this.getTranslationKey());
    }

    @Inject(method = "useOnBlock", at = @At("RETURN"))
    private void onPlaceBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        ActionResult result = cir.getReturnValue();

        if (this.getTranslationKey().equals("item.minecraft.water_bucket")) {
            SoundManager.playItemSound("item.minecraft.water_bucket.release");
        } else {
            if (result == ActionResult.PASS || result == ActionResult.SUCCESS) {
                SoundManager.playItemSound(context.getStack().getTranslationKey());
            }
        }
    }

}

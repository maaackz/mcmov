package com.maaackz.mcmov.mixin.client;

import com.maaackz.mcmov.sound.SoundManager;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin {

    @Inject(method = "markRemoved", at = @At("HEAD"))
    private void onSetRemoved(CallbackInfo ci) {
        if (((Object) this) instanceof AbstractFurnaceBlockEntity) {
            SoundManager.stopItemSound("steve.lava.chicken");
        }
    }
}

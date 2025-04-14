package com.maaackz.mcmov.mixin.client;

import com.maaackz.mcmov.sound.SoundManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlEntityMixin extends ThrownItemEntity {

    public EnderPearlEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "onCollision")
    private void onMoveToWorld(HitResult hitResult, CallbackInfo ci) {
        Entity owner = getOwner();
        if (owner instanceof PlayerEntity player) {
            boolean hasEnderPearl = false;

            for (ItemStack stack : player.getInventory().main) {
                if (stack.getItem() == Items.ENDER_PEARL && !stack.isEmpty()) {
                    hasEnderPearl = true;
                    break;
                }
            }

            if (!hasEnderPearl) {
                SoundManager.playItemSound("item.minecraft.ender_pearl_last");
            }
        }
    }
}

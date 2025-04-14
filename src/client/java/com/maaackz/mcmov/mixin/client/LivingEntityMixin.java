package com.maaackz.mcmov.mixin.client;

import com.maaackz.mcmov.sound.SoundManager;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Shadow
	public abstract ItemStack getMainHandStack();

	@Shadow
	public abstract ItemStack getOffHandStack();

	private ItemStack lastMainHandStack = ItemStack.EMPTY;
	private ItemStack lastOffHandStack = ItemStack.EMPTY;
	private String lastDimensionKey = "";

	ZombieEntity lastLookedJockey;

	@Inject(method = "triggerItemPickedUpByEntityCriteria", at = @At("HEAD"))
	private void onPickUpItem(ItemEntity item, CallbackInfo ci) {
		Entity entity = (Entity) (Object) this;
		if (!entity.isPlayer()) return;
		SoundManager.playItemSound(item.getStack().getItem().getTranslationKey());
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void onTick(CallbackInfo ci) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (!entity.isPlayer()) return;

		// ✅ Only run this logic on the client to avoid duplicate sounds
		if (!entity.getWorld().isClient()) return;

		// 🌍 Play sound on dimension change
		String dimensionKey = entity.getWorld().getRegistryKey().getValue().toString();
		if (!dimensionKey.equals(lastDimensionKey)) {
			lastDimensionKey = dimensionKey;
			SoundManager.playDimensionSound(dimensionKey);
		}

		// ✋ Check and play for main hand item change
		ItemStack currentMain = this.getMainHandStack();
		if (!ItemStack.areEqual(currentMain, lastMainHandStack)) {
			lastMainHandStack = currentMain.copy();
			if (!currentMain.isEmpty()) {
				SoundManager.playItemSound(currentMain.getTranslationKey());
			}
		}

		// 🫲 Check and play for offhand item change
		ItemStack currentOff = this.getOffHandStack();
		if (!ItemStack.areEqual(currentOff, lastOffHandStack)) {
			lastOffHandStack = currentOff.copy();
			if (!currentOff.isEmpty()) {
				SoundManager.playItemSound(currentOff.getTranslationKey());
			}
		}

		// 🐔 Check for looking at a Chicken Jockey
		ClientPlayerEntity player = (ClientPlayerEntity) entity;
		ClientWorld world = (ClientWorld) player.getWorld();

		double reach = 6.0D; // Max interaction distance
		Vec3d cameraPos = player.getCameraPosVec(1.0F);
		Vec3d lookVec = player.getRotationVec(1.0F).multiply(reach);
		Vec3d targetPos = cameraPos.add(lookVec);

		// Raycast entities
		EntityHitResult hit = ProjectileUtil.raycast(player, cameraPos, targetPos,
				player.getBoundingBox().stretch(lookVec).expand(1.0D),
				e -> e instanceof LivingEntity && e.isAlive(), reach);

		if (hit != null && hit.getEntity() instanceof ZombieEntity zombie) {
			if (zombie.isBaby() && zombie.hasVehicle() && zombie.getVehicle() instanceof ChickenEntity) {
				if (!zombie.equals(lastLookedJockey)) {
					lastLookedJockey = zombie;
					SoundManager.playMobSound("entity.minecraft.chicken_jockey");
				}
			}
		}
	}
}




package com.maaackz.mcmov.mixin.client;

import com.maaackz.mcmov.MinecraftMovieMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Inject(method = "init", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        client.getSoundManager().play(
                PositionedSoundInstance.master(
                        Objects.requireNonNull(Registries.SOUND_EVENT.get(Identifier.of(MinecraftMovieMod.MOD_ID, "minecraft"))),
                        1.0f // volume
                ),
                1
        );
    }
}

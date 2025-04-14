package com.maaackz.mcmov.mixin.client;


import com.maaackz.mcmov.sound.CustomSounds;
import com.maaackz.mcmov.sound.SoundManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



@Mixin(MinecraftClient.class)
public abstract class ScreenSoundsMixin {

    @Shadow
    @Nullable
    public Screen currentScreen;

    @Inject(at = @At("HEAD"), method = "run")
    private void init(CallbackInfo info) {
        // This code is injected into the start of MinecraftClient.run()V
    }

    @Inject(at = @At("HEAD"), method = "setScreen")
    private void onScreenChanged(Screen screen, CallbackInfo info) {
        if (screen != null) {
            String className = screen.getClass().getName();
            SoundManager.playScreenSound(className);
        }
    }


}
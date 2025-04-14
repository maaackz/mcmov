package com.maaackz.mcmov.sound;

import com.maaackz.mcmov.MinecraftMovieMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private static final Map<String, Long> lastPlayTime = new HashMap<>();
    private static final long DEBOUNCE_TIME_MS = 300; // 300ms between sound plays per key

    private static boolean canPlay(String key) {
        long now = System.currentTimeMillis();
        Long lastTime = lastPlayTime.get(key);
        if (lastTime == null || now - lastTime >= DEBOUNCE_TIME_MS) {
            lastPlayTime.put(key, now);
            return true;
        }
        return false;
    }

    public static void playItemSound(String itemTranslationKey) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || !canPlay("item:" + itemTranslationKey)) return;

        SoundEvent sound = switch (itemTranslationKey) {
            case "item.minecraft.ender_pearl" -> CustomSounds.ENDER_PEARL;
            case "item.minecraft.ender_pearl_last" -> CustomSounds.ENDER_PEARL_LAST;
            case "item.minecraft.flint_and_steel" -> CustomSounds.FLINTON_STEEL;
            case "block.minecraft.crafting_table" -> CustomSounds.CRAFTING_TABLE;
            case "block.minecraft.nether_portal" -> CustomSounds.NETHER;
            case "item.minecraft.water_bucket.release" -> CustomSounds.RELEASE;
            case "item.minecraft.water_bucket" -> CustomSounds.WATER_BUCKET;
            case "item.minecraft.elytra" -> CustomSounds.ELYTRA_WINGSUITS;
            case "steve.lava.chicken" -> CustomSounds.LAVA_CHICKEN;
            default -> null;
        };

        if (sound != null) {
            System.out.println("Playing item sound: " + sound.getId() + " for " + itemTranslationKey);
            MinecraftClient.getInstance().execute(() -> {
                player.playSound(sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
            });
        }
    }

    public static void playDimensionSound(String dimensionKey) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || !canPlay("dim:" + dimensionKey)) return;

        SoundEvent sound = switch (dimensionKey) {
            case "minecraft:overworld" -> CustomSounds.OVERWORLD;
            case "minecraft:the_nether" -> CustomSounds.NETHER;
            default -> null;
        };

        if (sound != null) {
            System.out.println("Playing dimension sound: " + sound.getId() + " for " + dimensionKey);
            MinecraftClient.getInstance().execute(() -> {
                player.playSound(sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
            });
        }
    }

    public static void playScreenSound(String screenKey) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || !canPlay("screen:" + screenKey)) return;

        SoundEvent sound = switch (screenKey) {
            case "net.minecraft.client.gui.screen.ingame.CraftingScreen" -> CustomSounds.CRAFTING_TABLE;
            case "net.minecraft.client.gui.screen.TitleScreen" -> CustomSounds.MINECRAFT;
            default -> null;
        };

        if (sound != null) {
            System.out.println("Playing screen sound: " + sound.getId() + " for " + screenKey);
            MinecraftClient.getInstance().execute(() -> {
                player.playSound(sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
            });
        }
    }

    public static void playMobSound(String mobKey) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || !canPlay("mob:" + mobKey)) return;

        SoundEvent sound = switch (mobKey) {
            case "entity.minecraft.chicken_jockey" -> CustomSounds.CHICKEN_JOCKEY;
            default -> null;
        };

        if (sound != null) {
            System.out.println("Playing mob sound: " + sound.getId() + " for " + mobKey);
            MinecraftClient.getInstance().execute(() -> {
                player.playSound(sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
            });
        }
    }

    public static void stopItemSound(String itemTranslationKey) {
        SoundEvent sound = switch (itemTranslationKey) {
            case "item.minecraft.ender_pearl_last" -> CustomSounds.ENDER_PEARL_LAST;
            case "steve.lava.chicken" -> CustomSounds.LAVA_CHICKEN;
            default -> null;
        };

        if (sound != null) {
            MinecraftClient.getInstance().execute(() -> {
                MinecraftClient.getInstance().getSoundManager().stopSounds(sound.getId(),SoundCategory.PLAYERS);
            });
        }
    }

}

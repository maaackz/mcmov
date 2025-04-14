package com.maaackz.mcmov.sound;

import com.maaackz.mcmov.MinecraftMovieMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class CustomSounds {
    private static final Map<String, SoundEvent> SOUND_MAP = new HashMap<>();

    public static final SoundEvent CHICKEN_JOCKEY = register("chicken_jockey");
    public static final SoundEvent ENDER_PEARL = register("ender_pearl");
    public static final SoundEvent ENDER_PEARL_LAST = register("ender_pearl_last");
    public static final SoundEvent WATER_BUCKET = register("water_bucket");
    public static final SoundEvent RELEASE = register("release");
    public static final SoundEvent ELYTRA_WINGSUITS = register("elytra_wingsuits");
    public static final SoundEvent FLINTON_STEEL = register("flinton_steel");
    public static final SoundEvent CRAFTING_TABLE = register("crafting_table");
    public static final SoundEvent OVERWORLD = register("overworld");
    public static final SoundEvent NETHER = register("nether");
    public static final SoundEvent THE_END = register("the_end");
    public static final SoundEvent MINECRAFT = register("minecraft");
    public static final SoundEvent LAVA_CHICKEN = register("lava_chicken");

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(MinecraftMovieMod.MOD_ID, name);
        SoundEvent sound = SoundEvent.of(id);
        Registry.register(Registries.SOUND_EVENT, id, sound);
        SOUND_MAP.put(name, sound);
        return sound;
    }

    public static void registerSounds() {
        MinecraftMovieMod.LOGGER.info("Registering sounds for " + MinecraftMovieMod.MOD_ID);
    }

    public static SoundEvent get(String key) {
        return SOUND_MAP.get(key);
    }
}

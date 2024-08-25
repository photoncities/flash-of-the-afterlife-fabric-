package net.dexo.fota.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static net.dexo.fota.FlashOfTheAfterlife.MOD_ID;


public class ModSounds{

    public static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(MOD_ID,name) ;
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
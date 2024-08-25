package net.dexo.fota;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.dexo.fota.sound.ModSounds.registerSoundEvent;


public class FlashOfTheAfterlife implements ModInitializer {
	public static final String MOD_ID = "fota";
	public static final Identifier FLASH_CLIENT_PACKET = Identifier.of(MOD_ID, "flashplayer");
	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playS2C().register(FlashPayload.ID, FlashPayload.CODEC);
//		LOGGER.info("Hello Fartbrick world!");

//		flash = registerSoundEvent("flash");
	}

//	public static void playSoundMethod(World world, PlayerEntity player) {
//		world.playSound(
//				player, // Player - if non-null, the sound will play only for this player
//				player.getX(), player.getY(), player.getZ(), // The position of the sound
//				flash, // The sound event
//				SoundCategory.PLAYERS, // The category of the sound
//				1f, // Volume
//				1f // Pitch
//		);
//	}
}


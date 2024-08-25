package net.dexo.fota;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dexo.fota.FlashPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.dexo.fota.FlashOfTheAfterlife.MOD_ID;
import static net.dexo.fota.sound.ModSounds.registerSoundEvent;

public class FlashOfTheAfterlifeClient implements ClientModInitializer {


    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Identifier IMAGE_LOCATION = new Identifier(MOD_ID, "textures/afterlife.png");
    public static final int FADE_DURATION = 10; // Duration of fade in ticks (3 seconds at 20 ticks per second)
    public static final int DISPLAY_DURATION = 0; // Duration to display before fading (1 second)

    public static long startTime = -1;
    public static boolean shouldRender = true;
    public static SoundEvent flash;


    @Override
    public void onInitializeClient() {

        LOGGER.info("Hello Fartbrick world!");
//		Registry.register(Registries.SOUND_EVENT, CUSTOM_SOUND_ID, CUSTOM_SOUND_EVENT);

        flash = registerSoundEvent("flash");

        ClientPlayNetworking.registerGlobalReceiver(FlashPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                LOGGER.info("Received payload");

                shouldRender = true;
                startTime = -1;
                HudRenderCallback.EVENT.register(this::renderImage);
            });
        });
    }

    private void renderImage(DrawContext drawContext, float v) {


        MinecraftClient client = MinecraftClient.getInstance();
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        long currentTime = client.world.getTime();
        if (startTime == -1) {
            startTime = currentTime;
        }

        long elapsedTime = currentTime - startTime;

        if (elapsedTime > DISPLAY_DURATION + FADE_DURATION) {
            shouldRender = false;
            return;
        }

        int imageWidth = 64;  // Replace with your image's width
        int imageHeight = 64; // Replace with your image's height
        int x = (screenWidth - imageWidth) / 2;
        int y = (screenHeight - imageHeight) / 2;

        float alpha = 1.0f;
        if (elapsedTime > DISPLAY_DURATION) {
            alpha = 1.0f - (float)(elapsedTime - DISPLAY_DURATION) / FADE_DURATION;
            LoggerFactory.getLogger(MOD_ID).info(String.valueOf(alpha));
        }

        RenderSystem.enableBlend();
        drawContext.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
        drawContext.drawTexture(IMAGE_LOCATION, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);



    }

}

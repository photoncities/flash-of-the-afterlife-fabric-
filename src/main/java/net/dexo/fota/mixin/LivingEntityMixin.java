package net.dexo.fota.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dexo.fota.FlashOfTheAfterlife;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.dexo.fota.FlashOfTheAfterlife.*;

@Mixin(LivingEntity.class)

public abstract class LivingEntityMixin {

    @Inject(method = "tryUseTotem", at = @At("HEAD"))
    private void onTotemUse(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof ServerPlayerEntity player) {
            ItemStack mainHand = player.getMainHandStack();
            ItemStack offHand = player.getOffHandStack();
            if (mainHand.isOf(Items.TOTEM_OF_UNDYING) || offHand.isOf(Items.TOTEM_OF_UNDYING)) {
//                entity.playSound(MY_SOUND_EVENT);
                FlashOfTheAfterlife.playSoundMethod(((Entity)(Object)this).getWorld(),player);
                LoggerFactory.getLogger(MOD_ID).info("WOW!!!");
                shouldRender = true;
                startTime = -1;
                HudRenderCallback.EVENT.register(this::renderImage);

            }
        }
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


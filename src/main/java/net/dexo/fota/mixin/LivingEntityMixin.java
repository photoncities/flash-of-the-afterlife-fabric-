package net.dexo.fota.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dexo.fota.FlashOfTheAfterlife;
import net.dexo.fota.FlashPayload;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.dexo.fota.FlashOfTheAfterlife.*;
import static net.dexo.fota.FlashOfTheAfterlifeClient.*;

@Mixin(LivingEntity.class)

public abstract class LivingEntityMixin {


    @Inject(method = "tryUseTotem", at = @At("HEAD"))
    private void onTotemUse(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof ServerPlayerEntity player) {
            ItemStack mainHand = player.getMainHandStack();
            ItemStack offHand = player.getOffHandStack();
            if (mainHand.isOf(Items.TOTEM_OF_UNDYING) || offHand.isOf(Items.TOTEM_OF_UNDYING)) {
                entity.playSound(flash);
//                FlashOfTheAfterlife.playSoundMethod(((Entity)(Object)this).getWorld(),player);
                LoggerFactory.getLogger(MOD_ID).info("WOW!!!");

                ServerPlayNetworking.send(player, new FlashPayload(new BlockPos((int)player.getX(), (int)player.getY(), (int)player.getZ())));
            }
        }
    }


}


package net.dexo.fota;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

import static net.dexo.fota.FlashOfTheAfterlife.FLASH_CLIENT_PACKET;


public record FlashPayload(BlockPos blockPos) implements CustomPayload {

    public static final Id<FlashPayload> ID = new Id<>(FLASH_CLIENT_PACKET);
    public static final PacketCodec<RegistryByteBuf, FlashPayload> CODEC = PacketCodec.tuple(BlockPos.PACKET_CODEC, FlashPayload::blockPos, FlashPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

package com.mallowwww.serverutils.packet;

import com.mallowwww.serverutils.ServerUtilsMod;
import com.mallowwww.serverutils.screen.MOTDScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.MenuProvider;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class MOTDAskPacket {
    public record ToServer() implements CustomPacketPayload {
        public static final Type<ToServer> TYPE = new Type<>(
                ServerUtilsMod.path("motd_to_server")
        );

        public static final StreamCodec<FriendlyByteBuf, ToServer> CODEC = StreamCodec.ofMember(
                ToServer::encode,
                ToServer::decode
        );

        public static void encode(@NotNull ToServer packet, @NotNull FriendlyByteBuf buf) {}
        public static ToServer decode(@NotNull FriendlyByteBuf buf) {
            return new ToServer();
        }
        public static void handle(@NotNull ToServer packet, IPayloadContext x) {
            // Happens on the server, grab the player and give them the MOTD information
            x.reply(new ToClient(
                    true, false, "https://landfall.world"
            ));

        }

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
    public record ToClient(boolean hasMOTD, boolean needsToAccept, String URL) implements CustomPacketPayload {
        public static final Type<ToClient> TYPE = new Type<>(
                ServerUtilsMod.path("motd_to_client")
        );
        public static final StreamCodec<FriendlyByteBuf, ToClient> CODEC = StreamCodec.ofMember(
                ToClient::encode,
                ToClient::decode
        );

        public static void encode(ToClient packet, FriendlyByteBuf buf) {
            var bytes = packet.URL.getBytes(StandardCharsets.UTF_8);
            buf.writeByte(bytes.length);
            buf.writeBoolean(packet.hasMOTD);
            buf.writeBoolean(packet.needsToAccept);
            buf.writeByteArray(bytes);
        }
        public static ToClient decode(FriendlyByteBuf buf) {
            var length = buf.readByte();
            return new ToClient(
                    buf.readBoolean(),
                    buf.readBoolean(),
                    new String(buf.readByteArray(length), StandardCharsets.UTF_8)
            );
        }

        public static void handle(@NotNull ToClient packet, IPayloadContext x) {
            // Happens on the client, set the screen and do all the things
            Minecraft.getInstance().setScreen(
                    new MOTDScreen(packet.needsToAccept, packet.URL)
            );
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}

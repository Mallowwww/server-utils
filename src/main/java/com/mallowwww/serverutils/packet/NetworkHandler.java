package com.mallowwww.serverutils.packet;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber
public class NetworkHandler {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1");
        registrar.playToClient(
                MOTDAskPacket.ToClient.TYPE,
                MOTDAskPacket.ToClient.CODEC,
                MOTDAskPacket.ToClient::handle
        );
        registrar.playToServer(
                MOTDAskPacket.ToServer.TYPE,
                MOTDAskPacket.ToServer.CODEC,
                MOTDAskPacket.ToServer::handle
        );
    }
}

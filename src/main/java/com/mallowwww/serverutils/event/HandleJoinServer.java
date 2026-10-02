package com.mallowwww.serverutils.event;

import com.mallowwww.serverutils.packet.MOTDAskPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.telemetry.events.GameLoadTimesEvent;
import net.minecraft.util.profiling.jfr.event.WorldLoadFinishedEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class HandleJoinServer {
    private static int countdown = -1;
    @SubscribeEvent
    public static void joinServer(ClientPlayerNetworkEvent.LoggingIn event) {
        countdown = 100;
    }
    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event) {
        if (countdown == 0) {
            Minecraft.getInstance().getConnection().send(new MOTDAskPacket.ToServer());
        }
        if (countdown >= 0)
            countdown--;
    }
}

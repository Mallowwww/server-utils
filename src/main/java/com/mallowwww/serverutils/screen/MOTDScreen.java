package com.mallowwww.serverutils.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class MOTDScreen extends Screen {
    private final boolean needsToAccept;
    private final String URL;

    public MOTDScreen(boolean needsToAccept, String URL) {
        super(Component.literal("MOTD"));
        this.needsToAccept = needsToAccept;
        this.URL = URL;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderBlurredBackground(partialTick);

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

package com.example.simplehud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SimpleHudMod implements ClientModInitializer {
    private static final int COLOR = 0xFFFFFFFF;
    private static final int BG = 0x90000000;
    private static final DateTimeFormatter CLOCK = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void onInitializeClient() {
        // Renders just before chat; inherits vanilla's "hide HUD on F1" behaviour.
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                Identifier.of("simplehud", "info"),
                SimpleHudMod::render);
    }

    private static void render(DrawContext ctx, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.world == null) return;

        List<String> lines = new ArrayList<>();
        lines.add("FPS: " + mc.getCurrentFps());

        lines.add(String.format(Locale.ROOT, "XYZ: %.1f / %.1f / %.1f",
                mc.player.getX(), mc.player.getY(), mc.player.getZ()));

        String facing = mc.player.getHorizontalFacing().asString();
        lines.add("Facing: " + Character.toUpperCase(facing.charAt(0)) + facing.substring(1));

        lines.add("Clock: " + LocalTime.now().format(CLOCK));

        if (mc.getNetworkHandler() != null) {
            PlayerListEntry entry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
            if (entry != null) lines.add("Ping: " + entry.getLatency() + " ms");
        }

        TextRenderer font = mc.textRenderer;
        int x = 6, y = 6, lineH = font.fontHeight + 2;
        int width = 0;
        for (String s : lines) width = Math.max(width, font.getWidth(s));

        ctx.fill(x - 3, y - 3, x + width + 3, y + lines.size() * lineH + 1, BG);
        for (String s : lines) {
            ctx.drawTextWithShadow(font, s, x, y, COLOR);
            y += lineH;
        }
    }
}

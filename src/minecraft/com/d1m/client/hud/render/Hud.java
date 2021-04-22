package com.d1m.client.hud.render;

import com.d1m.client.D1m;
import com.d1m.client.management.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class Hud extends GuiIngame {

    public static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

    public Hud(Minecraft mcIn) {
        super(mcIn);
    }

    public static void arrayList() {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        AtomicInteger arrayoffset = new AtomicInteger(3);
        final int[] yDist = {2};
        D1m.instance.moduleManager.getModuleList().stream().filter(Module::isToggled).sorted(Comparator.comparingInt(module -> -fr.getStringWidth(module.getDisplayName()))).forEach(module -> {
            Gui.drawRect(sr.getScaledWidth() - Minecraft.getMinecraft().fontRendererObj.getStringWidth(module.getDisplayName()) - 6, arrayoffset.get() - 3, sr.getScaledWidth() - Minecraft.getMinecraft().fontRendererObj.getStringWidth(module.getDisplayName()) - 7, arrayoffset.get() + 8, 0xffcc44ff);
            Gui.drawRect(sr.getScaledWidth() - 1, arrayoffset.get() - 3, sr.getScaledWidth(), arrayoffset.get() + 8, 0xffcc44ff);
            fr.drawString(module.getDisplayName(), sr.getScaledWidth() - 3 - fr.getStringWidth(module.getDisplayName()), yDist[0], 0xffcc44ff);
            yDist[0] += fr.FONT_HEIGHT + 2;
            arrayoffset.addAndGet(fr.FONT_HEIGHT + 2);
        });
    }

    public static void clientName() {
        fr.drawStringWithShadow(D1m.clientName.substring(0), 24.0f, 3.0f, 0xffcc44ff);
    }

}

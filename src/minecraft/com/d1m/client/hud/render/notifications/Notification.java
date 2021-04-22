package com.d1m.client.hud.render.notifications;

import com.d1m.client.D1m;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Notification
{
    private NotificationType type;
    private String title;
    private String message;
    private long start;
    private long fadedIn;
    private long fadeOut;
    private long end;
    private int yOffset;

    public Notification(final NotificationType type, final String title, final String message, final int length) {
        this.type = type;
        this.title = title;
        this.message = message;
        this.fadedIn = 200 * length;
        this.fadeOut = this.fadedIn + 500 * length;
        this.end = this.fadeOut + this.fadedIn;
    }

    public void show() {
        this.start = System.currentTimeMillis();
    }

    public void resetOffset() {
        this.yOffset = D1m.instance.notificationManager.getIndex(this) * 40;
    }

    public void updateOffset() {
        int changeOffset = 0;
        if (this.getTime() > this.fadeOut) {
            changeOffset = 40 - (int)(Math.tanh(3.0 - (this.getTime() - this.fadeOut) / (double)(this.end - this.fadeOut) * 3.0) * 40.0);
        }
        else {
            changeOffset = 0;
        }
        for (final Notification notification : D1m.instance.notificationManager.getNotifications()) {
            if (D1m.instance.notificationManager.getIndex(notification) > D1m.instance.notificationManager.getIndex(this)) {
                notification.changeOffset(Math.min(changeOffset, 40));
            }
        }
    }

    public void changeOffset(final int offset) {
        this.yOffset -= offset;
    }

    public boolean isShown() {
        return this.getTime() <= this.end;
    }

    public int fadingOutProgress() {
        if (this.getTime() > this.fadeOut && this.end - this.getTime() != 0L) {
            return 40 - (int)(Math.tanh(3.0 - (this.getTime() - this.fadeOut) / (double)(this.end - this.fadeOut) * 3.0) * 40.0);
        }
        return 0;
    }

    private long getTime() {
        return System.currentTimeMillis() - this.start;
    }

    public void render() {
        if (!this.isShown()) {
            D1m.instance.notificationManager.removeFromList(this);
            return;
        }
        if (D1m.instance.notificationManager.getIndex(this) == 0) {
            D1m.instance.notificationManager.setLastNotif(this);
        }
        double offset = 0.0;
        final int width = 120;
        final int height = 30;
        final long time = this.getTime();
        if (time < this.fadedIn) {
            offset = Math.tanh(time / (double)this.fadedIn * 3.0) * 120.0;
        }
        else if (time > this.fadeOut) {
            offset = Math.tanh(3.0 - (time - this.fadeOut) / (double)(this.end - this.fadeOut) * 3.0) * 120.0;
        }
        else {
            offset = 120.0;
        }
        Color color = new Color(0, 0, 0, 220);
        if (this.type == NotificationType.INFO) {
            final Color color2 = new Color(0, 26, 169);
        }
        else if (this.type == NotificationType.WARNING) {
            final Color color2 = new Color(204, 193, 0);
        }
        else {
            final Color color2 = new Color(204, 0, 18);
            final int i = Math.max(0, Math.min(255, (int)(Math.sin(time / 100.0) * 255.0 / 2.0 + 127.5)));
            color = new Color(i, 0, 0, 220);
        }
        final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        Gui.drawRect(GuiScreen.width - offset, GuiScreen.height - 5 - 30 - this.yOffset, GuiScreen.width, GuiScreen.height - 5 - this.yOffset, color.getRGB());
        Gui.drawRect(GuiScreen.width - offset, GuiScreen.height - 5 - 30 - this.yOffset, GuiScreen.width - offset - 1.0, GuiScreen.height - 5 - this.yOffset, 0xffcc44ff);
        Gui.drawRect(GuiScreen.width - offset, GuiScreen.height - 5 - 30 - this.yOffset, GuiScreen.width, GuiScreen.height - 34 - this.yOffset, 0xffcc44ff);
        Gui.drawRect(GuiScreen.width - 1 - offset, GuiScreen.height + 25 - 30 - this.yOffset, GuiScreen.width, GuiScreen.height - 4 - this.yOffset, 0xffcc44ff);
        GL11.glPushMatrix();
        GlStateManager.translate(GuiScreen.width - offset + 8.0, GuiScreen.height - 2 - 30 - this.yOffset, 0.0);
        GlStateManager.scale(Math.min(108.0 / fontRenderer.getStringWidth(this.title), 1.0), Math.min(108.0 / fontRenderer.getStringWidth(this.title), 1.0), 1.0);
        GlStateManager.translate(-(GuiScreen.width - offset + 8.0), -(GuiScreen.height - 2 - 30 - this.yOffset), 0.0);
        fontRenderer.drawString(this.title, (int)(GuiScreen.width - offset + 8.0), GuiScreen.height - 2 - 30 - this.yOffset, -1);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GlStateManager.translate(GuiScreen.width - offset + 8.0, GuiScreen.height + 12 - 30 - this.yOffset, 0.0);
        GlStateManager.scale(Math.min(108.0 / fontRenderer.getStringWidth(this.message), 1.0), Math.min(108.0 / fontRenderer.getStringWidth(this.message), 1.0), 1.0);
        GlStateManager.translate(-(GuiScreen.width - offset + 8.0), -(GuiScreen.height + 12 - 30 - this.yOffset), 0.0);
        fontRenderer.drawString(this.message, (int)(GuiScreen.width - offset + 8.0), GuiScreen.height + 8 - 30 - this.yOffset, -1);
        GL11.glPopMatrix();
    }
}

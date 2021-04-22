package com.d1m.client.hud.menu.button;

import com.d1m.client.D1m;
import com.d1m.client.management.utils.animations.AnimationUtil;
import com.d1m.client.management.utils.animations.easings.Quint;
import com.d1m.client.management.utils.animations.easings.utilities.Progression;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiTexturedButton extends GuiButton {

    public static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    private ResourceLocation resourceLocation;

    private double scaleFactor;

    private AnimationUtil animUtil;
    private Progression progIn, progOut;

    private double startPosX, startPosY;

    private boolean scissor;

    public GuiTexturedButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String imageLocation) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        init(imageLocation, x, y, false);
    }

    public GuiTexturedButton(int buttonId, int x, int y, int widthIn, int heightIn, String imageLocation) {
        super(buttonId, x, y, widthIn, heightIn, "");
        init(imageLocation, x, y, false);
    }

    public GuiTexturedButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String imageLocation, boolean scissor) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        init(imageLocation, x, y, scissor);
    }

    public GuiTexturedButton(int buttonId, int x, int y, int widthIn, int heightIn, String imageLocation, boolean scissor) {
        super(buttonId, x, y, widthIn, heightIn, "");
        init(imageLocation, x, y, scissor);
    }

    private void init(String imageLocation, double x, double y, boolean scissor) {
        this.resourceLocation = new ResourceLocation(imageLocation);
        //this.animUtil = new AnimationUtil(Quint.class);
        this.animUtil = new AnimationUtil(Quint.class);
        this.progIn = new Progression();
        this.progOut = new Progression();
        this.startPosX = x;
        this.startPosY = y;
        this.scissor = scissor;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        D1m.RENDER2D.color(Color.white);
        updateButton(mouseX, mouseY);

        D1m.RENDER2D.push();
        if (scissor) {
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            D1m.RENDER2D.scissor(startPosX, startPosY + 1, width, height - 1);
        }
        D1m.RENDER2D.translate(this.xPosition + this.width / 2, this.yPosition + this.height / 2);
        //D1m.RENDER2D.rect(-width / 2, -height / 2, width, height, new Color(0, 0, 0, 200));
        D1m.RENDER2D.scale(1 + scaleFactor, 1 + scaleFactor);
        D1m.RENDER2D.image(resourceLocation, -this.width / 2 + 10, -this.height / 2 + 10, this.width - 20, this.height - 20);
        if (scissor) GL11.glDisable(GL11.GL_SCISSOR_TEST);
        D1m.RENDER2D.pop();
        if (!displayString.isEmpty())
            fr.drawStringWithShadow(displayString, xPosition + width / 2 - fr.getStringWidth(displayString) /  2, yPosition + height / 2 - fr.FONT_HEIGHT / 2, -1);
        if (scissor) D1m.RENDER2D.outlineInlinedGradientRect(startPosX, startPosY, width, height, 5, Color.black, new Color(0, 0, 0, 0));
    }

    public final void updateButton(int mouseX, int mouseY) {
        this.hovered = (mouseX > xPosition && mouseX < xPosition + width) && (mouseY > yPosition && mouseY < yPosition + height);
        if (this.hovered) {
            progOut.setValue(0);
            scaleFactor = animUtil.easeOut(progIn, 0, .2F, .5);
        } else {
            progIn.setValue(0);
            scaleFactor = .2F - animUtil.easeOut(progOut, 0, .2F, .5);
        }
        this.scaleFactor = MathHelper.clamp_double(scaleFactor, 0, .2F);
    }

    public final double getStartPosX() {
        return startPosX;
    }

    public final double getStartPosY() {
        return startPosY;
    }

}

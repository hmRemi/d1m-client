package com.d1m.client.management.cosmetics.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CosmeticWings extends ModelBase {

    private Minecraft mc;
    private ResourceLocation location;
    private ModelRenderer wing;
    private ModelRenderer wingTip;
    private boolean playerUsesFullHeight;
    public static CosmeticWings renderWingsObject;

    public CosmeticWings() {
        this.mc = Minecraft.getMinecraft();
        this.location = new ResourceLocation("d1m/cosmetics/wings.png");
        this.playerUsesFullHeight = true;
        this.setTextureOffset("wing.bone", 0, 0);
        this.setTextureOffset("wing.skin", -10, 8);
        this.setTextureOffset("wingtip.bone", 0, 5);
        this.setTextureOffset("wingtip.skin", -10, 18);
        (this.wing = new ModelRenderer(this, "wing")).setTextureOffset(30, 30);
        this.wing.setRotationPoint(-2.0f, 0.0f, 0.0f);
        this.wing.addBox("bone", -10.0f, -1.0f, -1.0f, 10, 2, 2);
        this.wing.addBox("skin", -10.0f, 0.0f, 0.5f, 10, 0, 10);
        (this.wingTip = new ModelRenderer(this, "wingtip")).setTextureSize(30, 30);
        this.wingTip.setRotationPoint(-10.0f, 0.0f, 0.0f);
        this.wingTip.addBox("bone", -10.0f, -0.5f, -0.5f, 10, 1, 1);
        this.wingTip.addBox("skin", -10.0f, 0.0f, 0.5f, 10, 0, 10);
        this.wing.addChild(this.wingTip);
    }

    public void onRenderPlayer(final EntityPlayer player, final float partialTick) {
        if(player.equals(this.mc.thePlayer) && !player.isInvisible()) {
            this.renderWings(player, partialTick);
        }
    }

    private void renderWings(final EntityPlayer player, final float partialTicks) {
        final double scale1 = 80.0;
        final double scale2 = scale1 / 100.0;
        final double rotate = this.interpolate(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);
        GL11.glPushMatrix();
        GL11.glScaled(-scale2, -scale2, scale2);
        GL11.glRotated(180.0 + rotate, 0.0, 1.0, 0.0);
        GL11.glTranslated(0.0, -1.28 / scale2, 0.0);
        GL11.glTranslated(0.0, 0.0, 0.1 / scale2);
        if (player.isSneaking()) {
            GL11.glTranslated(0.0, 0.125 / scale2, 0.0);
        }
        final float[] colors = { 1.0f, 1.0f, 1.0f };
        GL11.glColor3f(colors[0], colors[1], colors[2]);
        this.mc.getTextureManager().bindTexture(this.location);
        for (int j = 0; j < 2; ++j) {
            GL11.glEnable(2884);
            final float f11 = System.currentTimeMillis() % 1000L / 1000.0f * 3.1415927f * 2.0f;
            this.wing.rotateAngleX = (float)Math.toRadians(-80.0) - (float)Math.cos(f11) * 0.2f;
            this.wing.rotateAngleY = (float)Math.toRadians(40.0) + (float)Math.sin(f11) * 0.4f;
            this.wing.rotateAngleZ = (float)Math.toRadians(20.0);
            this.wingTip.rotateAngleZ = -(float)(Math.sin(f11 + 2.0f) + 0.5) * 0.75f;
            this.wing.render(0.0625f);
            GL11.glScalef(-1.0f, 1.0f, 1.0f);
            if (j == 0) {
                GL11.glCullFace(1028);
            }
        }
        GL11.glCullFace(1029);
        GL11.glDisable(2884);
        GL11.glColor3f(255.0f, 255.0f, 255.0f);
        GL11.glPopMatrix();
    }

    private float interpolate(final float yaw1, final float yaw2, final float percent) {
        float f = (yaw1 + (yaw2 - yaw1) * percent) % 360.0f;
        if (f < 0.0f) {
            f += 360.0f;
        }
        return f;
    }

    public static CosmeticWings getWings() {
        if (CosmeticWings.renderWingsObject == null) {
            CosmeticWings.renderWingsObject = new CosmeticWings();
        }
        return CosmeticWings.renderWingsObject;
    }
}

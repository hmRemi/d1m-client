package com.d1m.client.management.utils;

import net.minecraft.client.Minecraft;

public class MovementUtils {

    public final static boolean isMoving() {
        return Minecraft.getMinecraft().thePlayer != null && (Minecraft.getMinecraft().thePlayer.movementInput.moveForward != 0 || Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe != 0);
    }

    public final static void doStrafe(double speed) {
        if(!isMoving())  return;

        final double yaw = getYaw(true);
        Minecraft.getMinecraft().thePlayer.motionX = -Math.sin(yaw) * speed;
        Minecraft.getMinecraft().thePlayer.motionZ = Math.cos(yaw) * speed;
    }

    public final static double getYaw(boolean strafing) {
        float rotationYaw = strafing ? Minecraft.getMinecraft().thePlayer.rotationYawHead : Minecraft.getMinecraft().thePlayer.rotationYaw;
        float forward = 1F;

        final double moveForward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
        final double moveStrafing = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
        final float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;

        if (moveForward < 0) {
            rotationYaw += 180F;
        }

        if (moveForward < 0) {
            forward = -0.5F;
        } else if(moveForward > 0) {
            forward = 0.5F;
        }

        if (moveStrafing > 0) {
            rotationYaw -= 90F * forward;
        } else if(moveStrafing < 0) {
            rotationYaw += 90F * forward;
        }

        return Math.toRadians(rotationYaw);
    }

}

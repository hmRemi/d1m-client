package com.d1m.client.management.utils.combat;

import com.d1m.client.D1m;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtil
{
    private static final Minecraft MC;
    public float serverYaw;
    public float serverPitch;

    public final float[] getRotations(final Entity entity, final boolean predict, final double predictionFactor) {
        final Vec3 playerPos = new Vec3(RotationUtil.MC.thePlayer.posX + (predict ? (RotationUtil.MC.thePlayer.motionX * predictionFactor) : 0.0), RotationUtil.MC.thePlayer.posY + ((entity instanceof EntityLivingBase) ? RotationUtil.MC.thePlayer.getEyeHeight() : 0.0f) + (predict ? (RotationUtil.MC.thePlayer.motionY * predictionFactor) : 0.0), RotationUtil.MC.thePlayer.posZ + (predict ? (RotationUtil.MC.thePlayer.motionZ * predictionFactor) : 0.0));
        final Vec3 entityPos = new Vec3(entity.posX + (predict ? ((entity.posX - entity.prevPosX) * predictionFactor) : 0.0), entity.posY + (predict ? ((entity.posY - entity.prevPosY) * predictionFactor) : 0.0), entity.posZ + (predict ? ((entity.posZ - entity.prevPosZ) * predictionFactor) : 0.0));
        final double diffX = entityPos.xCoord - playerPos.xCoord;
        final double diffY = (entity instanceof EntityLivingBase) ? (entityPos.yCoord + ((EntityLivingBase)entity).getEyeHeight() - playerPos.yCoord) : (entityPos.yCoord - playerPos.yCoord);
        final double diffZ = entityPos.zCoord - playerPos.zCoord;
        final double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final double yaw = Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0;
        final double pitch = -Math.toDegrees(Math.atan2(diffY, dist));
        return new float[] { (float)yaw, (float)pitch };
    }

    public final float[] getRotations(final Vec3 pos, final boolean predict, final double predictionFactor) {
        final Vec3 playerPos = new Vec3(RotationUtil.MC.thePlayer.posX + (predict ? (RotationUtil.MC.thePlayer.motionX * predictionFactor) : 0.0), RotationUtil.MC.thePlayer.posY + (predict ? (RotationUtil.MC.thePlayer.motionY * predictionFactor) : 0.0), RotationUtil.MC.thePlayer.posZ + (predict ? (RotationUtil.MC.thePlayer.motionZ * predictionFactor) : 0.0));
        final double diffX = pos.xCoord + 0.5 - playerPos.xCoord;
        final double diffY = pos.yCoord + 0.5 - (playerPos.yCoord + RotationUtil.MC.thePlayer.getEyeHeight());
        final double diffZ = pos.zCoord + 0.5 - playerPos.zCoord;
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        double yaw = Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0;
        double pitch = -Math.toDegrees(Math.atan2(diffY, dist));
        yaw = RotationUtil.MC.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_double(yaw - RotationUtil.MC.thePlayer.rotationYaw);
        pitch = RotationUtil.MC.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_double(pitch - RotationUtil.MC.thePlayer.rotationPitch);
        return new float[] { (float)yaw, (float)pitch };
    }

    public final Vec3 getVectorForRotation(final float yaw, final float pitch) {
        final double f = Math.cos(Math.toRadians(-yaw) - 3.141592653589793);
        final double f2 = Math.sin(Math.toRadians(-yaw) - 3.141592653589793);
        final double f3 = -Math.cos(Math.toRadians(-pitch));
        final double f4 = Math.sin(Math.toRadians(-pitch));
        return new Vec3(f2 * f3, f4, f * f3);
    }

    public final float getDifference(final float a, final float b) {
        float r = (float)((a - b) % 360.0);
        if (r < -180.0) {
            r += 360.0;
        }
        if (r >= 180.0) {
            r -= 360.0;
        }
        return r;
    }

    public final double getRotationDifference(final float[] clientRotations, final float[] serverRotations) {
        return Math.hypot(this.getDifference(clientRotations[0], serverRotations[0]), clientRotations[1] - serverRotations[1]);
    }

    public final double getRotationDifference(final Entity entity) {
        final float[] rotations = this.getRotations(entity, false, 1.0);
        return this.getRotationDifference(rotations, new float[] { RotationUtil.MC.thePlayer.rotationYaw, RotationUtil.MC.thePlayer.rotationPitch });
    }

    public final float[] smoothRotation(final float[] currentRotations, final float[] neededRotations, final float rotationSpeed) {
        final float yawDiff = this.getDifference(neededRotations[0], currentRotations[0]);
        final float pitchDiff = this.getDifference(neededRotations[1], currentRotations[1]);
        float rotationSpeedYaw = rotationSpeed;
        if (yawDiff > rotationSpeed) {
            rotationSpeedYaw = rotationSpeed;
        }
        else {
            rotationSpeedYaw = Math.max(yawDiff, -rotationSpeed);
        }
        float rotationSpeedPitch = rotationSpeed;
        if (pitchDiff > rotationSpeed) {
            rotationSpeedPitch = rotationSpeed;
        }
        else {
            rotationSpeedPitch = Math.max(pitchDiff, -rotationSpeed);
        }
        final float newYaw = currentRotations[0] + rotationSpeedYaw;
        final float newPitch = currentRotations[1] + rotationSpeedPitch;
        return new float[] { newYaw, newPitch };
    }

    public final boolean isFaced(final double range) {
        return D1m.RAYCAST_UTIL.raycastEntity(range, new float[] { this.serverYaw, this.serverPitch }) != null;
    }

    public final void setRotations(final float yaw, final float pitch) {
        this.serverYaw = yaw;
        this.serverPitch = pitch;
    }

    public final void setRotations(final float[] rotations) {
        this.setRotations(rotations[0], rotations[1]);
    }

    public final float[] getServerRotations() {
        return new float[] { this.serverYaw, this.serverPitch };
    }

    static {
        MC = Minecraft.getMinecraft();
    }
}
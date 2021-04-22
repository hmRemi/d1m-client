package com.d1m.client.management.module.impl.movement;

import com.d1m.client.management.event.EventTarget;
import com.d1m.client.management.event.events.update.EventUpdate;
import com.d1m.client.management.module.Category;
import com.d1m.client.management.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {

    private boolean boosted;
    double Motion;
    double speed;

    public Speed() {
        super("Speed", Keyboard.KEY_V, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.setDisplayName("Speed");
        Speed.mc.gameSettings.keyBindSprint.pressed = true;
        if (Speed.mc.thePlayer.onGround) {
            Speed.mc.gameSettings.keyBindJump.pressed = false;
            Speed.mc.thePlayer.jump();
            this.boosted = false;
        } else {
            this.speed = 1.0034;
            this.Motion = Math.sqrt(Speed.mc.thePlayer.motionX * Speed.mc.thePlayer.motionX + Speed.mc.thePlayer.motionZ * Speed.mc.thePlayer.motionZ);
            if (Speed.mc.thePlayer.hurtTime < 5) {
                Speed.mc.thePlayer.motionX = -Math.sin(getDirection()) * this.speed * this.Motion;
                Speed.mc.thePlayer.motionZ = Math.cos(getDirection()) * this.speed * this.Motion;
                if (!this.boosted) {
                    Speed.mc.timer.timerSpeed = 1.05f;
                    final double motionV = 0.12;
                    final double x = this.getPosForSetPosX(motionV);
                    final double z = this.getPosForSetPosZ(motionV);
                    Speed.mc.thePlayer.motionX += x;
                    Speed.mc.thePlayer.motionZ += z;
                    this.boosted = true;
                }
            }
        }
    }

    public double getPosForSetPosX(final double value) {
        final double yaw = Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw);
        final double x = -Math.sin(yaw) * value;
        return x;
    }

    public double getPosForSetPosZ(final double value) {
        final double yaw = Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw);
        final double z = Math.cos(yaw) * value;
        return z;
    }

    public static float getDirection() {
        float var1 = Speed.mc.thePlayer.rotationYaw;
        if (Speed.mc.thePlayer.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Speed.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (Speed.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Speed.mc.thePlayer.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Speed.mc.thePlayer.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        var1 *= 0.017453292f;
        return var1;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Speed.mc.timer.timerSpeed = 1.0f;
    }

}

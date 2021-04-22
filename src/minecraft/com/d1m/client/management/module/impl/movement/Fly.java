package com.d1m.client.management.module.impl.movement;

import com.d1m.client.management.event.EventTarget;
import com.d1m.client.management.event.events.update.EventUpdate;
import com.d1m.client.management.module.Category;
import com.d1m.client.management.module.Module;
import com.d1m.client.management.utils.MovementUtils;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {

    public Fly() {
        super("Fly", Keyboard.KEY_F, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (MovementUtils.isMoving()) {
            if (mc.thePlayer.ticksExisted % 3 == 0) {
                mc.thePlayer.motionY = 0;
                mc.timer.timerSpeed = 1F;
                MovementUtils.doStrafe(1);
            } else {
                mc.thePlayer.motionY = 0;
                mc.timer.timerSpeed = .5F;
                MovementUtils.doStrafe(.2);
            }
        } else {
            stop(true);
            mc.timer.timerSpeed = 1F;
        }
    }

    public final void stop(boolean y) {
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
        if (y) mc.thePlayer.motionY = 0;
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1f;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}

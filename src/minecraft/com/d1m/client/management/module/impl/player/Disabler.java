package com.d1m.client.management.module.impl.player;

import com.d1m.client.D1m;
import com.d1m.client.management.event.EventTarget;
import com.d1m.client.management.event.events.update.EventUpdate;
import com.d1m.client.management.event.events.packet.EventSendPacket;
import com.d1m.client.management.module.Category;
import com.d1m.client.management.module.Module;
import com.d1m.client.management.utils.MathUtils;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.*;

import java.util.Random;

public class Disabler extends Module {

    public Disabler() {
        super("Disabler", 0, Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(D1m.instance.moduleManager.getModByName("Fly").isToggled()) {
            PlayerCapabilities playerCapabilities = new PlayerCapabilities();
            playerCapabilities.isFlying = true;
            playerCapabilities.allowFlying = true;
            playerCapabilities.setFlySpeed((float) MathUtils.randomNumber(10.1D, 19.0D));
            mc.getNetHandler().addToSendQueue(new C13PacketPlayerAbilities(playerCapabilities));
        }
    }

    public void onSendPacket(EventSendPacket event) {
        if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
            C0FPacketConfirmTransaction packetConfirmTransaction = (C0FPacketConfirmTransaction)event.getPacket();
            mc.getNetHandler().addToSendQueue(new C0FPacketConfirmTransaction(Integer.MAX_VALUE, packetConfirmTransaction.getUid(), false));
            event.setCancelled(true);
        }

        if (event.getPacket() instanceof C00PacketKeepAlive) {
            mc.getNetHandler().addToSendQueue(new C00PacketKeepAlive(Integer.MIN_VALUE + (new Random()).nextInt(100)));
            event.setCancelled(true);
        }
    }
}

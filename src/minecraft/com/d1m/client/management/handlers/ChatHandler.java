package com.d1m.client.management.handlers;

import com.d1m.client.D1m;
import com.d1m.client.management.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;

public class ChatHandler {

    public static void addChatMessage(final String s) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§7[§dD1m§7] " + s));
    }

    public static boolean onSendChatMessage(final String s) {
        if (s.startsWith(".")) {
            D1m.instance.cmdManager.callCMD(s.substring(1));
            return false;
        }
        for (final Module m : D1m.instance.moduleManager.getModuleList()) {
            if (m.isToggled()) {
                return m.onSendChatMessage(s);
            }
        }
        return true;
    }

    public static boolean onReceiveChatMessage(final S02PacketChat packet) {
        for (final Module m : D1m.instance.moduleManager.getModuleList()) {
            if (m.isToggled()) {
                return m.onReceiveChatMessage(packet);
            }
        }
        return true;
    }
}

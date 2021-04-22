package com.d1m.client.management.module;

import com.d1m.client.D1m;
import com.d1m.client.hud.render.notifications.Notification;
import com.d1m.client.hud.render.notifications.NotificationManager;
import com.d1m.client.hud.render.notifications.NotificationType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.EnumChatFormatting;

public class Module {

    private static final Minecraft MC = Minecraft.getMinecraft();
    public static Minecraft mc = Minecraft.getMinecraft();
    private String name, displayName;
    private static String suffix;
    private Category category;
    protected boolean toggled;
    private int key;

    public Module(String name, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
        toggled = false;
    }

    public void onRender() {}

    public void onEnable() {
        D1m.instance.eventManager.register(this);
    }

    public void onDisable() {
        D1m.instance.eventManager.unregister(this);
    }

    public void onToggle() {

    }

    public void toggle() {
        toggled = !toggled;
        onToggle();
        if (toggled) {
            NotificationManager.show(NotificationType.INFO, "", "§aEnabled §r" + this.getName(), 2);
            onEnable();
        } else {
            NotificationManager.show(NotificationType.INFO, "", "§cDisabled §r" + this.getName(), 2);
            onDisable();
        }
    }

    public void setState(boolean state) {
        this.toggle();
        if (state) {
            this.onEnable();
            NotificationManager.show(NotificationType.INFO, "", "§aEnabled §r" + this.getName(), 2);
            this.toggled = true;
        } else {
            this.onDisable();
            NotificationManager.show(NotificationType.INFO, "", "§cDisabled §r" + this.getName(), 2);
            this.toggled = false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isToggled() {
        return toggled;
    }

    public String getDisplayName() {
        return displayName == null ? name : displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Module module(final String mod) {
        return D1m.instance.moduleManager.getModByName(mod);
    }

    public boolean onSendChatMessage(String s) {
        return true;
    }

    public boolean onReceiveChatMessage(final S02PacketChat packet) {
        return true;
    }

    public static String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }


}

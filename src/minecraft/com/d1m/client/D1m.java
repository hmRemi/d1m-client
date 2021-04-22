package com.d1m.client;

import com.d1m.client.hud.altmanager.account.AccountManager;
import com.d1m.client.hud.altmanager.thealtening.AltService;
import com.d1m.client.hud.render.Hud;
import com.d1m.client.hud.render.notifications.NotificationManager;
import com.d1m.client.hud.render.tabgui.TabGui;
import com.d1m.client.management.command.CommandManager;
import com.d1m.client.management.cosmetics.impl.CosmeticWings;
import com.d1m.client.management.event.EventManager;
import com.d1m.client.management.event.EventTarget;
import com.d1m.client.management.event.events.EventKey;
import com.d1m.client.management.event.events.render.EventRenderPlayer;
import com.d1m.client.management.event.events.update.EventPreMotionUpdate;
import com.d1m.client.management.module.Module;
import com.d1m.client.management.module.ModuleManager;
import com.d1m.client.management.utils.Files;
import com.d1m.client.management.utils.Strings;
import com.d1m.client.management.utils.combat.RaycastUtil;
import com.d1m.client.management.utils.combat.RotationUtil;
import com.d1m.client.management.utils.rendering.DeltaUtil;
import com.d1m.client.management.utils.rendering.Render2D;
import org.lwjgl.opengl.Display;

import java.io.File;

public class D1m {

    public static String clientDeveloper = "4Remi & D1m";
    public static String clientVersion = "1.0.0";
    public static String clientName = "D1m";
    public static String clientBuild = "1";

    public static float partialTicks;

    public static D1m instance = new D1m();

    public static final DeltaUtil DELTA_UTIL = new DeltaUtil();
    public static final Render2D RENDER2D = new Render2D();
    public static RotationUtil ROTATION_UTIL;
    public static RaycastUtil RAYCAST_UTIL;
    public NotificationManager notificationManager;
    private AccountManager accountManager;
    public ModuleManager moduleManager;
    public CommandManager cmdManager;
    public EventManager eventManager;
    private AltService altService;
    public static TabGui tabgui;
    public static Hud hud;

    public void clientStartup() {
        String clientFolder = new File(".").getAbsolutePath();
        clientFolder = (clientFolder.contains("jars") ? new File(".").getAbsolutePath().substring(0, clientFolder.length() - 2) : new File(".").getAbsolutePath()) + Strings.getSplitter() + clientName;
        String accountManagerFolder = clientFolder + Strings.getSplitter() + "alts";
        Files.createRecursiveFolder(accountManagerFolder);

        accountManager = new AccountManager(new File(accountManagerFolder));
        notificationManager = new NotificationManager();
        moduleManager = new ModuleManager();
        eventManager = new EventManager();
        cmdManager = new CommandManager();
        tabgui = new TabGui();

        //switchToMojang();

        Display.setResizable(true);
        Display.setTitle(clientName + " Build: " + clientBuild);
        eventManager.register(this);
    }

    public void clientShutdown() {
        eventManager.unregister(this);
    }

    @EventTarget
    public void onKey(EventKey ek) {
        moduleManager.getModuleList().stream().filter(module -> module.getKey() == ek.getKey()).forEach(module -> module.toggle());
    }

    @EventTarget
    public void RenderPlayer(final EventRenderPlayer event) {
        CosmeticWings.getWings().onRenderPlayer(event.entity, partialTicks);
    }

    public static void onRender(){
        for(Module m: D1m.instance.moduleManager.getModuleList()){
            m.onRender();
        }
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public AltService getAltService() {
        return altService;
    }

    public void switchToMojang() {
        try {
            this.altService.switchService(AltService.EnumAltService.MOJANG);
        } catch (NoSuchFieldException e) {
            System.out.println("Couldn't switch to modank altservice");
        } catch (IllegalAccessException e) {
            System.out.println("Couldn't switch to modank altservice -2");
        }
    }

    public void switchToTheAltening() {
        try {
            this.altService.switchService(AltService.EnumAltService.THEALTENING);
        } catch (NoSuchFieldException e) {
            System.out.println("Couldn't switch to altening altservice");
        } catch (IllegalAccessException e) {
            System.out.println("Couldn't switch to altening altservice -2");
        }
    }
}

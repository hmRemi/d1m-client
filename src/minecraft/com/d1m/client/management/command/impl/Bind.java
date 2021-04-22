package com.d1m.client.management.command.impl;

import com.d1m.client.D1m;
import com.d1m.client.management.command.Command;
import com.d1m.client.management.handlers.ChatHandler;
import com.d1m.client.management.module.Module;
import org.lwjgl.input.Keyboard;

import java.util.Locale;

public class Bind extends Command {
    public Bind(String[] names, String description) {
        super(names, description);
    }

    @Override
    public String getAlias() {
        return "bind";
    }

    @Override
    public String getDescription() {
        return "Bind a module to your liking.";
    }

    @Override
    public String getSyntax() {
        return ".bind add (module) (key) | .bind del (module) | .bind clear";
    }

    @Override
    public String executeCMD(String line, String[] args) {
        if(args[0].equalsIgnoreCase("")) {
            ChatHandler.addChatMessage(this.getSyntax());
        }

        if(args[0].equalsIgnoreCase("add")) {
            args[2] = args[2].toUpperCase();
            final int key = Keyboard.getKeyIndex(args[2]);
            for (final Module mod : D1m.instance.moduleManager.getModuleList()) {
                if (args[1].equalsIgnoreCase(mod.getName())) {
                    mod.setKey(Keyboard.getKeyIndex(Keyboard.getKeyName(key)));
                    ChatHandler.addChatMessage("§d" + args[1] + " §7has been bound to §d" + key);
                }
            }
        } else if (args[0].equalsIgnoreCase("del")) {
            for (final Module i : D1m.instance.moduleManager.getModuleList()) {
                if (i.getName().equalsIgnoreCase(args[1])) {
                    i.setKey(0);
                    ChatHandler.addChatMessage("§d" + args[1] + "'s §7binds has been set to §dNONE");
                }
            }
        } else if (args[0].equalsIgnoreCase("clear")) {
            for (final Module i : D1m.instance.moduleManager.getModuleList()) {
                i.setKey(0);
            }
            ChatHandler.addChatMessage("All binds has been removed");
        }

        return line;
    }
}

package com.d1m.client.management.command;

import com.d1m.client.management.command.impl.Bind;
import com.d1m.client.management.handlers.ChatHandler;

import java.util.ArrayList;

public class CommandManager {

    public static ArrayList<Command> cmds;

    public CommandManager() {
        cmds = new ArrayList<Command>();
        addCommand(new Bind(new String[] { "bind" }, "Bind a module to your liking"));
    }

    public static void addCommand(final Command cmd){
        cmds.add(cmd);
    }

    public static ArrayList<Command> getCommands() {
        return cmds;
    }

    public void callCMD(final String input) {
        final String[] split = input.split(" ");
        final String command = split[0];
        final String args = input.substring(command.length()).trim();
        for (final Command c : getCommands()) {
            if (c.getAlias().equalsIgnoreCase(command)) {
                try {
                    c.executeCMD(args, args.split(" "));
                }
                catch (Exception e) {
                    ChatHandler.addChatMessage(" Invalid usage, please use the correct syntax.");
                    ChatHandler.addChatMessage(c.getSyntax());
                }
                return;
            }
        }
        ChatHandler.addChatMessage("Command was not found.");
    }
}

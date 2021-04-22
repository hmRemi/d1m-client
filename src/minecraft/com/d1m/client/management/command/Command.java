package com.d1m.client.management.command;

public abstract class Command {

    private final String[] names;
    private final String description;

    public Command(final String[] names, final String description) {
        this.names = names;
        this.description = description;
    }

    public String getName() {
        return this.names[0];
    }

    public abstract String getAlias();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract String executeCMD(final String p0, final String[] p1);
}

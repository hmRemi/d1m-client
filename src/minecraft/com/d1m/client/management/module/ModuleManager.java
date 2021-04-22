package com.d1m.client.management.module;

import com.d1m.client.management.module.impl.movement.*;
import com.d1m.client.management.module.impl.player.*;

import java.util.ArrayList;

public class ModuleManager {

    private ArrayList<Module> mods = new ArrayList<Module>();

    public ModuleManager() {
        mods.add(new Fly());
        mods.add(new Disabler());
        mods.add(new Speed());
    }

    public ArrayList<Module> getModuleList() {
        return mods;
    }

    public Module getModByName(String name) {
        return mods.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public ArrayList<Module> getModulesInCategory(final Category category) {
        final ArrayList<Module> categoryModules = new ArrayList<Module>();
        for (final Module m : this.mods) {
            if (m.getCategory() == category) {
                categoryModules.add(m);
            }
        }
        return categoryModules;
    }
}

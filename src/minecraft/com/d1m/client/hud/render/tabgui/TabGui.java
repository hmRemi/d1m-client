package com.d1m.client.hud.render.tabgui;

import com.d1m.client.D1m;
import com.d1m.client.management.module.Category;
import com.d1m.client.management.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class TabGui {

    public static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

    private ArrayList<String> mods = new ArrayList();
    private ArrayList<String> category = new ArrayList();

    private int selectedMod, selectedTab;
    private int start, start1 = start + 14, end;
    public static int screen = 0;

    public TabGui() {
        Category[] arrayofCategories;
        int j = (arrayofCategories = Category.values()).length;

        for(int i = 0; i < j; i++) {
            Category category = arrayofCategories[i];

            if(category.name().equalsIgnoreCase("Gui")) {
                continue;
            }
            this.category.add(category.toString().substring(0,1) + category.toString().substring(1, category.toString().length()).toLowerCase());
        }
    }

    public void drawTabGui() {
        int count = 0;

        for(Category cat : Category.values()) {
            if(!cat.name().equalsIgnoreCase("Gui")) {
                int y = 14 + (count * 13);
                int yZ = 98 + ((count + 1) * 13);

                drawBorderRect(6, y, 65, y + 13, !cat.name().equalsIgnoreCase(category.get(this.selectedTab)) ? 0xbb151515 : 0xffcc44ff, !cat.name().equalsIgnoreCase(category.get(this.selectedTab)) ? 0xbb151515 : 0xffcc44ff, 0);
                Gui.drawRect(6, y, 5, y + 13, 0xffcc44ff);

                count++;
            }
        }
        int categoryCount = 0;

        for(String s : this.category) {
            fr.drawCenteredString(s, 35, 17 + categoryCount * 13, -1);
            categoryCount++;
        }

        if(screen == 1) {
            int modCount = 0;

            for(Module mod: getModsForCategory()) {
                String color;
                if(mod.isToggled()) {
                    color = "\247f";
                } else {
                    color = "\2477";
                }
                String name = color + mod.getName();
                int y = 14 + (modCount * 13);
                int yZ = 62 + ((modCount + 1) * 13);

                drawBorderRect(66, y, 70 + this.getLongestModWidth(), y + 13, !mod.getName().equalsIgnoreCase(this.getModsForCategory().get(this.selectedMod).getName()) ? 0xffcc44ff : 0xbb151515, !mod.getName().equalsIgnoreCase(this.getModsForCategory().get(this.selectedMod).getName()) ? 0xbb151515 : 0xffcc44ff, 0);
                fr.drawString(name, 70, y + 3, 0x0000000);
                Gui.drawRect(65, y, 66, y + 13, 0xffcc44ff);
                modCount++;
            }
        }
    }

    private void up() {
        if(screen == 0) {
            if(this.selectedTab <= 0) {
                this.selectedTab = this.category.size();
            }
            this.selectedTab -= 1;
        } else if(screen == 1) {
            if(this.selectedMod <= 0) {
                this.selectedMod = getModsForCategory().size();
            }
            this.selectedMod -= 1;
        }
    }

    private void down() {
        if(screen == 0) {
            if(this.selectedTab >= this.category.size() - 1) {
                this.selectedTab = -1;
            }
            this.selectedTab += 1;
        } else if(screen == 1) {
            if(this.selectedMod >= getModsForCategory().size() - 1) {
                this.selectedMod = -1;
            }
            this.selectedMod += 1;
        }
    }

    public void left() {
        if(screen == 1) {
            screen = 0;
        }
    }

    public void right() {
        if(screen == 1) {
            this.enter();
        } else {
            if(screen == 0) {
                screen = 1;
                this.selectedMod = 0;
            }
        }
    }

    public void enter() {
        if(screen == 1) {
            ((Module) getModsForCategory().get(this.selectedMod)).toggle();
        }
    }

    public void actionEvent(final int key) {
        if(key == Keyboard.KEY_UP) {
            this.up();
        }
        if(key == Keyboard.KEY_DOWN) {
            this.down();
        }
        if(key == Keyboard.KEY_LEFT) {
            this.left();
        }
        if(key == Keyboard.KEY_RIGHT) {
            this.right();
        }
        if(key == Keyboard.KEY_RETURN) {
            this.enter();
        }
    }

    private ArrayList<Module> getModsForCategory() {
        ArrayList<Module> modss = new ArrayList<Module>();

        for(Module mod : D1m.instance.moduleManager.getModuleList()) {
            if(mod.getCategory() == Category.valueOf((String) this.category.get(this.selectedTab).toUpperCase())) {
                modss.add(mod);
            }
        }
        return modss;
    }

    private int getLongestModWidth() {
        int longest = 0;

        for(Module mod : getModsForCategory()) {
            if(fr.getStringWidth(mod.getName()) + 3 > longest) {
                longest = (int) fr.getStringWidth(mod.getName()) + 3;
            }
        }
        return longest;
    }

    public static void drawBorderRect(float left, float top, float right, float bottom, int bcolor, int color, float f) {
        Gui.drawRect(left + f, top + f, right - f, bottom - f, color);
        Gui.drawRect(left, top, left + f, bottom, bcolor);
        Gui.drawRect(left + f, top, right, top + f, bcolor);
        Gui.drawRect(left + f, bottom - f, right, bottom, bcolor);
        Gui.drawRect(right + f, top + f, right, bottom - f, bcolor);
    }
}

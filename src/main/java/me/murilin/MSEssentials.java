package me.murilin;

import org.bukkit.plugin.java.JavaPlugin;

public class MSEssentials extends JavaPlugin {

    private Modules modules;

    @Override
    public void onEnable() {
        super.onEnable();
        modules = new Modules(this);
        modules.loadModules();
    }

    @Override
    public void onDisable() {
        modules = null;
        super.onDisable();
        System.gc();
    }
}

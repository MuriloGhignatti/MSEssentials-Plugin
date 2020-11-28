package me.murilin;

import org.bukkit.plugin.java.JavaPlugin;

public class MSEssentials extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        Modules modules = new Modules(this);
        modules.loadModules();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}

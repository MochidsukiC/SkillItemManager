package jp.houlab.mochidsuki.skillItemManager;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    static public FileConfiguration config;
    static public Plugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        config = getConfig();
        getServer().getPluginManager().registerEvents(new ItemUsingListener(), this);
        plugin =  this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void allCoolDown(Player player) {
        for(String string : config.getConfigurationSection("Items").getKeys(false)) {
            player.setCooldown(Material.matchMaterial(string),config.getInt("Items." + string+".CT")*20);
        }
    }
}

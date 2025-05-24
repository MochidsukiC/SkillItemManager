package jp.houlab.mochidsuki.skillItemManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

import static jp.houlab.mochidsuki.skillItemManager.Main.config;

public class ItemUsingListener implements Listener {
    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL){
            boolean b = trigger(event.getPlayer());
            event.setCancelled(b);
        }
    }

    @EventHandler
    public void onItemUseAtEntity(PlayerInteractEntityEvent event) {
        boolean b = trigger(event.getPlayer());
        event.setCancelled(b);
    }



    public boolean trigger(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        Set<String> strings = config.getConfigurationSection("Items").getKeys(false);
        boolean b = false;
        for (String name : strings){
            Material material = Material.matchMaterial(name);
            if(item.getType().equals(material)){
                if(player.getCooldown(material) <= 0) {
                    if (config.getString("Items." + name + ".Type").equalsIgnoreCase("command")) {
                        player.getScoreboard().getObjective(config.getString("Items." + name + ".ScoreBoard")).getScore(player.getName()).setScore(1);
                    }
                    player.setCooldown(material, config.getInt("Items." + name + ".CT") * 20);
                }
                b = config.getBoolean("ItemUsing");
            }
        }
        return b;
    }
}

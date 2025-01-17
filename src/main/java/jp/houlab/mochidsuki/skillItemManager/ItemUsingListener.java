package jp.houlab.mochidsuki.skillItemManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static jp.houlab.mochidsuki.skillItemManager.Main.config;

public class ItemUsingListener implements Listener {
    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        for (String name : config.getStringList("Items")){
            Material material = Material.getMaterial(name);
            if(item.getType().equals(material) && player.getCooldown(material) <= 0){
                if (config.getString("Items."+ name + ".Type").equalsIgnoreCase("command")){
                    player.getScoreboard().getObjective("ender_eye").getScore(player.getName()).setScore(1);
                }
                player.setCooldown(material, config.getInt("Items."+ name + ".CT"));

            }
        }

    }
}

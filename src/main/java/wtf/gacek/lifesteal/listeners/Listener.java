package wtf.gacek.lifesteal.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import wtf.gacek.lifesteal.Lifesteal;
import wtf.gacek.lifesteal.Utils;
import wtf.gacek.lifesteal.managers.LifestealManager;

import java.util.Objects;


public class Listener implements org.bukkit.event.Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        if (killer == null) {
            return;
        }
        Player player = e.getEntity();
        LifestealManager lifeManager = Lifesteal.getLifeManager();
        lifeManager.setHealth(player, lifeManager.getHealth(player) - 2);
        if (killer.getUniqueId() != player.getUniqueId()) {
            lifeManager.setHealth(killer, lifeManager.getHealth(killer) + 2);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!e.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }
        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        if (mainHandItem.getType() != Material.NETHER_STAR) {
            return;
        }
        if (!Objects.requireNonNull(mainHandItem.getItemMeta()).getDisplayName().equals(Utils.colorize("&cHeart"))) {
            return;
        }
        player.getInventory().getItemInMainHand().setAmount(mainHandItem.getAmount() - 1);
        LifestealManager lifeManager = Lifesteal.getLifeManager();
        lifeManager.setHealth(player, lifeManager.getHealth(player) + 2);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.setHealth(player.getHealth());
    }
}

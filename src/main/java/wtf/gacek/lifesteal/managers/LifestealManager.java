package wtf.gacek.lifesteal.managers;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import wtf.gacek.lifesteal.Utils;

import java.util.*;

public class LifestealManager {

    public int getHealth(Player player) {
        if (player == null) return 20;
        return (int) Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue();
    }

    public void setHealth(Player player, int health) {
        if (player == null) return;
        if (player.getHealth() > health) {
            player.setHealth(health);
        }
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(health);
        if (getHealth(player) <= 0) {
            Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(player.getName(), Utils.colorize("&cYou are out of hearts!"), null, null);
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(2);
            player.kickPlayer(Utils.colorize("&cYou are out of hearts!"));
        }
    }
}

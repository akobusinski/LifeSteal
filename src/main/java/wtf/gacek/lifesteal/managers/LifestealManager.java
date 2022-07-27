package wtf.gacek.lifesteal.managers;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import wtf.gacek.lifesteal.Lifesteal;
import wtf.gacek.lifesteal.Utils;

import java.io.*;
import java.util.*;

public class LifestealManager {

    public int getHealth(Player player) {
        if (player == null) return 20;
        return (int) Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue();
    }

    @SuppressWarnings("unchecked")
    public void setHealth(Player player, int health) {
        try {
            if (player == null) return;
            if (player.getHealth() > health) {
                player.setHealth(health);
            }
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(health);
            if (getHealth(player) <= 0) {
                Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(player.getName(), Utils.colorize("&cYou are out of hearts!"), null, null);
                Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(10);
                player.kickPlayer(Utils.colorize("&cYou are out of hearts!"));
                File f = new File(Lifesteal.getInstance().getDataFolder(), File.separator + "bans");
                ArrayList<String> banList = new ArrayList<>();
                if (f.exists()) {
                    FileInputStream inputStream = new FileInputStream(f);
                    ObjectInputStream objectInput = new ObjectInputStream(inputStream);
                    banList.addAll((ArrayList<String>) objectInput.readObject());
                    objectInput.close();
                    inputStream.close();
                }
                banList.add(player.getUniqueId().toString());
                FileOutputStream outputStream = new FileOutputStream(f);
                ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);
                objectOutput.writeObject(banList);
                objectOutput.close();
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public boolean isBanned(UUID uuid) {
        try {
            File f = new File(Lifesteal.getInstance().getDataFolder(), File.separator + "bans");
            if (!f.exists()) {
                return false;
            }
            FileInputStream inputStream = new FileInputStream(f);
            ObjectInputStream objectInput = new ObjectInputStream(inputStream);
            ArrayList<String> banList = (ArrayList<String>) objectInput.readObject();
            objectInput.close();
            inputStream.close();
            return banList.contains(uuid.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public void unban(UUID uuid) {
        try {
            File f = new File(Lifesteal.getInstance().getDataFolder(), File.separator + "bans");
            if (!f.exists()) {
                return;
            }
            FileInputStream inputStream = new FileInputStream(f);
            ObjectInputStream objectInput = new ObjectInputStream(inputStream);
            ArrayList<String> banList = (ArrayList<String>) objectInput.readObject();
            objectInput.close();
            inputStream.close();
            if (!banList.contains(uuid.toString())) {
                return;
            }
            banList.remove(uuid.toString());
            FileOutputStream outputStream = new FileOutputStream(f);
            ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);
            objectOutput.writeObject(banList);
            objectOutput.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

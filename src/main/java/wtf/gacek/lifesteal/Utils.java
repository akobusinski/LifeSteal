package wtf.gacek.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Utils {
    public static void colorize(CommandSender sender, String message) {
        if (sender == null || message == null) return;
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.stripColor(colorize(message)));
        } else {
            sender.sendMessage(colorize(message));
        }
    }

    public static String colorize(String message) {
        if (message == null) return "";
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void debug(CommandSender sender, String message) {
        if (sender == null || message == null) return;
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.stripColor(debug(message)));
        } else {
            sender.sendMessage(debug(message));
        }
    }

    public static String debug(String message) {
        if (message == null) return "";
        return colorize("&7[&3DEBUG&7] " + message);
    }

    public static Inventory craftingInventory(String name, ArrayList<ItemStack> ingredients, ItemStack output) {
        Inventory inventory = Bukkit.createInventory(null, 54, Utils.colorize(name));
        for (int i=0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }
        for (int i=0; i < 3; i++) {
            inventory.setItem(i + 10, new ItemStack(Material.AIR));
        }
        for (int i=0; i < 3; i++) {
            inventory.setItem(i + 19, new ItemStack(Material.AIR));
        }
        for (int i=0; i < 3; i++) {
            inventory.setItem(i + 28, new ItemStack(Material.AIR));
        }
        inventory.setItem(23, new ItemStack(Material.CRAFTING_TABLE));
        inventory.setItem(25, new ItemStack(Material.AIR));
        // god please have mercy
        inventory.setItem(10, ingredients.get(0));
        inventory.setItem(11, ingredients.get(1));
        inventory.setItem(12, ingredients.get(2));
        inventory.setItem(19, ingredients.get(3));
        inventory.setItem(20, ingredients.get(4));
        inventory.setItem(21, ingredients.get(5));
        inventory.setItem(28, ingredients.get(6));
        inventory.setItem(29, ingredients.get(7));
        inventory.setItem(30, ingredients.get(8));
        inventory.setItem(25, output);
        return inventory;
    }
}

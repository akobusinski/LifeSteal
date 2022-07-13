package wtf.gacek.lifesteal;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

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
}

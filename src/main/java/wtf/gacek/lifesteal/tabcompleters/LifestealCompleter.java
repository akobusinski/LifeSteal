package wtf.gacek.lifesteal.tabcompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LifestealCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completion = new ArrayList<>();
        if (!(sender instanceof Player)) {
            return completion;
        }
        if (args.length == 1) {
            if (sender.hasPermission("lifesteal.admin")) {
                completion.add("sethearts");
                completion.add("gethearts");
            }
            completion.add("withdraw");
        }
        if (args.length == 2) {
            if ((Objects.equals(args[0], "gethearts") || Objects.equals(args[0], "sethearts")) && sender.hasPermission("lifesteal.admin")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!(player.hasMetadata("vanished") && player.getMetadata("vanished").get(0).asBoolean())) {
                        completion.add(player.getName());
                    }
                }
            }
            if (Objects.equals(args[0], "withdraw")) {
                for (int n=0; n<=9; n++) {
                    completion.add(String.valueOf(n));
                }
            }
        }
        if (args.length == 3 && sender.hasPermission("lifesteal.admin") && Objects.equals(args[0], "sethearts")) {
            for (int n=0; n<=9; n++) {
                completion.add(String.valueOf(n));
            }
        }
        return completion;
    }
}

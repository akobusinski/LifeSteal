package wtf.gacek.lifesteal.commands;

import java.util.*;
import java.util.function.Supplier;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import wtf.gacek.lifesteal.Lifesteal;
import wtf.gacek.lifesteal.Utils;
import wtf.gacek.lifesteal.recipes.revivetotem;

public class LifestealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            Utils.colorize(sender, "&cLifeSteal Plugin " + Lifesteal.getInstance().getDescription().getVersion());
            Utils.colorize(sender, "&cCreated by GacekKosmatek");
            Utils.colorize(sender, "");
            if (sender.hasPermission("lifesteal.admin")) {
                Utils.colorize(sender, "&cAdmin Commands:");
                Utils.colorize(sender, " - &c/lifesteal sethearts <player> <amount> - Sets players hearts to the amount provided");
                Utils.colorize(sender, " - &c/lifesteal gethearts <player> - Get how many hearts a player has");
            }
            Utils.colorize(sender, "&cCommands: ");
            Utils.colorize(sender, " - &c/lifesteal withdraw [amount] - Withdraw a amount of hearts, defaults to 1");
            Utils.colorize(sender, " - &c/lifesteal revive <player> - Revives a player, takes one of your hearts");
            Utils.colorize(sender, " - &c/lifesteal recipe - Shows the recipe for the revive totem");
            return true;
        }
        Player target;
        Player p;
        switch (args[0].toLowerCase()) {
            case "sethearts":
                if (!sender.hasPermission("lifesteal.admin")) {
                    Utils.colorize(sender, "&cCommand not found.");
                    break;
                }
                if (!(args.length >= 2)) {
                    Utils.colorize(sender, "&cNo player provided");
                    break;
                }
                target = Bukkit.getServer().getPlayer(args[1]);
                if (target == null) {
                    Utils.colorize(sender, "&cCould not find " + args[1] + ", are you sure they are online?");
                    break;
                }
                if (!(args.length >= 3)) {
                    Utils.colorize(sender, "&cNo heart amount provided");
                    break;
                }
                Lifesteal.getLifeManager().setHealth(target, Integer.parseInt(args[2]) * 2);
                Utils.colorize(sender, "&cSet " + target.getName() + "'s hearts to " + args[2]);
                break;
            case "gethearts":
                if (!sender.hasPermission("lifesteal.admin")) {
                    Utils.colorize(sender, "&cCommand not found.");
                    break;
                }
                if (!(args.length >= 2)) {
                    Utils.colorize(sender, "&cNo player provided");
                    break;
                }
                target = Bukkit.getServer().getPlayer(args[1]);
                if (target == null) {
                    Utils.colorize(sender, "&cCould not find " + args[1] + ", are you sure they are online?");
                    break;
                }
                Utils.colorize(sender, "&6" + target.getName() + "'s health: " + Lifesteal.getLifeManager().getHealth(target) / 2);
                break;
            case "withdraw":
                if (!(sender instanceof Player)) {
                    Utils.colorize(sender, "&cYou have to be a player to use this command!");
                    break;
                }
                int amount = 1;
                if (args.length >= 2) {
                    try {
                        amount = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        Utils.colorize(sender, "&cThat does not look like a number!");
                        break;
                    }
                }
                if (amount <= 0) {
                    Utils.colorize(sender, "&cInvalid heart amount to withdraw");
                    break;
                }
                p = (Player) sender;
                ItemStack itemStack = new ItemStack(Material.NETHER_STAR);
                ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
                assert itemMeta != null;
                itemMeta.setDisplayName(Utils.colorize("&cHeart"));
                itemMeta.setLore(List.of(Utils.colorize("&cRight click to redeem a single heart")));
                itemStack.setItemMeta(itemMeta);
                if ((Lifesteal.getLifeManager().getHealth(p) - (amount * 2)) <= 0) {
                    Utils.colorize(sender, "&cYou can't withdraw that many hearts!");
                    break;
                }
                itemStack.setAmount(amount);
                p.getInventory().addItem(itemStack);
                Lifesteal.getLifeManager().setHealth((Player) sender, Lifesteal.getLifeManager().getHealth((Player) sender) - (amount * 2));
                Utils.colorize(sender, "&cYou withdrew " + amount + " heart!");
                break;
            case "revive":
                if (!(sender instanceof Player)) {
                    Utils.colorize(sender, "&cYou have to be a player to use this command");
                    break;
                }
                p = (Player) sender;
                ItemStack item = p.getInventory().getItemInMainHand();
                if (item.getItemMeta() == null || !item.getItemMeta().getDisplayName().equals(Utils.colorize("&6Totem of Revival"))) {
                    Utils.colorize(sender, "&cYou are not holding a revive totem!");
                    Utils.colorize(sender, "&cUse &7/lifesteal recipe&c to see the recipe!");
                    break;
                }
                if (!(args.length >= 2)) {
                    Utils.colorize(sender, "&cNo player provided");
                    break;
                }
                boolean isFound = false;
                for (OfflinePlayer op : Bukkit.getOfflinePlayers()) {
                    if (Objects.equals(Objects.requireNonNull(op.getName()).toLowerCase(), args[1].toLowerCase())) {
                        if (Lifesteal.getLifeManager().isBanned(op.getUniqueId())) {
                            Bukkit.getServer().getBanList(BanList.Type.NAME).pardon(Objects.requireNonNull(op.getName()));
                            Lifesteal.getLifeManager().unban(op.getUniqueId());
                            p.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
                            Utils.colorize(sender, "&a" + op.getName() + " has been successfully revived!");
                        } else {
                            Utils.colorize(sender, "&c" + op.getName() + " is not banned!");
                        }
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) {
                    Utils.colorize(sender, "&cPlayer " + args[1] + " not found");
                }
                break;
            case "recipe":
                if (!(sender instanceof Player)) {
                    Utils.colorize(sender, "&cYou have to be a player to use this command");
                    break;
                }
                p = (Player) sender;
                Inventory inventory = Utils.craftingInventory("&6Revive Totem Recipe", revivetotem.getRecipeIngredients(), revivetotem.getItem());
                p.openInventory(inventory);
                break;
            default:
                Utils.colorize(sender, "&cCommand not found.");
                break;
        }
        return true;
    }
}


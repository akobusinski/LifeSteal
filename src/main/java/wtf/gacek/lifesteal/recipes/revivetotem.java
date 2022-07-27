package wtf.gacek.lifesteal.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import wtf.gacek.lifesteal.Lifesteal;
import wtf.gacek.lifesteal.Utils;

import java.util.ArrayList;

public class revivetotem {
    public static ShapedRecipe getRecipe() {
        ItemStack totemStack = new ItemStack(Material.TOTEM_OF_UNDYING, 1);
        ItemMeta totemMeta = Bukkit.getItemFactory().getItemMeta(totemStack.getType());
        totemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        assert totemMeta != null;
        totemMeta.setDisplayName(Utils.colorize("&6Totem of Revival"));
        totemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ArrayList<String> totemLore = new ArrayList<>();
        totemLore.add(Utils.colorize("&6Have this item in your main hand and use"));
        totemLore.add(Utils.colorize("&6the &r&7/lifesteal revive &r&6command to revive someone"));
        totemLore.add(Utils.colorize("&6WARNING: THIS ITEM CAN BE USED"));
        totemLore.add(Utils.colorize("&6LIKE A NORMAL TOTEM OF UNDYING"));
        totemMeta.setLore(totemLore);
        totemStack.setItemMeta(totemMeta);
        ShapedRecipe totemRecipe = new ShapedRecipe(new NamespacedKey(Lifesteal.getInstance(), "revive_totem"), totemStack);

        //    G  |  E  |  G
        //  -----|-----|-----
        //    D  |  N  |  D     ->  Totem Of Revival
        //  -----|-----|-----
        //    G  |  E  |  G

        // N - Nether Star
        // D - Diamond Block
        // G - Gold Block
        // E - Emerald Block

        totemRecipe.shape("GEG", "DND", "GEG");
        totemRecipe.setIngredient('N', Material.NETHER_STAR);
        totemRecipe.setIngredient('D', Material.DIAMOND_BLOCK);
        totemRecipe.setIngredient('G', Material.GOLD_BLOCK);
        totemRecipe.setIngredient('E', Material.EMERALD_BLOCK);

        return totemRecipe;
    }
}

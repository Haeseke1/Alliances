package me.Haeseke1.Alliances.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class InventoryManager {
	
	
	public static void removeItems(Inventory inventory, ItemStack item, int amount) {
		Material type = item.getType();
		MaterialData md = item.getData();
		ItemMeta im = item.getItemMeta();
		short dura = item.getDurability();
        if (amount <= 0) return;
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (type.equals(is.getType()) && md.equals(is.getData()) && is.getEnchantments().equals(is.getEnchantments()) && dura == is.getDurability() && im.equals(is.getItemMeta())) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    break;
                } else {
                    inventory.clear(slot);
                    amount = -newAmount;
                    if (amount == 0) break;
                }
            }
        }
    }
	
	public static boolean containsItems(Inventory inventory, ItemStack item, int amount) {
		Material type = item.getType();
		MaterialData md = item.getData();
		ItemMeta im = item.getItemMeta();
		short dura = item.getDurability();
        if (amount <= 0) return true;
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (type.equals(is.getType()) && md.equals(is.getData()) && is.getEnchantments().equals(is.getEnchantments()) && dura == is.getDurability() && im.equals(is.getItemMeta())) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    return true;
                } else {
                    amount = -newAmount;
                    if (amount == 0) return true;
                }
            }
        }
        return false;
        
    }

}

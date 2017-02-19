package me.Haeseke1.Alliances.Crates;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Key {
	
	public String name;
	
	public static List<Key> keys = new ArrayList<Key>();
	
	public Key(String name) {
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		keys.add(this);
	}
	
	public ItemStack getItemStack(){
		ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name + " Key");
		List<String> list = new ArrayList<String>();
		list.add(ChatColor.DARK_PURPLE + "Right click to open a crate!");
		im.setLore(list);
		item.setItemMeta(im);
		return item;
	}
	
	public boolean checkItemStack(ItemStack item){
		if(item == null){
			return false;
		}
		ItemStack key = getItemStack();
		key.setAmount(item.getAmount());
		if(key.equals(item)){
			return true;
		}
		return false;
	}
	
}
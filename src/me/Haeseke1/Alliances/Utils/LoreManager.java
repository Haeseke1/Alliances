package me.Haeseke1.Alliances.Utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LoreManager {

	public static Player getOwner(ItemStack item){
		ItemMeta m = item.getItemMeta();
		if(!m.hasLore()) return null;
		List<String> lore = m.getLore();
		for(String s: lore){
			s = ChatColor.stripColor(s);
			if(s.contains("By:")){
				return Bukkit.getPlayer(s.substring(4));
			}
		}
		return null;
	}
	
}

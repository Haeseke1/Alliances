package me.Haeseke1.Alliances.Alliance.Type;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Alliance.AllianceType;
import me.Haeseke1.Alliances.Economy.Coins;

public class Undine {
	
	public static int cost;
	
	public static ItemStack createItem(Player player){
		ItemStack i = new ItemStack(Material.GOLDEN_APPLE, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Undine");
		List<String> list = new ArrayList<String>();
		if(Coins.getPlayerCoins(player) >= cost){
			list.add(ChatColor.DARK_PURPLE + "Cost: " + ChatColor.GREEN + cost + "$");
		}else{
			list.add(ChatColor.DARK_PURPLE + "Cost: " + ChatColor.RED + cost + "$");
		}
		list.add(ChatColor.DARK_PURPLE + "Bonus:");
		list.add(ChatColor.DARK_PURPLE + "- Faster healing");
		im.setLore(list);
		i.setItemMeta(im);
		return i;
	}
	
	
	public static void update(){
		for(Player player : Bukkit.getOnlinePlayers()){
			Alliance alli = AllianceManager.getAlliance(player);
			if(alli == null){
				continue;
			}
			if(alli.getType().equals(AllianceType.UNDINE)){
				if(player.getHealth() + 1 <= player.getMaxHealth()){
					player.setHealth(player.getHealth() + 1);
				}
			}
		}
	}
	
}

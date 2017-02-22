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

public class Gnome {
	
	public static int cost;
	
	public static List<Player> players = new ArrayList<Player>();
		
	public static ItemStack createItem(Player player){
		ItemStack i = new ItemStack(Material.DIRT, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Gnome");
		List<String> list = new ArrayList<String>();
		if(Coins.getPlayerCoins(player) >= cost){
			list.add(ChatColor.DARK_PURPLE + "Cost: " + ChatColor.GREEN + cost + "$");
		}else{
			list.add(ChatColor.DARK_PURPLE + "Cost: " + ChatColor.RED + cost + "$");
		}
		list.add(ChatColor.DARK_PURPLE + "Bonus:");
		list.add(ChatColor.DARK_PURPLE + "- Extra hearth");
		im.setLore(list);
		i.setItemMeta(im);
		return i;
	}
	
	public static void update(){
		List<Player> recent_Players = new ArrayList<Player>();
		for(Player player : Bukkit.getOnlinePlayers()){
			Alliance alli = AllianceManager.getAlliance(player);
			if(alli == null){
				if(players.contains(player)){
					player.setMaxHealth(player.getMaxHealth() - 4);
				}
				continue;
			}
			if(alli.getType().equals(AllianceType.GNOME)){
				if(!players.contains(player)){
					player.setMaxHealth(player.getMaxHealth() + 4);
					player.setHealth(player.getHealth() + 4);
				}
				recent_Players.add(player);
			}else{
				if(players.contains(player)){
					player.setMaxHealth(player.getMaxHealth() - 4);
				}
			}
		}
		players = recent_Players;
	}
	
	
	
}

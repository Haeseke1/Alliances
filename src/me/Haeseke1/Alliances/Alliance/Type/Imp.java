package me.Haeseke1.Alliances.Alliance.Type;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Alliance.AllianceType;
import me.Haeseke1.Alliances.Economy.Coins;

public class Imp {
	
	public static int cost;
	
	
	public static ItemStack createItem(Player player){
		ItemStack i = new ItemStack(Material.EYE_OF_ENDER, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Imp");
		List<String> list = new ArrayList<String>();
		if(Coins.getPlayerCoins(player) >= cost){
			list.add(ChatColor.DARK_PURPLE + "Cost: " + ChatColor.GREEN + cost + "$");
		}else{
			list.add(ChatColor.DARK_PURPLE + "Cost: " + ChatColor.RED + cost + "$");
		}
		list.add(ChatColor.DARK_PURPLE + "Bonus:");
		list.add(ChatColor.DARK_PURPLE + "- Stronger in the night");
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
			if(alli.getType().equals(AllianceType.IMP)){
				if(player.getWorld().getTime() > 13000 && player.getWorld().getTime() < 23000){
					player.removePotionEffect(PotionEffectType.SPEED);
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0));
				}
			}
		}
	}
	
	
}

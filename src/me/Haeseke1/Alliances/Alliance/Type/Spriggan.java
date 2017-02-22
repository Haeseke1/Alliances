package me.Haeseke1.Alliances.Alliance.Type;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Alliance.AllianceType;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Spriggan implements Listener{
	
	public static int cost;
	
	public static ItemStack createItem(Player player){
		ItemStack i = new ItemStack(Material.DIAMOND_BLOCK, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Spriggan");
		List<String> list = new ArrayList<String>();
		if(Coins.getPlayerCoins(player) >= cost){
			list.add(ChatColor.DARK_PURPLE + "Cost: " + ChatColor.GREEN + cost + "$");
		}else{
			list.add(ChatColor.DARK_PURPLE + "Cost: " + ChatColor.RED + cost + "$");
		}
		list.add(ChatColor.DARK_PURPLE + "Bonus:");
		list.add(ChatColor.DARK_PURPLE + "- Extra loot");
		im.setLore(list);
		i.setItemMeta(im);
		return i;
	}
	
	
	@EventHandler
	private void entityhitentity(EntityDeathEvent event){
		if(!(event.getEntity() instanceof LivingEntity)){
			return;
		}
		LivingEntity death = event.getEntity();
		if(death.getKiller() == null || !(death.getKiller() instanceof Player)){
			return;
		}
		Player player = death.getKiller();
		if(!AllianceManager.playerIsInAlli(player)){
			return;
		}
		Alliance alli = AllianceManager.getAlliance(player);
		if(!alli.getType().equals(AllianceType.SPRIGGAN)){
			return;
		}
		MessageManager.sendMessage(player, ChatColor.DARK_GREEN + "You are rewarded with " + ChatColor.GOLD + "10 coins!");
		Coins.addPlayerCoins(player, 10);
	}
	
	
}

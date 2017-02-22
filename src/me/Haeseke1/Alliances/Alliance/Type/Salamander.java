package me.Haeseke1.Alliances.Alliance.Type;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Alliance.AllianceType;
import me.Haeseke1.Alliances.Economy.Coins;

public class Salamander implements Listener {
	
	public static int cost;
	
	public static ItemStack createItem(Player player){
		ItemStack i = new ItemStack(Material.FIREBALL, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Salamander");
		List<String> list = new ArrayList<String>();
		if(Coins.getPlayerCoins(player) >= cost){
			list.add(ChatColor.DARK_PURPLE + "Cost: " + ChatColor.GREEN + cost + "$");
		}else{
			list.add(ChatColor.DARK_PURPLE + "Cost: " + ChatColor.RED + cost + "$");
		}
		list.add(ChatColor.DARK_PURPLE + "Bonus:");
		list.add(ChatColor.DARK_PURPLE + "- Extra damage");
		im.setLore(list);
		i.setItemMeta(im);
		return i;
	}
	
	@EventHandler
	private void entityhitentity(EntityDamageByEntityEvent event){
		if(!(event.getEntity() instanceof LivingEntity) || !(event.getDamager() instanceof Player)){
			return;
		}
		Player player = (Player) event.getDamager();
		if(!AllianceManager.playerIsInAlli(player)){
			return;
		}
		Alliance alli = AllianceManager.getAlliance(player);
		if(!alli.getType().equals(AllianceType.SALAMANDER)){
			return;
		}
		event.setDamage(event.getDamage() + 2);
	}

}

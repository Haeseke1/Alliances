package me.Haeseke1.Alliances.Item.Weapons.Armor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Legend_Of_Zeus implements Listener{

	public static ItemStack getHelmet(){
		ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Legend Of Zeus Helmet");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 10% chance to strike lightning on you!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 50% chance!");
		lore.add(ChatColor.RED + "Passive: Lightning will heal the player!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getChestplate(){
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Legend Of Zeus Chestplate");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 10% chance to strike lightning on you!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 50% chance!");
		lore.add(ChatColor.RED + "Passive: Lightning will heal the player!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getLeggings(){
		ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Legend Of Zeus Leggings");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 10% chance to strike lightning on you!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 50% chance!");
		lore.add(ChatColor.RED + "Passive: Lightning will heal the player!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getBoots(){
		ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Legend Of Zeus Boots");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 10% chance to strike lightning on you!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 50% chance!");
		lore.add(ChatColor.RED + "Passive: Lightning will heal the player!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	@EventHandler
	private void entityHit(EntityDamageByEntityEvent event){
		if(!(event.getEntity() instanceof Player)){
			return;
		}
		Player player = (Player) event.getEntity();
		int amount = 0;
		for(ItemStack item : player.getInventory().getArmorContents()){
			if(item == null || item.getType() == Material.AIR){
				continue;
			}
			if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName() && !item.getItemMeta().hasLore()){
				continue;
			}
			String displayname = item.getItemMeta().getDisplayName();
			if(!displayname.startsWith(ChatColor.RED + "" + ChatColor.BOLD + "Legend Of Zeus")){
				continue;
			}
			amount++;
		}
		amount = amount == 4 ? 5 : amount;
		if(event.getDamager() instanceof LightningStrike){
			event.setCancelled(true);
			if(player.getHealth() + amount / 2 <= player.getMaxHealth()){
				player.setHealth(player.getHealth() + amount / 2);
			}else{
				player.setHealth(player.getMaxHealth());
			}
			
			return;
		}
		int random = new Random().nextInt(100) + 1;
		if(amount * 15 > random){
			LightningStrike ls = player.getWorld().strikeLightning(player.getLocation());
			ls.setFireTicks(0);
		}
	}
	
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event) {
	    if (event.getCause() == IgniteCause.LIGHTNING) {
	        event.setCancelled(true);
	    }
	}
	
}

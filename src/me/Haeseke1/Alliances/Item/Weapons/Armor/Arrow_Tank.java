package me.Haeseke1.Alliances.Item.Weapons.Armor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Arrow_Tank implements Listener {
	
	public static ItemStack getHelmet(){
		ItemStack item = new ItemStack(Material.IRON_HELMET);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE + "Arrow Tank Helmet");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 15% chance to nullifie!");
		lore.add(ChatColor.DARK_GREEN + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 75% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getChestplate(){
		ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE + "Arrow Tank Chestplate");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 15% chance to nullifie!");
		lore.add(ChatColor.DARK_GREEN + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 75% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getLeggings(){
		ItemStack item = new ItemStack(Material.IRON_LEGGINGS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE + "Arrow Tank Leggings");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 15% chance to nullifie!");
		lore.add(ChatColor.DARK_GREEN + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 75% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getBoots(){
		ItemStack item = new ItemStack(Material.IRON_BOOTS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE + "Arrow Tank Boots");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 15% chance to nullifie!");
		lore.add(ChatColor.DARK_GREEN + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 75% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	@EventHandler
	private void entityHit(EntityDamageByEntityEvent event){
		if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Arrow)){
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
			if(!displayname.startsWith(ChatColor.LIGHT_PURPLE + "Arrow Tank")){
				continue;
			}
			amount++;
		}
		amount = amount == 4 ? 5 : amount;
		int random = new Random().nextInt(100) + 1;
		if(amount * 15 > random){
			event.setCancelled(true);
			for(ItemStack item : player.getInventory().getArmorContents()){
				if(item == null || item.getType() == Material.AIR){
					continue;
				}
				item.setDurability((short) (item.getDurability() + 1));
			}
		}
	}
	
	
	
	
	
}

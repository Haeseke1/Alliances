package me.Haeseke1.Alliances.Item.Weapons.Armor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Flame_Of_Hell implements Listener {
	
	public static ItemStack getHelmet(){
		ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE + "Flame Of Hell Helmet");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 10% chance to heal you on fire!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 50% chance!");
		lore.add(ChatColor.RED + "Passive: No fire damage for you!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getChestplate(){
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE + "Flame Of Hell Chestplate");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 10% chance to heal you on fire!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 50% chance!");
		lore.add(ChatColor.RED + "Passive: No fire damage for you!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getLeggings(){
		ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE + "Flame Of Hell Leggings");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 10% chance to heal you on fire!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 50% chance!");
		lore.add(ChatColor.RED + "Passive: No fire damage for you!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getBoots(){
		ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE + "Flame Of Hell Boots");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 10% chance to heal you on fire!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 50% chance!");
		lore.add(ChatColor.RED + "Passive: No fire damage for you!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	
	@EventHandler
	private void entityHit(EntityDamageEvent event){
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
			if(!displayname.startsWith(ChatColor.LIGHT_PURPLE + "Flame Of Hell")){
				continue;
			}
			amount++;
		}
		amount = amount == 4 ? 5 : amount;
		if(amount > 0){
			if(event.getCause() == DamageCause.FIRE_TICK){
				for(ItemStack item : player.getInventory().getArmorContents()){
					if(item == null || item.getType() == Material.AIR){
						continue;
					}
					item.setDurability((short) (item.getDurability() + 1));
				}
				event.setCancelled(true);
			}
		}
		int random = new Random().nextInt(100) + 1;
		if(random < amount * 10){
			if(event.getCause() == DamageCause.FIRE_TICK){
				if(player.getHealth() + 1 < player.getMaxHealth()){
					player.setHealth(player.getMaxHealth() + 1);
				}
			}
		}
	}
}

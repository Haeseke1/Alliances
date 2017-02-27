package me.Haeseke1.Alliances.Item.Weapons.Armor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Fire_Resistance {
	public static ItemStack getHelmet(){
		ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Fire Resistance Helmet");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "Has a 25% chance to absorb fire damage!");
		lore.add(ChatColor.RED + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.RED + "Full armor set has a 100% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getChestplate(){
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Fire Resistance Chestplate");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "Has a 25% chance to absorb fire damage!");
		lore.add(ChatColor.RED + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.RED + "Full armor set has a 100% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getLeggings(){
		ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Fire Resistance Leggings");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "Has a 25% chance to absorb fire damage!");
		lore.add(ChatColor.RED + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.RED + "Full armor set has a 100% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getBoots(){
		ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Fire Resistance Boots");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "Has a 25% chance to absorb fire damage!");
		lore.add(ChatColor.RED + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.RED + "Full armor set has a 100% chance!");
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
			if(!displayname.startsWith(ChatColor.RED + "Fire Resistance ")){
				continue;
			}
			amount++;
		}
		if(event.getCause() != DamageCause.FIRE_TICK) return;
		amount = amount == 4 ? 5 : amount;
		int random = new Random().nextInt(100) + 1;
		if(amount * 25 > random){
			event.setCancelled(true);
            player.setFireTicks(0);
			for(ItemStack item : player.getInventory().getArmorContents()){
				if(item == null || item.getType() == Material.AIR){
					continue;
				}
				item.setDurability((short) (item.getDurability() + 1));
			}
		}
	}
}

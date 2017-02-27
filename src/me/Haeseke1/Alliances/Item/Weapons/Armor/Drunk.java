package me.Haeseke1.Alliances.Item.Weapons.Armor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Drunk implements Listener{
	
	public static ItemStack getHelmet(){
		ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Drunk Helmet");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 10% chance to give your enemies beer!");
		lore.add(ChatColor.DARK_GREEN + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 50% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getChestplate(){
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Drunk Chestplate");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 10% chance to give your enemies beer!");
		lore.add(ChatColor.DARK_GREEN + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 50% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getLeggings(){
		ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Drunk Leggings");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 10% chance to give your enemies beer!");
		lore.add(ChatColor.DARK_GREEN + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 50% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getBoots(){
		ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Drunk Boots");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 10% chance to give your enemies beer!");
		lore.add(ChatColor.DARK_GREEN + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 50% chance!");
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
			if(!displayname.startsWith(ChatColor.AQUA + "Drunk ")){
				continue;
			}
			amount++;
		}
		if(!(event.getDamager() instanceof LivingEntity)) return;
		LivingEntity damager = (LivingEntity) event.getDamager();
		amount = amount == 4 ? 5 : amount;
		int random = new Random().nextInt(100) + 1;
		if(amount * 10 > random){
			event.setCancelled(true);
			damager.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 60, 1,true));
			damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 1,true));
			for(ItemStack item : player.getInventory().getArmorContents()){
				if(item == null || item.getType() == Material.AIR){
					continue;
				}
				item.setDurability((short) (item.getDurability() + 1));
			}
		}
	}
}

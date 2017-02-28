package me.Haeseke1.Alliances.Item.Weapons.Armor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Main.Main;

public class Creeper_Armor implements Listener{
	
	public static List<Player> extension = new ArrayList<Player>();
	
	
	public static ItemStack getHelmet(){
		ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "Creeper Helmet");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 7% chance to explode!");
		lore.add(ChatColor.DARK_GREEN + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 35% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getChestplate(){
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "Creeper Chestplate");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 7% chance to explode!");
		lore.add(ChatColor.DARK_GREEN + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 35% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getLeggings(){
		ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "Creeper Leggings");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 7% chance to explode!");
		lore.add(ChatColor.DARK_GREEN + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 35% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getBoots(){
		ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "Creeper Boots");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 7% chance to explode!");
		lore.add(ChatColor.DARK_GREEN + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 35% chance!");
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
			if(!displayname.startsWith(ChatColor.GREEN + "Creeper ")){
				continue;
			}
			amount++;
		}
		amount = amount == 4 ? 5 : amount;
		if((event.getCause().equals(DamageCause.ENTITY_EXPLOSION) || event.getCause().equals(DamageCause.BLOCK_EXPLOSION)) && extension.contains(player)){
			event.setCancelled(true);
		}
		int random = new Random().nextInt(100) + 1;
		if(amount * 7 > random && !extension.contains(player)){
			extension.add(player);
            player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 2*amount, false, false);
			for(ItemStack item : player.getInventory().getArmorContents()){
				if(item == null || item.getType() == Material.AIR){
					continue;
				}
				item.setDurability((short) (item.getDurability() + 1));
			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				
				@Override
				public void run() {
					extension.remove(player);
				}
			}, 20);
		}
	}
	
	
	
	
	
}

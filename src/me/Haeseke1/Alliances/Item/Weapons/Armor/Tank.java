package me.Haeseke1.Alliances.Item.Weapons.Armor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Main.Main;

public class Tank implements Listener {
	
	
	public HashMap<Player,Extension> players = new HashMap<Player,Extension>();
	
	
	
	public static ItemStack getHelmet(){
		ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Tank Helmet");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 15% chance to nullifie!");
		lore.add(ChatColor.DARK_GREEN + "Chance goes higher with more armor pieces!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set has a 75% chance!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getChestplate(){
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Tank Chestplate");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 15% chance to nullifie!");
		lore.add(ChatColor.DARK_GREEN + "Get a hearth extra in a fight!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set give you 1 extra hearth more!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getLeggings(){
		ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Tank Leggings");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Has a 15% chance to nullifie!");
		lore.add(ChatColor.DARK_GREEN + "Get a hearth extra in a fight!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set give you 1 extra hearth more!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getBoots(){
		ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Tank Boots");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Get a hearth extra in a fight!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set give you 1 extra hearth more!");
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
			if(!displayname.startsWith(ChatColor.RED + "Tank")){
				continue;
			}
			amount++;
		}
		amount = amount == 4 ? 5 : amount;
		if(amount > 0){
			if(!players.containsKey(player)){
				players.put(player, new Extension(player, amount * 2));
			}else{
				players.get(player).resetScheduler();
			}
			return;
		}
	}
	
	@EventHandler
	private void playerleave(PlayerQuitEvent event){
		if(players.containsKey(event.getPlayer())){
			players.get(event.getPlayer()).forceReset();
		}
	}
	
	
	public class Extension {
		
		public Player damaged;
		public int scheduler;
		public int extra_Health;
		
		
		
		public Extension(Player damaged, int extra_health) {
			this.damaged = damaged;
			this.extra_Health = extra_health;
			damaged.setMaxHealth(damaged.getMaxHealth() + extra_Health);
			damaged.setHealth(damaged.getHealth() + extra_Health);
			startScheduler();
		}
		
		private void startScheduler(){
			scheduler = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				
				@Override
				public void run() {
					damaged.setMaxHealth(damaged.getMaxHealth() - extra_Health);
					players.remove(damaged);
				}
			},160);
		}
		
		private void forceReset(){
			damaged.setMaxHealth(damaged.getMaxHealth() - extra_Health);
			players.remove(damaged);
		}
		
		private void resetScheduler(){
			Bukkit.getScheduler().cancelTask(scheduler);
			startScheduler();
		}
		
	}
	
	

}

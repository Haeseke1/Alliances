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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Haeseke1.Alliances.Main.Main;

public class Golden implements Listener {
	
	
	public HashMap<Player,Extension> players = new HashMap<Player,Extension>();
	
	
	
	public static ItemStack getHelmet(){
		ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Golden Helmet");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Get 2 golden hearths extra in a fight!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set give you 2 extra golden hearth more!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getChestplate(){
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Golden Chestplate");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Get 2 golden hearths extra in a fight!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set give you 2 extra golden hearth more!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getLeggings(){
		ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Golden Leggings");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Get 2 golden hearths extra in a fight!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set give you 2 extra golden hearth more!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack getBoots(){
		ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Golden Boots");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Get 2 golden hearths extra in a fight!");
		lore.add(ChatColor.DARK_GREEN + "Full armor set give you 2 extra golden hearth more!");
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
			if(!displayname.startsWith(ChatColor.GOLD + "Golden ")){
				continue;
			}
			amount++;
		}
		amount = amount == 4 ? 5 : amount;
		if(amount > 0){
			if(!players.containsKey(player)){
				players.put(player, new Extension(player, amount - 1));
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
			damaged.removePotionEffect(PotionEffectType.ABSORPTION);
			damaged.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 140, extra_health));
			startScheduler();
		}
		
		private void startScheduler(){
			scheduler = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				
				@Override
				public void run() {
					damaged.removePotionEffect(PotionEffectType.ABSORPTION);
					players.remove(damaged);
				}
			},200);
		}
		
		private void forceReset(){
			damaged.removePotionEffect(PotionEffectType.ABSORPTION);
			players.remove(damaged);
		}
		
	}
	
	

}

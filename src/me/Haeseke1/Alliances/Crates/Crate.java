package me.Haeseke1.Alliances.Crates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Crate {
	
	public static List<Crate> crates = new ArrayList<Crate>();
	
	
	public String name;
	public List<Location> locs = new ArrayList<Location>();
	public HashMap<ItemStack,Integer> rewards = new HashMap<ItemStack,Integer>();
	public Key key;
	
	public static HashMap<Player,CountDown> players = new HashMap<Player,CountDown>();
	
	public Crate(String name, List<Location> locs, HashMap<ItemStack,Integer> rewards, Key key) {
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.locs = locs;
		this.rewards = rewards;
		this.key = key;
		for(Location loc : locs){
			setHolo(loc);
		}
		crates.add(this);
	}
	
	public void addLocation(Location loc){
		locs.add(loc);
		for(Location location : locs){
			setHolo(location);
		}
	}
	
	public void setKey(Key key){
		this.key = key;
		for(Location location : locs){
			setHolo(location);
		}
	}
	
	public void addReward(ItemStack item, int luck){
		rewards.put(item, luck);
	}
	
	
	
	public void removeArmorStand(Location loc){
		for(Entity e : loc.getWorld().getNearbyEntities(loc, 2, 2, 2)){
			if(e instanceof ArmorStand && !((ArmorStand) e).isVisible()){
				e.remove();
			}
		}
	}
	
	public void setHolo(Location location){
		Location loc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
		loc.add(0.5, 0, 0.5);
		removeArmorStand(loc);
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		as.setGravity(false);
		as.setCanPickupItems(false);
		as.setCustomName(name);
		as.setCustomNameVisible(true);
		as.setVisible(false);
		loc.add(0, -0.5, 0);
		as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		as.setGravity(false);
		as.setCanPickupItems(false);
		if(key != null){
			as.setCustomName(ChatColor.AQUA + "This crate can be opened with a " + key.name + " Key");
		}else{
			as.setCustomName(ChatColor.AQUA + "This crate can be opened with a null");
		}
		as.setCustomNameVisible(true);
		as.setVisible(false);
	}
	
	
	public void openCrate(Player player){
		Inventory inv = Bukkit.createInventory(null, 27, name);
		for(int i = 0; i < 9; i++){
			if(i != 4){
				inv.setItem(i, createPanel((short) 15, ""));
				inv.setItem(i + 18, createPanel((short) 15, ""));
				continue;
			}
			inv.setItem(i, createPanel((short) 4, ""));
			inv.setItem(i + 18, createPanel((short) 4, ""));
		}
		List<ItemStack> randomRewards = new ArrayList<ItemStack>();
		for(int i = 9; i < 18;i++){
			ItemStack reward = randomReward();
			randomRewards.add(reward);
			inv.setItem(i, reward);
		}
		new CountDown(player, this, randomRewards);
		player.openInventory(inv);
		SoundManager.playSoundToPlayer(Sound.CHEST_OPEN, player);
	}
	
	
	
	public ItemStack randomReward(){
		if(rewards.isEmpty()){
			return null;
		}
		int som = 0;
		for(Integer i : rewards.values()){
			som += i;
		}
		int random = new Random().nextInt(som) + 1;
		for(Entry<ItemStack,Integer> entry : rewards.entrySet()){
			som -= entry.getValue();
			if(random > som){
				return entry.getKey();
			}
		}
		return new ItemStack(Material.DIAMOND_SWORD);
	}
	
	
	
	public ItemStack createPanel(short color, String name){
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1 ,color);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		item.setItemMeta(im);
		return item;
	}
	
	
	public class CountDown {
		
		public Player player;
		
		public int max_value = 1;
		public int tick = 0;
		
		public List<ItemStack> rewards;
		public Crate crate;
		
		public int scheduler;
		
		public CountDown(Player player, Crate crate, List<ItemStack> rewards) {
			players.put(player, this);
			this.player = player;
			this.crate = crate;
			this.rewards = rewards;
			scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
				
				@Override
				public void run() {
					tick++;
					if(tick == max_value){
						if(max_value == 20){
							getReward();
							return;
						}
						max_value += 1;
						tick = 0;
						updateInventory();
					}
					
				}
			}, 1, 1);
		}
		
		public void updateInventory(){
			InventoryView inv = player.getOpenInventory();
			rewards.remove(0);
			rewards.add(crate.randomReward());
			for(int i = 9; i < 18;i++){
				inv.setItem(i, rewards.get(i - 9));
			}
			SoundManager.playSoundToPlayer(Sound.ORB_PICKUP, player);
		}
		
		public void getReward(){
			if((player.getInventory().firstEmpty() == -1)){
				player.getWorld().dropItem(player.getLocation(), rewards.get(4));
			}else{
				player.getInventory().setItem(player.getInventory().firstEmpty(),rewards.get(4));
			}
			InventoryView inv = player.getOpenInventory();
			inv.setItem(4, createPanel((short) 0, ""));
			inv.setItem(22, createPanel((short) 0, ""));
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				
				@Override
				public void run() {
					if(inv.getTopInventory().getName().equalsIgnoreCase(crate.name)){
						inv.setItem(4, createPanel((short) 4, ""));
						inv.setItem(22, createPanel((short) 4, ""));
					}
				}
			},7);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				
				@Override
				public void run() {
					if(inv.getTopInventory().getName().equalsIgnoreCase(crate.name)){
						inv.setItem(4, createPanel((short) 0, ""));
						inv.setItem(22, createPanel((short) 0, ""));
					}
				}
			},14);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				
				@Override
				public void run() {
					if(inv.getTopInventory().getName().equalsIgnoreCase(crate.name)){
						inv.setItem(4, createPanel((short) 4, ""));
						inv.setItem(22, createPanel((short) 4, ""));
					}
				}
			},21);
			SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
			Bukkit.getScheduler().cancelTask(scheduler);
			players.remove(player);
		}
		
		public void forceReward(){
			if((player.getInventory().firstEmpty() == -1)){
				player.getWorld().dropItem(player.getLocation(), crate.randomReward());
			}else{
				player.getInventory().setItem(player.getInventory().firstEmpty(),crate.randomReward());
			}
			Bukkit.getScheduler().cancelTask(scheduler);
			SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
			players.remove(player);
		}
	}
	
	
}

package me.Haeseke1.Alliances.PVE;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class Settings {
	
	public HashMap<Integer,Integer> zombies = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> skeletons = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> spiders = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> creepers = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> witches = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> zombie_pigmans = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> blazes = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> wither_skeletons = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> endermans = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> withers = new HashMap<Integer, Integer>();
	
	public int placing = 0;
	public int level = 1;
	
	public final int MAX_LEVEL = 5;
	public final int MAX_PLACING = 3;
	
	public Settings() {
		
	}
	
	public void createGUI(Player player){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', ChatColor.RED + "Settings"));
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Left");
		item.setItemMeta(im);
		inv.addItem(item);
		for(int i = 1; i < 8; i++){
			item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN + "Add enemy");
			item.setItemMeta(im);
			inv.setItem(i, item);
			
			inv.setItem(i + 9, createMobItemStack(i + placing));
			
			item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.RED + "Remove enemy");
			item.setItemMeta(im);
			inv.setItem(i + 18, item);
			
		}
		item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN + "Right");
		item.setItemMeta(im);
		inv.setItem(8, item);
		placing = 0;
		level = 0;
		inv.setItem(17, getSword());
		player.openInventory(inv);
	}
	
	@SuppressWarnings("deprecation")
	public void updateGUI(Player player){
		InventoryView inv = player.getOpenInventory();
		if(placing == 0){
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.RED + "Left");
			item.setItemMeta(im);
			inv.setItem(0,item);
		}else{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN + "Left");
			item.setItemMeta(im);
			inv.setItem(0,item);
		}
		
		for(int i = 1; i < 8; i++){
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN + "Add enemy");
			item.setItemMeta(im);
			inv.setItem(i, item);
			
			inv.setItem(i + 9, createMobItemStack(i + placing));
			
			item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.RED + "Remove enemy");
			item.setItemMeta(im);
			inv.setItem(i + 18, item);
			
		}
		if(MAX_PLACING == placing){
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.RED + "Right");
			item.setItemMeta(im);
			inv.setItem(8, item);
		}else{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN + "Right");
			item.setItemMeta(im);
			inv.setItem(8, item);
		}
		inv.setItem(17, getSword());
		player.updateInventory();
	}
	
	
	
	
	public ItemStack getSword(){
		ItemStack item = null;
		ItemMeta im = null;
		switch(level){
		case 1:
			item = new ItemStack(Material.WOOD_SWORD);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Level 1");
			item.setItemMeta(im);
			return item;
		case 2:
			item = new ItemStack(Material.STONE_SWORD);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Level 2");
			item.setItemMeta(im);
			return item;
		case 3:
			item = new ItemStack(Material.IRON_SWORD);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Level 3");
			item.setItemMeta(im);
			return item;
		case 4:
			item = new ItemStack(Material.GOLD_SWORD);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Level 4");
			item.setItemMeta(im);
			return item;
		case 5:
			item = new ItemStack(Material.DIAMOND_SWORD);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Level 5");
			item.setItemMeta(im);
			return item;
		}
		return null;
	}
	
	
	public ItemStack createMobItemStack(int i){
		ItemStack item = new ItemStack(Material.MONSTER_EGG);
		ItemMeta im = item.getItemMeta();
		switch(i){
		case 1:
			if(zombies.containsKey(level)){
				item.setAmount(zombies.get(level));
			}else{
				item.setAmount(0);
			}
			item.setDurability((short) EntityType.ZOMBIE.ordinal());
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Zombie Lv" + level);
			item.setItemMeta(im);
			return item;
		case 2:
			if(skeletons.containsKey(level)){
				item.setAmount(skeletons.get(level));
			}else{
				item.setAmount(0);
			}
			item.setDurability((short) EntityType.SKELETON.ordinal());
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Skeleton Lv" + level);
			item.setItemMeta(im);
			return item;
		case 3:
			if(spiders.containsKey(level)){
				item.setAmount(spiders.get(level));
			}else{
				item.setAmount(0);
			}
			item.setDurability((short) EntityType.SPIDER.ordinal());
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Spider Lv" + level);
			item.setItemMeta(im);
			return item;
		case 4:
			if(creepers.containsKey(level)){
				item.setAmount(creepers.get(level));
			}else{
				item.setAmount(0);
			}
			item.setDurability((short) EntityType.CREEPER.ordinal());
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Creeper Lv" + level);
			item.setItemMeta(im);
			return item;
		case 5:
			if(witches.containsKey(level)){
				item.setAmount(witches.get(level));
			}else{
				item.setAmount(0);
			}
			item.setDurability((short) EntityType.WITCH.ordinal());
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Witch Lv" + level);
			item.setItemMeta(im);
			return item;
		case 6:
			if(zombie_pigmans.containsKey(level)){
				item.setAmount(zombie_pigmans.get(level));
			}else{
				item.setAmount(0);
			}
			item.setDurability((short) EntityType.PIG_ZOMBIE.ordinal());
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Zombie Pigmans Lv" + level);
			item.setItemMeta(im);
			return item;
		case 7:
			if(blazes.containsKey(level)){
				item.setAmount(blazes.get(level));
			}else{
				item.setAmount(0);
			}
			item.setDurability((short) EntityType.BLAZE.ordinal());
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Blaze Lv" + level);
			item.setItemMeta(im);
			return item;
		case 8:
			if(wither_skeletons.containsKey(level)){
				item.setAmount(wither_skeletons.get(level));
			}else{
				item.setAmount(0);
			}
			item.setDurability((short) EntityType.SKELETON.ordinal());
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Wither Skeleton Lv" + level);
			item.setItemMeta(im);
			return item;
		case 9:
			if(endermans.containsKey(level)){
				item.setAmount(endermans.get(level));
			}else{
				item.setAmount(0);
			}
			item.setDurability((short) EntityType.ENDERMAN.ordinal());
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Enderman Lv" + level);
			item.setItemMeta(im);
			return item;
		case 10:
			if(withers.containsKey(level)){
				item.setAmount(withers.get(level));
			}else{
				item.setAmount(0);
			}
			item.setDurability((short) EntityType.WITHER.ordinal());
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Wither Lv" + level);
			item.setItemMeta(im);
			return item;
		default:
			break;
		}
		return null;
	}
	
	
	
	
	
	
}

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
	public HashMap<Integer,Integer> zombie_pigmans = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> blazes = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> wither_skeletons = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> endermans = new HashMap<Integer, Integer>();
	public HashMap<Integer,Integer> withers = new HashMap<Integer, Integer>();
	
	public final int ZombieLV1 = 2;
	public final int ZombieLV2 = 5;
	public final int ZombieLV3 = 10;
	public final int ZombieLV4 = 20;
	public final int ZombieLV5 = 35;
	
	public final int BlazeLV1 = 5;
	public final int BlazeLV2 = 10;
	public final int BlazeLV3 = 20;
	public final int BlazeLV4 = 30;
	public final int BlazeLV5 = 50;
	
	public final int SkeletonLV1 = 3;
	public final int SkeletonLV2 = 7;
	public final int SkeletonLV3 = 12;
	public final int SkeletonLV4 = 18;
	public final int SkeletonLV5 = 32;
	
	public final int SpiderLV1 = 3;
	public final int SpiderLV2 = 7;
	public final int SpiderLV3 = 12;
	public final int SpiderLV4 = 18;
	public final int SpiderLV5 = 32;
	
	public final int CreeperLV1 = 7;
	public final int CreeperLV2 = 14;
	public final int CreeperLV3 = 25;
	public final int CreeperLV4 = 35;
	public final int CreeperLV5 = 50;
	
	public final int Zombie_PigmanLV1 = 5;
	public final int Zombie_PigmanLV2 = 10;
	public final int Zombie_PigmanLV3 = 20;
	public final int Zombie_PigmanLV4 = 35;
	public final int Zombie_PigmanLV5 = 50;
	
	public final int Wither_SkeletonsLV1 = 3;
	public final int Wither_SkeletonsLV2 = 6;
	public final int Wither_SkeletonsLV3 = 11;
	public final int Wither_SkeletonsLV4 = 21;
	public final int Wither_SkeletonsLV5 = 36;
	
	public final int EndermanLV1 = 3;
	public final int EndermanLV2 = 6;
	public final int EndermanLV3 = 11;
	public final int EndermanLV4 = 21;
	public final int EndermanLV5 = 36;
	
	public final int WitherLV1 = 20;
	public final int WitherLV2 = 30;
	public final int WitherLV3 = 50;
	public final int WitherLV4 = 75;
	public final int WitherLV5 = 100;
	
	public int placing = 0;
	public int level = 1;
	
	public final int MAX_LEVEL = 5;
	public final int MAX_PLACING = 2;
	
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
		level = 1;
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
	
	
	@SuppressWarnings("deprecation")
	public ItemStack createMobItemStack(int i){
		ItemStack item = new ItemStack(Material.MONSTER_EGG);
		ItemMeta im = item.getItemMeta();
		switch(i){
		case 1:
			item = new ItemStack(Material.MONSTER_EGG, 1, (short) EntityType.ZOMBIE.getTypeId());
			if(zombies.containsKey(level)){
				item.setAmount(zombies.get(level));
			}else{
				item.setAmount(0);
			}
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Zombie Lv" + level);
			item.setItemMeta(im);
			return item;
		case 2:
			item = new ItemStack(Material.MONSTER_EGG, 1, (short) EntityType.SKELETON.getTypeId());
			if(skeletons.containsKey(level)){
				item.setAmount(skeletons.get(level));
			}else{
				item.setAmount(0);
			}
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Skeleton Lv" + level);
			item.setItemMeta(im);
			return item;
		case 3:
			item = new ItemStack(Material.MONSTER_EGG, 1, (short) EntityType.SPIDER.getTypeId());
			if(spiders.containsKey(level)){
				item.setAmount(spiders.get(level));
			}else{
				item.setAmount(0);
			}
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Spider Lv" + level);
			item.setItemMeta(im);
			return item;
		case 4:
			item = new ItemStack(Material.MONSTER_EGG, 1, (short) EntityType.CREEPER.getTypeId());
			if(creepers.containsKey(level)){
				item.setAmount(creepers.get(level));
			}else{
				item.setAmount(0);
			}
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Creeper Lv" + level);
			item.setItemMeta(im);
			return item;
		case 5:
			item = new ItemStack(Material.MONSTER_EGG, 1, (short) EntityType.PIG_ZOMBIE.getTypeId());
			if(zombie_pigmans.containsKey(level)){
				item.setAmount(zombie_pigmans.get(level));
			}else{
				item.setAmount(0);
			}
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Zombie Pigmans Lv" + level);
			item.setItemMeta(im);
			return item;
		case 6:
			item = new ItemStack(Material.MONSTER_EGG, 1, (short) EntityType.BLAZE.getTypeId());
			if(blazes.containsKey(level)){
				item.setAmount(blazes.get(level));
			}else{
				item.setAmount(0);
			}
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Blaze Lv" + level);
			item.setItemMeta(im);
			return item;
		case 7:
			item = new ItemStack(Material.MONSTER_EGG, 1, (short) EntityType.SKELETON.getTypeId());
			if(wither_skeletons.containsKey(level)){
				item.setAmount(wither_skeletons.get(level));
			}else{
				item.setAmount(0);
			}
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Wither Skeleton Lv" + level);
			item.setItemMeta(im);
			return item;
		case 8:
			item = new ItemStack(Material.MONSTER_EGG, 1, (short) EntityType.ENDERMAN.getTypeId());
			if(endermans.containsKey(level)){
				item.setAmount(endermans.get(level));
			}else{
				item.setAmount(0);
			}
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Enderman Lv" + level);
			item.setItemMeta(im);
			return item;
		case 9:
			item = new ItemStack(Material.MONSTER_EGG, 1, (short) EntityType.WITHER.getTypeId());
			if(withers.containsKey(level)){
				item.setAmount(withers.get(level));
			}else{
				item.setAmount(0);
			}
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Wither Lv" + level);
			item.setItemMeta(im);
			return item;
		default:
			break;
		}
		return null;
	}
	
	public void changeMobCount(int i, int change){
		switch(i){
		case 1:
			if(zombies.containsKey(level)){
				if(zombies.get(level) + change >= 0 && zombies.get(level) + change <= 64){
					zombies.replace(level, zombies.get(level) + change);
				}
			}else{
				if(change >= 0){
					zombies.put(level, change);
				}
			}
			break;
		case 2:
			if(skeletons.containsKey(level)){
				if(skeletons.get(level) + change >= 0 && skeletons.get(level) + change <= 64){
					skeletons.replace(level, skeletons.get(level) + change);
				}
			}else{
				if(change >= 0){
					skeletons.put(level, change);
				}
			}
			break;
		case 3:
			if(spiders.containsKey(level)){
				if(spiders.get(level) + change >= 0 && spiders.get(level) + change <= 64){
					spiders.replace(level, spiders.get(level) + change);
				}
			}else{
				if(change >= 0){
					spiders.put(level, change);
				}
			}
			break;
		case 4:
			if(creepers.containsKey(level)){
				if(creepers.get(level) + change >= 0 && creepers.get(level) + change <= 64){
					creepers.replace(level, creepers.get(level) + change);
				}
			}else{
				if(change >= 0){
					creepers.put(level, change);
				}
			}
			break;
		case 5:
			if(zombie_pigmans.containsKey(level)){
				if(zombie_pigmans.get(level) + change >= 0 && zombie_pigmans.get(level) + change <= 64){
					zombie_pigmans.replace(level, zombie_pigmans.get(level) + change);
				}
			}else{
				if(change >= 0){
					zombie_pigmans.put(level, change);
				}
			}
			break;
		case 6:
			if(blazes.containsKey(level)){
				if(blazes.get(level) + change >= 0 && blazes.get(level) + change <= 64){
					blazes.replace(level, blazes.get(level) + change);
				}
			}else{
				if(change >= 0){
					blazes.put(level, change);
				}
			}
			break;
		case 7:
			if(wither_skeletons.containsKey(level)){
				if(wither_skeletons.get(level) + change >= 0 && wither_skeletons.get(level) + change <= 64){
					wither_skeletons.replace(level, wither_skeletons.get(level) + change);
				}
			}else{
				if(change >= 0){
					wither_skeletons.put(level, change);
				}
			}
			break;
		case 8:
			if(endermans.containsKey(level)){
				if(endermans.get(level) + change >= 0 && endermans.get(level) + change <= 64){
					endermans.replace(level, endermans.get(level) + change);
				}
			}else{
				if(change >= 0){
					endermans.put(level, change);
				}
			}
			break;
		case 9:
			if(withers.containsKey(level)){
				if(withers.get(level) + change >= 0 && withers.get(level) + change <= 64){
					withers.replace(level, withers.get(level) + change);
				}
			}else{
				if(change >= 0){
					withers.put(level, change);
				}
			}
			break;
		default:
			break;
		}
	}
	
	public int mobCount(){
		int amount = 0;
		for(int i : zombies.values()){
			amount = amount + i;
		}
		for(int i : skeletons.values()){
			amount = amount + i;
		}
		for(int i : spiders.values()){
			amount = amount + i;
		}
		for(int i : creepers.values()){
			amount = amount + i;
		}
		for(int i : zombie_pigmans.values()){
			amount = amount + i;
		}
		for(int i : blazes.values()){
			amount = amount + i;
		}
		for(int i : wither_skeletons.values()){
			amount = amount + i;
		}
		for(int i : endermans.values()){
			amount = amount + i;
		}
		for(int i : withers.values()){
			amount = amount + i;
		}
		return amount;
	}
	
	
	public int getCoinReward(){
		int amount = 0;
		if(zombies.containsKey(1)){
			amount += zombies.get(1) * ZombieLV1;
		}
		if(zombies.containsKey(2)){
			amount += zombies.get(2) * ZombieLV2;
		}
		if(zombies.containsKey(3)){
			amount += zombies.get(3) * ZombieLV3;
		}
		if(zombies.containsKey(4)){
			amount += zombies.get(4) * ZombieLV4;
		}
		if(zombies.containsKey(5)){
			amount += zombies.get(5) * ZombieLV5;
		}
		
		if (skeletons.containsKey(1)) {
			amount += skeletons.get(1) * SkeletonLV1;
		}
		if (skeletons.containsKey(2)) {
			amount += skeletons.get(2) * SkeletonLV2;
		}
		if (skeletons.containsKey(3)) {
			amount += skeletons.get(3) * SkeletonLV3;
		}
		if (skeletons.containsKey(4)) {
			amount += skeletons.get(4) * SkeletonLV4;
		}
		if (skeletons.containsKey(5)) {
			amount += skeletons.get(5) * SkeletonLV5;
		}
		
		if (spiders.containsKey(1)) {
			amount += spiders.get(1) * SpiderLV1;
		}
		if (spiders.containsKey(2)) {
			amount += spiders.get(2) * SpiderLV2;
		}
		if (spiders.containsKey(3)) {
			amount += spiders.get(3) * SpiderLV3;
		}
		if (spiders.containsKey(4)) {
			amount += spiders.get(4) * SpiderLV4;
		}
		if (spiders.containsKey(5)) {
			amount += spiders.get(5) * SpiderLV5;
		}
		
		if (creepers.containsKey(1)) {
			amount += creepers.get(1) * CreeperLV1;
		}
		if (creepers.containsKey(2)) {
			amount += creepers.get(2) * CreeperLV2;
		}
		if (creepers.containsKey(3)) {
			amount += creepers.get(3) * CreeperLV3;
		}
		if (creepers.containsKey(4)) {
			amount += creepers.get(4) * CreeperLV4;
		}
		if (creepers.containsKey(5)) {
			amount += creepers.get(5) * CreeperLV5;
		}
		
		if (zombie_pigmans.containsKey(1)) {
			amount += zombie_pigmans.get(1) * Zombie_PigmanLV1;
		}
		if (zombie_pigmans.containsKey(2)) {
			amount += zombie_pigmans.get(2) * Zombie_PigmanLV2;
		}
		if (zombie_pigmans.containsKey(3)) {
			amount += zombie_pigmans.get(3) * Zombie_PigmanLV3;
		}
		if (zombie_pigmans.containsKey(4)) {
			amount += zombie_pigmans.get(4) * Zombie_PigmanLV4;
		}
		if (zombie_pigmans.containsKey(5)) {
			amount += zombie_pigmans.get(5) * Zombie_PigmanLV5;
		}
		
		if (blazes.containsKey(1)) {
			amount += blazes.get(1) * BlazeLV1;
		}
		if (blazes.containsKey(2)) {
			amount += blazes.get(2) * BlazeLV2;
		}
		if (blazes.containsKey(3)) {
			amount += blazes.get(3) * BlazeLV3;
		}
		if (blazes.containsKey(4)) {
			amount += blazes.get(4) * BlazeLV4;
		}
		if (blazes.containsKey(5)) {
			amount += blazes.get(5) * BlazeLV5;
		}
		
		if (wither_skeletons.containsKey(1)) {
			amount += wither_skeletons.get(1) * Wither_SkeletonsLV1;
		}
		if (wither_skeletons.containsKey(2)) {
			amount += wither_skeletons.get(2) * Wither_SkeletonsLV2;
		}
		if (wither_skeletons.containsKey(3)) {
			amount += wither_skeletons.get(3) * Wither_SkeletonsLV3;
		}
		if (wither_skeletons.containsKey(4)) {
			amount += wither_skeletons.get(4) * Wither_SkeletonsLV4;
		}
		if (wither_skeletons.containsKey(5)) {
			amount += wither_skeletons.get(5) * Wither_SkeletonsLV5;
		}
		
		if (endermans.containsKey(1)) {
			amount += endermans.get(1) * EndermanLV1;
		}
		if (endermans.containsKey(2)) {
			amount += endermans.get(2) * EndermanLV2;
		}
		if (endermans.containsKey(3)) {
			amount += endermans.get(3) * EndermanLV3;
		}
		if (endermans.containsKey(4)) {
			amount += endermans.get(4) * EndermanLV4;
		}
		if (endermans.containsKey(5)) {
			amount += endermans.get(5) * EndermanLV5;
		}
		
		if (withers.containsKey(1)) {
			amount += withers.get(1) * WitherLV1;
		}
		if (withers.containsKey(2)) {
			amount += withers.get(2) * WitherLV2;
		}
		if (withers.containsKey(3)) {
			amount += withers.get(3) * WitherLV3;
		}
		if (withers.containsKey(4)) {
			amount += withers.get(4) * WitherLV4;
		}
		if (withers.containsKey(5)) {
			amount += withers.get(5) * WitherLV5;
		}
		return amount;
	}
	
	
}

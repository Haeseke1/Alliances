package me.Haeseke1.Alliances.Shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.CustomEntity.CustomEntityVillager;
import me.Haeseke1.Alliances.Economy.Coins;


public class Shop {
	
	
	public static List<Shop> shops = new ArrayList<Shop>();
	
	public List<Villager> vendors = new ArrayList<Villager>();
	public List<Chunk> chunks = new ArrayList<Chunk>();
	
	public List<SItem> shopItems = new ArrayList<SItem>();
	public String name;
	
	
	public Shop(String name, List<SItem> shopItems, List<Location> locations){
		this.name = name;
		this.shopItems = shopItems;
		for(Location loc : locations){
			if(!loc.getChunk().isLoaded()){
				loc.getChunk().load();
			}
			Villager cev = CustomEntityVillager.spawn(loc);
			cev.setCustomName(name);
			cev.setCustomNameVisible(true);
			vendors.add(cev);
			chunks.add(loc.getChunk());
		}
		shops.add(this);
	}
	
	
	public void addSItem(SItem sitem){
		shopItems.add(sitem);
		
	}
	
	public void addVendor(Location loc){
		if(!loc.getChunk().isLoaded()){
			loc.getChunk().load();
		}
		Villager cev = CustomEntityVillager.spawn(loc);
		cev.setCustomName(name);
		cev.setCustomNameVisible(true);
		vendors.add(cev);
		chunks.add(loc.getChunk());
	}
	
	public void despawnVendors(){
		for(Villager cev : vendors){
			cev.remove();
		}
	}
	
	public void createInventory(Player player){
		Inventory inv = Bukkit.createInventory(null, 27, name);
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Left");
		item.setItemMeta(im);
		inv.addItem(item);
		for(int i = 1; i < 8; i++){
			if(shopItems.size() < i){
				break;
			}
			SItem sitem = shopItems.get(i - 1);
			inv.setItem(i, sitem.item);
			if(sitem.buy){
				if(Coins.getPlayerCoins(player) >= sitem.buyV){
					item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
					im = item.getItemMeta();
					im.setDisplayName(ChatColor.GREEN + "Buy "  + ChatColor.GRAY + "(" + sitem.buyV + " coins)");
					item.setItemMeta(im);
				}else{
					item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
					im = item.getItemMeta();
					im.setDisplayName(ChatColor.RED + "Buy "  + ChatColor.GRAY + "(" + sitem.buyV + " coins)");
					item.setItemMeta(im);
				}
			}else{
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
				im = item.getItemMeta();
				im.setDisplayName(ChatColor.RED + "Unavailable");
				item.setItemMeta(im);
			}
			inv.setItem(i + 9, item);
			
			if(sitem.sell){
				if(player.getInventory().contains(sitem.item, sitem.item.getAmount())){
					item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
					im = item.getItemMeta();
					im.setDisplayName(ChatColor.GREEN + "Sell "  + ChatColor.GRAY + "(" + sitem.buyV + " coins)");
					item.setItemMeta(im);
				}else{
					item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
					im = item.getItemMeta();
					im.setDisplayName(ChatColor.RED + "Sell "  + ChatColor.GRAY + "(" + sitem.buyV + " coins)");
					item.setItemMeta(im);
				}
			}else{
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
				im = item.getItemMeta();
				im.setDisplayName(ChatColor.RED + "Unavailable");
				item.setItemMeta(im);
			}
			inv.setItem(i + 18, item);
			if(shopItems.size() > 7){
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
				im = item.getItemMeta();
				im.setDisplayName(ChatColor.GREEN + "Right");
				item.setItemMeta(im);
			}else{
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
				im = item.getItemMeta();
				im.setDisplayName(ChatColor.RED + "Right");
				item.setItemMeta(im);
			}
			inv.setItem(8, item);
		}
		player.openInventory(inv);
	}
	
	public boolean isVendor(Villager v){
		if(vendors.contains(v)){
			return true;
		}
		return false;
	}
	
}

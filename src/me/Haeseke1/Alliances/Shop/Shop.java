package me.Haeseke1.Alliances.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.CustomEntity.CustomEntityVillager;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.InventoryManager;


public class Shop {
	
	public static List<Shop> shops = new ArrayList<Shop>();
	
	public HashMap<Player,Integer> placing = new HashMap<Player,Integer>();
	
	public List<Villager> vendors = new ArrayList<Villager>();
	public List<Chunk> chunks = new ArrayList<Chunk>();
	public List<SItem> shopItems = new ArrayList<SItem>();
	
	public String name;
	
	public Shop(String name, List<SItem> shopItems, List<Location> locations){
		this.name = name;
		this.shopItems = shopItems;
		for(Location loc : locations){
			createVillager(loc);
		}
		shops.add(this);
	}
	
	private void createVillager(Location loc){
		if(!loc.getChunk().isLoaded()){
			loc.getChunk().load();
		}
		Villager cev = CustomEntityVillager.spawn(loc);
		cev.setRemoveWhenFarAway(false);
		cev.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
		cev.setCustomNameVisible(true);
		vendors.add(cev);
		chunks.add(loc.getChunk());
	}
		
	public void addSItem(SItem sitem){
		shopItems.add(sitem);
	}
	
	public void addVendor(Location loc){
		createVillager(loc);
	}
	
	public void despawnVendors(){
		for(Villager cev : vendors){
			cev.remove();
		}
	}
	
	public boolean isVendor(Villager v){
		if(vendors.contains(v)){
			return true;
		}
		return false;
	}
	
	public void createInventory(Player player){
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', name));
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
				if(InventoryManager.containsItems(player.getInventory(), sitem.item, sitem.item.getAmount())){
					item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
					im = item.getItemMeta();
					im.setDisplayName(ChatColor.GREEN + "Sell "  + ChatColor.GRAY + "(" + sitem.sellV + " coins)");
					item.setItemMeta(im);
				}else{
					item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
					im = item.getItemMeta();
					im.setDisplayName(ChatColor.RED + "Sell "  + ChatColor.GRAY + "(" + sitem.sellV + " coins)");
					item.setItemMeta(im);
				}
			}else{
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
				im = item.getItemMeta();
				im.setDisplayName(ChatColor.RED + "Unavailable");
				item.setItemMeta(im);
			}
			inv.setItem(i + 18, item);
		}
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
		placing.put(player, 0);
		player.openInventory(inv);
	}
	
	@SuppressWarnings("deprecation")
	public void updateInventory(Player player){
		InventoryView inv = player.getOpenInventory();
		ItemStack item;
		ItemMeta im;
		if(placing.get(player) > 0){
			item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN + "Left");
			item.setItemMeta(im);
		}else{
			item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.RED + "Left");
			item.setItemMeta(im);
		}
		inv.setItem(0,item);
		for(int i = 1; i < 8; i++){
			if(shopItems.size() < i){
				break;
			}
			SItem sitem = shopItems.get(i - 1 + placing.get(player));
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
				if(InventoryManager.containsItems(player.getInventory(), sitem.item, sitem.item.getAmount())){
					item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
					im = item.getItemMeta();
					im.setDisplayName(ChatColor.GREEN + "Sell "  + ChatColor.GRAY + "(" + sitem.sellV + " coins)");
					item.setItemMeta(im);
				}else{
					item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
					im = item.getItemMeta();
					im.setDisplayName(ChatColor.RED + "Sell "  + ChatColor.GRAY + "(" + sitem.sellV + " coins)");
					item.setItemMeta(im);
				}
			}else{
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
				im = item.getItemMeta();
				im.setDisplayName(ChatColor.RED + "Unavailable");
				item.setItemMeta(im);
			}
			inv.setItem(i + 18, item);
			if(shopItems.size() > 7 + placing.get(player)){
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
		player.updateInventory();
	}
}

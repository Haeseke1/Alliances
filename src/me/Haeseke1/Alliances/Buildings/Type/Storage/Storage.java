package me.Haeseke1.Alliances.Buildings.Type.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Buildings.Building;
import me.Haeseke1.Alliances.Buildings.BuildingType;
import net.md_5.bungee.api.ChatColor;

public class Storage extends Building{
	
	public static List<Storage> storages = new ArrayList<Storage>();
	
	public HashMap<ItemStack,Integer> items = new HashMap<ItemStack,Integer>();
	public int level = 1;
	
	public HashMap<Player,Integer> openInventory = new HashMap<Player,Integer>();
	
	public Storage(Location mainBlock, Chunk chunk, int ymin, int ymax) {
		super(mainBlock, chunk, ymin, ymax, BuildingType.STORAGE, true);
		setHolo();
		storages.add(this);
	}
	
	public Storage(Location mainBlock, Chunk chunk,int ymin, int ymax, HashMap<ItemStack,Integer> items, int level) {
		super(mainBlock, chunk, ymin, ymax, BuildingType.STORAGE, true);
		this.level = level;
		setHolo();
		this.items = items;
		storages.add(this);
		
	}
	
	
	public void setHolo(){
		Location loc = new Location(mainBlock.getWorld(), mainBlock.getX() + 0.5, mainBlock.getY(), mainBlock.getZ() + 0.5);
		removeArmorStand(loc);
		ArmorStand as = (ArmorStand) mainBlock.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		as.setGravity(false);
		as.setCanPickupItems(false);
		if(level == 0){
			as.setCustomName(ChatColor.GOLD + "Storage LV\u221E");
		}else{
			as.setCustomName(ChatColor.GOLD + "Storage LV" + level);
		}
		as.setCustomNameVisible(true);
		as.setVisible(false);
	}
	
	public void level_up(int level){
		this.level = level;
		setHolo();

	}
	
	public boolean canLevel_Up(int level){
		if(this.level == level - 1 || (level == 0 && this.level != 0)){
			return true;
		}
		return false;
	}
	
	public void removeArmorStand(Location loc){
		for(Entity e : loc.getWorld().getNearbyEntities(loc, 5, 5, 5)){
			if(e instanceof ArmorStand && !((ArmorStand) e).isVisible()){
				e.remove();
			}
		}
	}
	
	public int itemCount(){
		int tAmount = 0;
		for(int amount : items.values()){
			tAmount += amount;
		}
		return tAmount;
	}
	
	public boolean canAddItem(){
		if(level == 0){
			return true;
		}
		if(level == 1 && itemCount() < 2500){
			return true;
		}
		if(level == 2 && itemCount() < 7500){
			return true;
		}
		if(level == 3 && itemCount() < 15000){
			return true;
		}
		return false;
	}
	
	
	public void up(Player player){
		if(openInventory.get(player) > 0){
			openInventory.replace(player, openInventory.get(player) - 1);
		}
	}
	
	public void down(Player player){
		if(items.keySet().size() > 48 + (openInventory.get(player) * 8)){
			openInventory.replace(player, openInventory.get(player) + 1);
		}
	}
	
	public void openStorage(Player player){
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Storage");
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Up");
		item.setItemMeta(im);
		inv.setItem(8, item);
		if(items.keySet().size() > 48){
			item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN + "Down");
		}else{
			item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.RED + "Down");
		}
		item.setItemMeta(im);
		inv.setItem(53, item);
		List<ItemStack> types = new ArrayList<>(items.keySet());
		for(int x = 0; x < 6; x++){
			for(int y = 0; y < 8; y++){
				if(types.size() - 1 < (x * 8) + y){
					break;
				}
				inv.setItem((x*9) + y, StorageManager.addLore(types.get((x * 8) + y), items.get(types.get((x * 8) + y))));
			}
		}
		openInventory.put(player, 0);
		player.openInventory(inv);
	}
	
	@SuppressWarnings("deprecation")
	public void updateStorage(){
		for(Player player : openInventory.keySet()){
			InventoryView inv = player.getOpenInventory();
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
			ItemMeta im = item.getItemMeta();
			if(openInventory.get(player) > 0){
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
				im = item.getItemMeta();
				im.setDisplayName(ChatColor.GREEN + "Up");
			}else{
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
				im = item.getItemMeta();
				im.setDisplayName(ChatColor.RED + "Up");
			}
			item.setItemMeta(im);
			inv.setItem(8, item);
			
			
			if(items.keySet().size() > 48 + openInventory.get(player) * 8){
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
				im = item.getItemMeta();
				im.setDisplayName(ChatColor.GREEN + "Down");
			}else{
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
				im = item.getItemMeta();
				im.setDisplayName(ChatColor.RED + "Down");
			}
			item.setItemMeta(im);
			inv.setItem(53, item);
			
			inv.setItem(17, new ItemStack(Material.AIR));
			inv.setItem(26, new ItemStack(Material.AIR));
			inv.setItem(35, new ItemStack(Material.AIR));
			inv.setItem(44, new ItemStack(Material.AIR));
			
			List<ItemStack> types = new ArrayList<>(items.keySet());
			for(int x = 0; x < 6; x++){
				for(int y = 0; y < 8; y++){
					if(types.size() - 1 < ((x + openInventory.get(player)) * 8) + y){
						inv.setItem((x*9) + y, new ItemStack(Material.AIR));
						continue;
					}
					inv.setItem((x*9) + y, StorageManager.addLore(types.get(((x + openInventory.get(player)) * 8) + y), items.get(types.get(((x + openInventory.get(player)) * 8) + y))));
				}
			}
			player.updateInventory();
		}
	}
	
	public void addItem(ItemStack item){
		int amount = item.getAmount();
		item.setAmount(1);
		if(items.containsKey(item)){
			items.replace(item, items.get(item) + amount);
		}else{
			items.put(item, amount);
		}
	}
	
	public ItemStack removeItem(ItemStack item, int amount){
		ItemStack item2 = new ItemStack(item);
		if(items.containsKey(item)){
			if(items.get(item) > amount){
				items.replace(item, items.get(item) - amount);
				item2.setAmount(amount);
			}else{
				item2.setAmount(items.get(item));
				items.remove(item);
			}
		}else{
			return null;
		}
		return item2;
	}
	
	
}

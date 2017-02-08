package me.Haeseke1.Alliances.Buildings.Type.Storage;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Buildings.Building;
import me.Haeseke1.Alliances.Buildings.BuildingManager;
import me.Haeseke1.Alliances.Buildings.BuildingType;
import me.Haeseke1.Alliances.Main.Main;

public class StorageListener implements Listener {
	
	@EventHandler
	private void rightClickObsidian(PlayerInteractEvent event){
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			return;
		}
		if(!event.getClickedBlock().getType().equals(Material.OBSIDIAN)){
			return;
		}
		Block block = event.getClickedBlock();
		if(!BuildingManager.isMainBlock(block.getLocation())){
			return;
		}
		Building b = BuildingManager.getBuilding(block.getLocation());
		if(!b.type.equals(BuildingType.STORAGE)){
			return;
		}
		if(b instanceof Storage){
			Storage s = (Storage) b;
			s.openStorage(event.getPlayer());
		}else{
			Storage s = StorageManager.createStorage(b);
			s.openStorage(event.getPlayer());
		}
	}
	
	@EventHandler
	private void InventroyClose(InventoryCloseEvent event){
		for(Storage s : Storage.storages){
			if(s.openInventory.containsKey((Player) event.getPlayer())){
				s.openInventory.remove((Player) event.getPlayer());
			}
		}
	}
	
	
	@EventHandler
	private void InventoryClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		Storage storage = null;
		for(Storage s : Storage.storages){
			if(s.openInventory.containsKey(player)){
				storage = s;
			}
		}
		if(storage == null){
			return;
		}
		if(event.getClick().equals(ClickType.DOUBLE_CLICK)){
			event.setCancelled(true);
			return;
		}
		final Storage s = storage;
		final int slot = event.getRawSlot();
		if(slot == 17 || slot == 26 || slot == 35 || slot == 44){
			if(event.isLeftClick()){
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
					
					@Override
					public void run() {
						if(event.getInventory().getItem(slot) == null || event.getInventory().getItem(slot).getType().equals(Material.AIR)){
							return;
						}
						s.addItem(event.getInventory().getItem(slot));
						s.updateStorage();
					}
				}, 10);
				return;
			}
			if(event.isRightClick()){
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
					
					@Override
					public void run() {
						if(event.getInventory().getItem(slot) == null || event.getInventory().getItem(slot).getType().equals(Material.AIR)){
							return;
						}
						s.addItem(event.getInventory().getItem(slot));
						s.updateStorage();
						
					}
				}, 10);
				return;
			}
			return;
		}
		if(slot == 53){
			s.down(player);
			s.updateStorage();
			event.setCancelled(true);
			return;
		}
		if(slot == 8){
			s.up(player);
			s.updateStorage();
			event.setCancelled(true);
			return;
		}
		if(slot > 53){
			if(event.isShiftClick()){
				Bukkit.broadcastMessage("test");
				if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)){
					Bukkit.broadcastMessage("away");
					event.setCancelled(true);
					return;
				}
				Bukkit.broadcastMessage("adding");
				event.setCancelled(true);
				ItemStack item = new ItemStack(event.getCurrentItem());
				storage.addItem(item);
				s.updateStorage();
				event.setCurrentItem(null);
				return;
			}
		}
		if(slot < 53){
			if(event.getCursor() != null){
				if(!event.getCursor().getType().equals(Material.AIR)){
					if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)){
						if(event.isLeftClick()){
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
								
								@Override
								public void run() {
									if(event.getInventory().getItem(slot) == null || event.getInventory().getItem(slot).getType().equals(Material.AIR)){
										return;
									}
									s.addItem(event.getInventory().getItem(slot));
									s.updateStorage();
								}
							}, 10);
							return;
						}
						if(event.isRightClick()){
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
								
								@Override
								public void run() {
									if(event.getInventory().getItem(slot) == null || event.getInventory().getItem(slot).getType().equals(Material.AIR)){
										return;
									}
									s.addItem(event.getInventory().getItem(slot));
									s.updateStorage();
									
								}
							}, 10);
							return;
						}
						return;
					}
				}
			}
			if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)){
				event.setCancelled(true);
				return;
			}
			if(player.getInventory().firstEmpty() == -1){
				event.setCancelled(true);
				return;
			}
			ItemStack item = new ItemStack(event.getCurrentItem());
			
			
			if(event.isLeftClick() || event.isShiftClick()){
				if(!StorageManager.hasLore(item)){
					return;
				}
				StorageManager.removeLore(item);
				event.setCancelled(true);
				ItemStack item2 = s.removeItem(item, item.getMaxStackSize());
				if(item2 != null){
					player.getInventory().addItem(item2);
				}
				s.updateStorage();
				return;
			}
			if(event.isRightClick()){
				if(!StorageManager.hasLore(item)){
					return;
				}
				StorageManager.removeLore(item);
				event.setCancelled(true);
				ItemStack item2 = s.removeItem(item, 1);
				if(item2 != null){
					player.getInventory().addItem(item2);
				}
				s.updateStorage();
				return;
			}
		}
		
		
		
	}
	
	
	
	
	
	
	
	
}

package me.Haeseke1.Alliances.Buildings.Type.Storage;

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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Buildings.Building;
import me.Haeseke1.Alliances.Buildings.BuildingManager;
import me.Haeseke1.Alliances.Buildings.BuildingType;
import me.Haeseke1.Alliances.Town.Town;
import me.Haeseke1.Alliances.Town.TownManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class AlchemyListener implements Listener{
	
	
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
		if(!b.type.equals(BuildingType.ALCHEMY)){
			return;
		}
		if(!TownManager.hasBuilding(b)){
			MessageManager.sendMessage(event.getPlayer(), ChatColor.RED + "This building is not claimed, build a town here to claim it!");
			return;
		}
		Town t = TownManager.getTown(b);
		if(!t.owner.getMembers().containsKey(event.getPlayer().getUniqueId())){
			MessageManager.sendMessage(event.getPlayer(), ChatColor.RED + "This town is not from your alliance!");
			return;
		}
		if(b instanceof Alchemy){
			Alchemy s = (Alchemy) b;
			s.openAlchemy(event.getPlayer());
		}else{
			if(Building.buildings.contains(b)){
				Alchemy s = AlchemyManager.createAlchemy(b);
				s.openAlchemy(event.getPlayer());
			}
		}
	}
	
	@EventHandler
	private void updateClickEvent(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		Alchemy alchemy = null;
		for(Alchemy a : Alchemy.alchemies){
			if(a.openInventory.contains(player)){
				alchemy = a;
			}
		}
		if(alchemy == null){
			return;
		}
		if(event.getClick().equals(ClickType.DOUBLE_CLICK) || event.isShiftClick()){
			alchemy.updateInventory(player);
			event.setCancelled(true);
			return;
		}
		int slot = event.getRawSlot();
		Inventory inv = event.getInventory();
		if(slot < 54 ){
			for(int i = 0; i < 53; i = i + 9){
				for(int j = 4; j < 9; j++){
					if(i+j != slot){
						continue;
					}
					event.setCancelled(true);
					if(player.getInventory().firstEmpty() == -1){
						return;
					}
					if(j == 4){
						return;
					}
					ItemStack item = inv.getItem(slot);
					if(alchemy.removeSide(inv, item)){
						player.getInventory().setItem(player.getInventory().firstEmpty(), item);
					}
				}
			}
			alchemy.updateInventory(player);
		}
	}
	
	
	@EventHandler
	private void closeinv(InventoryCloseEvent event){
		Player player = (Player) event.getPlayer();
		for(Alchemy a : Alchemy.alchemies){
			if(a.openInventory.contains(player)){
				Inventory inv = event.getInventory();
				for(int i = 0; i < 53; i = i + 9){
					for(int j = 0; j < 4; j++){
						ItemStack slot = inv.getItem(i + j);
						if(slot != null && slot.getType() != Material.AIR){
							player.getWorld().dropItem(player.getLocation(), slot);
						}
					}
				}
				a.openInventory.remove(player);
			}
		}
	}
}

package me.Haeseke1.Alliances.Shop;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.InventoryManager;
import net.minecraft.server.v1_8_R2.Material;

import org.bukkit.event.entity.EntityDamageEvent;

public class ShopEvents implements Listener{
	
	
	@EventHandler
	private void onInventroyClick(InventoryClickEvent event){
		if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)){
			return;
		}
		for(Shop s : Shop.shops){
			if(event.getInventory().getName().equalsIgnoreCase(s.name)){
				Player player = (Player) event.getWhoClicked();
				event.setCancelled(true);
				if(event.getRawSlot() == 0){
					if(s.placing.get(player) != 0){
						s.placing.replace(player, s.placing.get(player) - 1);
						s.updateInventory(player);
						return;
					}
				}
				if(event.getRawSlot() > 0 && event.getRawSlot() < 8){
					return;
				}
				if(event.getRawSlot() == 8){
					if(s.shopItems.size() > 7 + s.placing.get(player)){
						s.placing.replace(player, s.placing.get(player) + 1);
						s.updateInventory(player);
						return;
					}
				}
				if(event.getRawSlot() > 9 && event.getRawSlot() < 17){
					SItem sitem = s.shopItems.get(event.getSlot() - 10 + s.placing.get(player));
					if(sitem.buy && player.getInventory().firstEmpty() != -1){
						if(Coins.removePlayerCoins(player, sitem.buyV)){
							player.getInventory().addItem(sitem.item);
							s.updateInventory(player);
						}
					}
				}
				if(event.getRawSlot() > 18 && event.getRawSlot() < 26){
					SItem sitem = s.shopItems.get(event.getSlot() - 19 + s.placing.get(player));
					if(sitem.sell && InventoryManager.containsItems(player.getInventory(), sitem.item, sitem.item.getAmount())){
						InventoryManager.removeItems(player.getInventory(), sitem.item, sitem.item.getAmount());
						Coins.addPlayerCoins(player, sitem.sellV);
						s.updateInventory(player);
					}
				}
			}
		}
	}
	
	
	@EventHandler
	private void onPlayerCloseInv(InventoryCloseEvent event){
		for(Shop s : Shop.shops){
			if(event.getInventory().getName().equalsIgnoreCase(s.name)){
				s.placing.remove(event.getPlayer());
			}
		}
	}
	
	
	@EventHandler
	private void onPlayerInteract(PlayerInteractEntityEvent event){
		if(!(event.getRightClicked() instanceof Villager)){
			return;
		}
		Villager villager = (Villager) event.getRightClicked();
		for(Shop s : Shop.shops){
			if(s.isVendor(villager)){
				event.setCancelled(true);
				s.createInventory(event.getPlayer());
			}
		}
	}
	
	@EventHandler
	private void onEntityHit(EntityDamageEvent event){
		if(!(event.getEntity() instanceof Villager)){
			return;
		}
		Villager villager = (Villager) event.getEntity();
		for(Shop s : Shop.shops){
			if(s.isVendor(villager)){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	private void unloadChunk(ChunkUnloadEvent event){
		for(Shop s : Shop.shops){
			if(s.chunks.contains(event.getChunk())){
				event.setCancelled(true);
			}
		}
		
	}
	
	
}

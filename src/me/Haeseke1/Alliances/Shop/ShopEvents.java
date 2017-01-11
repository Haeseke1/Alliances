package me.Haeseke1.Alliances.Shop;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class ShopEvents implements Listener{
	
	
	@EventHandler
	private void onInventroyClick(InventoryClickEvent event){
		for(Shop s : Shop.shops){
			if(event.getInventory().getName().equalsIgnoreCase(s.name)){
				event.setCancelled(true);
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

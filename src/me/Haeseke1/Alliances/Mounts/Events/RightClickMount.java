package me.Haeseke1.Alliances.Mounts.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;

import me.Haeseke1.Alliances.Utils.MessageManager;

public class RightClickMount implements Listener{

	@EventHandler
	public void rightClick(PlayerInteractEntityEvent event){
		if(!(event.getRightClicked().getType()).equals(EntityType.HORSE)){ return;}
		Horse horse = (Horse) event.getRightClicked();
		if(ChatColor.stripColor(horse.getCustomName()).contains("'s mount")){
			String name = ChatColor.stripColor(horse.getCustomName()).replace("'s mount", "");
			if(name.equalsIgnoreCase(event.getPlayer().getName())){return;}
			Player player = event.getPlayer();
			MessageManager.sendMessage(player, ChatColor.RED + "This mount belongs to: " + ChatColor.GOLD + name);
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		if(event.getClickedInventory() == null){return;}
		if(event.getWhoClicked() == null){return;}
		Inventory inv = event.getInventory();
		if(ChatColor.stripColor(inv.getName()).contains("'s mount")){
			if(event.getRawSlot() == 0 || event.getRawSlot() == 1){
				event.setCancelled(true);
			}
		}
	}
}

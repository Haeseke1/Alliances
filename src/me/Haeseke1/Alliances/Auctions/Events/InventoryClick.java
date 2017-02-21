package me.Haeseke1.Alliances.Auctions.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Auctions.GUI.GUI;

public class InventoryClick implements Listener{

	@EventHandler
	public void onInvClick(InventoryClickEvent event){
		
		if(event.getInventory() == null) return;
		Inventory inv = event.getInventory();
		if(!inv.getName().equalsIgnoreCase("AUCTION HOUSE")) return;
		Player player = (Player) inv.getHolder();
		GUI gui = GUI.getGuiOfPlayer(player);
		
		event.setCancelled(true);
		if(event.getCurrentItem() == null) return;
		ItemStack item = event.getCurrentItem();
		ItemMeta m = item.getItemMeta();
		
		if(item.getItemMeta().hasDisplayName()){
			String dpname = ChatColor.stripColor(m.getDisplayName());
			switch(dpname.toLowerCase()){
			case "refresh":
				
				break;
			}
		}
	}
	
}

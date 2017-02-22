package me.Haeseke1.Alliances.Auctions.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Auctions.Auction;
import me.Haeseke1.Alliances.Auctions.GUI.GUI;
import me.Haeseke1.Alliances.Utils.LoreManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

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
				gui.updateGui(player, gui);
				break;
			case "next page":
				gui.nextPage();
				break;
			case "no page":
				break;
			case "_":
				break;
			default:
				if(!Auction.playerHasAuction(LoreManager.getOwner(item))){
					player.closeInventory();
					MessageManager.sendMessage(player, "&cThis offer is expired or closed by the owner");
					return;
				}
				if(LoreManager.getOwner(item).getName().equalsIgnoreCase(player.getName())){
					MessageManager.sendMessage(player, "&cYou can't raise the price on your own offer");
					return;
				}
				Auction auction = Auction.getAuctionFromPlayer(LoreManager.getOwner(item));
				auction.bid(player);
				break;
			}
		}
	}
	
	@EventHandler
	public void closeInventory(InventoryCloseEvent event){
		Player player = (Player) event.getPlayer();
		if(GUI.getGuiOfPlayer(player) == null) return;
		GUI gui = GUI.getGuiOfPlayer(player);
		GUI.guis.remove(gui);
	}
}

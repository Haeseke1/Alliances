package me.Haeseke1.Alliances.Auctions.Events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Auctions.Auction;
import me.Haeseke1.Alliances.Auctions.AuctionPlayer;
import me.Haeseke1.Alliances.Auctions.GUI.GUI;
import me.Haeseke1.Alliances.Utils.LoreManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class InventoryClick implements Listener{

	@EventHandler
	public void onAucInvClick(InventoryClickEvent event){
		if(event.getInventory() == null) return;
		Inventory inv = event.getInventory();
		if(!inv.getName().equalsIgnoreCase("AUCTION HOUSE")) return;
		event.setCancelled(true);
		if(event.getRawSlot() != event.getSlot()) return;
		Player player = (Player) inv.getHolder();
		GUI gui = GUI.getGuiOfPlayer(player);
		
		event.setCancelled(true);
		if(event.getCurrentItem() == null) return;
		ItemStack item = event.getCurrentItem();
		if(!item.hasItemMeta()){
			return;
		}
		ItemMeta m = item.getItemMeta();
		if(!m.hasDisplayName()){
			if(item.getType() == Material.AIR) return;
            if(LoreManager.getOwner(item) == null){
            	return;
            }
			Auction auction = Auction.getAuctionFromPlayer(LoreManager.getOwner(item));
			auction.bid(player);
			return;
		}
		if(item.getItemMeta().hasDisplayName()){
			String dpname = ChatColor.stripColor(m.getDisplayName());
			if(dpname.toLowerCase().contains("current coins:")){
				return;
			}
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
			case "previous page":
				gui.previousPage();
			    break;
			default:
                if(LoreManager.getOwner(item) == null){
                	return;
                }
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
	public void onRewardInvClick(InventoryClickEvent event){
		if(event.getInventory() == null) return;
		Inventory inv = event.getInventory();
		if(!inv.getName().equalsIgnoreCase("REWARDS")) return;
		event.setCancelled(true);
		Player player = (Player) inv.getHolder();
		GUI gui = GUI.getGuiOfPlayer(player);
		if(event.getCurrentItem() == null) return;
		ItemStack item = event.getCurrentItem();
		if(!item.hasItemMeta()){
			if(item.getType() == Material.AIR) return;
			AuctionPlayer aucplayer = AuctionPlayer.getAuctionPlayer(player);
			aucplayer.rewards.remove(event.getCurrentItem());
            player.closeInventory();
			player.getInventory().addItem(event.getCurrentItem());
			MessageManager.sendMessage(player, "&2You've successfully claimed a reward");
			SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
			gui.updateGui(player, gui);
			return;
		}
		ItemMeta m = item.getItemMeta();
		if(!m.hasDisplayName()){
			if(item.getType() == Material.AIR) return;
			AuctionPlayer aucplayer = AuctionPlayer.getAuctionPlayer(player);
			aucplayer.rewards.remove(event.getCurrentItem());
            player.closeInventory();
			player.getInventory().addItem(event.getCurrentItem());
			MessageManager.sendMessage(player, "&2You've successfully claimed a reward");
			SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
			gui.updateGui(player, gui);
			return;
		}
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
			case "previous page":
				gui.previousPage();
			    break;
			default:
				if(event.getCurrentItem().getType() == Material.AIR) return;
				AuctionPlayer aucplayer = AuctionPlayer.getAuctionPlayer(player);
				aucplayer.rewards.remove(event.getCurrentItem());
                player.closeInventory();
				player.getInventory().addItem(event.getCurrentItem());
				MessageManager.sendMessage(player, "&2You've successfully claimed a reward");
				gui.updateGui(player, gui);
				SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
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

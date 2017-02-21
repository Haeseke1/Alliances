package me.Haeseke1.Alliances.Auctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;
import net.md_5.bungee.api.ChatColor;

public class Auction {
	
	public static List<Auction> auctions = new ArrayList<>();
	
    public UUID owner;
	public UUID last_person;
    
	public int id;
	public int price;
	public int raising;
	
	public ItemStack item;
	
	public Auction(int id,UUID owner,int price,int raising,ItemStack item){
		if(auctionAlreadyExists(owner)){
			MessageManager.sendMessage(this.getOwnerAsPlayer(), "&cYou can't start two auctions at the same time");
			SoundManager.playSoundToPlayer(Sound.NOTE_BASS, Bukkit.getPlayer(owner));
			return;
		}
		this.owner = owner;
		this.id = new Random().nextInt(Integer.MAX_VALUE);
		this.price = price;
		this.raising = raising;
		this.item = item;
		for(Player player: Bukkit.getOnlinePlayers()){
			MessageManager.sendMessage(player, "&6" + Bukkit.getPlayer(owner).getName() + "&b has started an auction! (" + item.getItemMeta().getDisplayName() + "&b)");
		    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Starting price: &6" + price));
			SoundManager.playSoundToPlayer(Sound.CAT_MEOW, player);
		}
	}
	
	public void bid(Player player){
        if(Coins.getPlayerCoins(player) < price + raising){
            MessageManager.sendMessage(player, "&cYou don't have enough coins to bid on this auction offer");
            SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
            return;
        }
        if(alreadyBade()){
        	Coins.addPlayerCoins(last_person, price);
        	this.last_person = null;
        	if(!Bukkit.getPlayer(this.last_person).isOnline()) return;
        	MessageManager.sendMessage(player, "&6" + player.getName() + "&c bade more on auction &6#" + this.id);
        }
        this.last_person = player.getUniqueId();
        Coins.removePlayerCoins(player, price + raising);
        MessageManager.sendMessage(player, "&2You've raised the price on auction &6#" + this.id);
        SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
        this.price = price + raising;
        if(!this.ownerIsOnline()) return;
        MessageManager.sendMessage(this.getOwnerAsPlayer(), "&6" + player.getName() + "&2 raised the price on your auction!");
	    this.getOwnerAsPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Current price: &6" + price));
        return;
	}

	public boolean alreadyBade(){
		if(this.last_person != null) return true;
		return false;
	}
	
	@SuppressWarnings("static-access")
	public boolean auctionAlreadyExists(UUID owner){
		for(Auction auction: this.auctions){
			if(auction.owner.equals(owner)){
				return true;
			}
		}
		return false;
	}
	
	public boolean ownerIsOnline(){
	    if(Bukkit.getPlayer(owner).isOnline()) return true;
		return false;
	}
	
	public Player getOwnerAsPlayer(){
		if(ownerIsOnline()){
			return Bukkit.getPlayer(owner);
		}
		return null;
	}
	
	public void closeAuction(){
		if(ownerIsOnline()){
		Player owner = this.getOwnerAsPlayer();
		if(this.last_person == null){
        MessageManager.sendMessage(owner, "&cYour auction has been closed with no bids");
        SoundManager.playSoundToPlayer(Sound.NOTE_BASS, owner);
        return;
		}
		MessageManager.sendMessage(owner, "&2You gained &6" + price + "&2 coins from your auction!");
		SoundManager.playSoundToPlayer(Sound.LEVEL_UP, owner);
		}
		if(Bukkit.getPlayer(this.last_person).isOnline()){
	    Player last_person = Bukkit.getPlayer(this.last_person);
	    MessageManager.sendMessage(last_person, "&2You've won an auction! &e#Do /auc rewards to pick get your reward");
	    SoundManager.playSoundToPlayer(Sound.LEVEL_UP, last_person);
		}
		Coins.addPlayerCoins(owner, price);
	}
	
	public void removeAuction(){
		
	}
	
	public static void openInventory(Player player){
		Inventory inv = Bukkit.createInventory(player, 56, ChatColor.RED + "AUCTION HOUSE");
		for(int i = 37; i <= 46; i++){
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
			item.setDurability((short) 7);
			ItemMeta itemm = item.getItemMeta();
			itemm.setDisplayName("");
			item.setItemMeta(itemm);
			inv.setItem(i, item);
		}
		player.openInventory(inv);
	}
}

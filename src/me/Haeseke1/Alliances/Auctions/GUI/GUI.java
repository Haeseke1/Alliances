package me.Haeseke1.Alliances.Auctions.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Auctions.Auction;
import me.Haeseke1.Alliances.Auctions.AuctionPlayer;
import me.Haeseke1.Alliances.Buildings.Type.Storage.Alchemy;
import me.Haeseke1.Alliances.Economy.Coins;
import net.md_5.bungee.api.ChatColor;

public class GUI {
	
	public static List<GUI> guis = new ArrayList<>();
	
	public int page = 0;
	public int size;
	public int contentssize;
	
	public String name;
	
	public Player owner;
	
	public HashMap<ItemStack,Integer> contents = new HashMap<>();
	
	public Inventory inv;
	
	public AuctionPlayer aucplayer;
	
	public GUI(Player owner, String name, int size){
		this.owner = owner;
		this.name = name;
		this.size = size;
		this.contentssize = size - 18;
        inv = Bukkit.createInventory(owner, size, name);
	}
	
	public void setItem(int slot, ItemStack item){
		inv.setItem(slot, item);
	}
	
	public void setPage(int pageNumber){
		this.page = 0;
		this.updateGui(this.owner, this);
	}
	
	public boolean generateAuctions(){
		if(Auction.auctions.isEmpty()) return false;
		int slot = 0;
	    for(int i = this.contentssize * this.page; i < (this.contentssize * this.page) + 36; i++){
	    	if(Auction.auctions.size() <= i){ 
	    		return false;
	    	}
	    	this.setItem(slot, Auction.auctions.get(i).generateInvItem());
            Auction.auctions.get(i).restoreItem();
	    	slot ++;
	    }
	    if(Auction.auctions.size() == 36)return false;
	    return true;
	}
	
	public boolean generateRewards(Player player){
	    aucplayer = AuctionPlayer.getAuctionPlayer(player);
		if(aucplayer == null){
        aucplayer = new AuctionPlayer(this.owner.getUniqueId());
		}
		int slot = 0;
		for(int i = 9 * this.page; i < (9 * this.page + 9); i++){
		if(aucplayer.rewards.size() <= i) return false;
		this.setItem(slot, aucplayer.rewards.get(i));
		slot ++;
		}
		if(aucplayer.rewards.size() == 9) return false;
		return true;
	}
	
	public void openInv(){
		if(this.size == 54){
		for(ItemStack item: this.contents.keySet()){
			this.inv.setItem(this.contents.get(item), item);
		}
		this.contents.clear();
		for(int i = 0; i < this.inv.getSize(); i++){
				inv.setItem(i, null);
		}
		for(int i = 36; i <= 44; i++){
			this.setItem(i, Alchemy.createPanel((short) 15,ChatColor.BLACK + "_"));
		}
		if(!generateAuctions()){
			this.setItem(50, Alchemy.createPanel((short) 14, ChatColor.RED + "No Page"));
			if(page == 0){
		    this.setItem(48, Alchemy.createPanel((short) 14, ChatColor.RED + "No Page"));
			}else{
		    this.setItem(50, Alchemy.createPanel((short) 13, ChatColor.GREEN + "Previous Page"));
			}
		}else{
		this.setItem(50, Alchemy.createPanel((short) 13, ChatColor.GREEN + "Next Page"));
		if(page == 0){
		    this.setItem(48, Alchemy.createPanel((short) 14, ChatColor.RED + "No Page"));
		}else{
		    this.setItem(48, Alchemy.createPanel((short) 13, ChatColor.GREEN + "Previous Page"));
		}
		}
		this.setItem(49, Alchemy.createPanel((short) 9, ChatColor.AQUA + "Refresh"));
		ItemStack chest = new ItemStack(Material.CHEST);
		ItemMeta mchest = chest.getItemMeta();
		mchest.setDisplayName(ChatColor.GREEN + "Current coins: " + ChatColor.GOLD + Coins.getPlayerCoins(owner));
		chest.setItemMeta(mchest);
		this.setItem(46, chest);
		}
		if(this.size == 18){
			for(ItemStack item: this.contents.keySet()){
				this.inv.setItem(this.contents.get(item), item);
			}
			this.contents.clear();
			for(int i = 0; i < 9; i++){
				inv.setItem(i, null);
			}
			if(!generateRewards(this.owner)){
				this.setItem(14, Alchemy.createPanel((short) 14, ChatColor.RED + "No Page"));
				if(page == 0){
			    this.setItem(12, Alchemy.createPanel((short) 14, ChatColor.RED + "No Page"));
				}else{
			    this.setItem(12, Alchemy.createPanel((short) 13, ChatColor.GREEN + "Previous Page"));
				}
			}else{
			this.setItem(14, Alchemy.createPanel((short) 13, ChatColor.GREEN + "Next Page"));
			if(page == 0){
			    this.setItem(12, Alchemy.createPanel((short) 14, ChatColor.RED + "No Page"));
			}else{
			    this.setItem(14, Alchemy.createPanel((short) 13, ChatColor.GREEN + "Previous Page"));
			}
			}
			this.setItem(13, Alchemy.createPanel((short) 9, ChatColor.AQUA + "Refresh"));
		}
		owner.openInventory(inv);
		guis.add(this);
		owner.updateInventory();
	}

	public static GUI getGuiOfPlayer(Player player){
		for(GUI gui: guis){
			if(gui.owner.getName().equalsIgnoreCase(player.getName())){
				return gui;
			}
		}
		return null;
	}
	
	public void nextPage(){
		this.page = this.page + 1;
		this.updateGui(this.owner, this);
	}
	
	public void previousPage(){
		this.page = this.page - 1;
		this.updateGui(this.owner, this);
	}
	
	public void updateGui(Player player, GUI gui){
		guis.remove(gui);
		this.openInv();
	}
	
}

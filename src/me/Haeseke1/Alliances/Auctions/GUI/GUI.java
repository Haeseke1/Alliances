package me.Haeseke1.Alliances.Auctions.GUI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Auctions.Auction;
import me.Haeseke1.Alliances.Buildings.Type.Storage.Alchemy;
import net.md_5.bungee.api.ChatColor;

public class GUI {
	
	public static List<GUI> guis = new ArrayList<>();
	
	public int page = 0;
	public int size;
	public int contentssize;
	
	public String name;
	
	public Player owner;
	
	public List<ItemStack> contents;
	
	public Inventory inv;
	
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
	
	public void openInv(){
		for(int i = 36; i <= 44; i++){
			this.setItem(i, Alchemy.createPanel((short) 15,ChatColor.BLACK + ""));
		}
		if(!generateAuctions()){
			this.setItem(50, Alchemy.createPanel((short) 14, ChatColor.RED + ""));
			if(page == 0){
		    this.setItem(48, Alchemy.createPanel((short) 14, ChatColor.RED + ""));
			}else{
		    this.setItem(50, Alchemy.createPanel((short) 13, ChatColor.GREEN + "Previous Page"));
			}
		}else{
		this.setItem(50, Alchemy.createPanel((short) 13, ChatColor.GREEN + "Next Page"));
		if(page == 0){
		    this.setItem(48, Alchemy.createPanel((short) 14, ChatColor.RED + ""));
		}else{
		    this.setItem(50, Alchemy.createPanel((short) 13, ChatColor.GREEN + "Previous Page"));
		}
		}
		this.setItem(49, Alchemy.createPanel((short) 9, ChatColor.AQUA + "Refresh"));
		owner.openInventory(inv);
		guis.add(this);
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
	}
	
	public void updateGui(Player player){
		GUI gui = this.getGuiOfPlayer(player);
		guis.remove()
		player.closeInventory();
		this.openInv();
	}
	
}

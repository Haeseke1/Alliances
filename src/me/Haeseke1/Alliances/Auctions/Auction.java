package me.Haeseke1.Alliances.Auctions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Auctions.GUI.GUI;
import me.Haeseke1.Alliances.Auctions.Schedulers.Timer;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Exceptions.EmptyItemStackException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;
import me.Haeseke1.Alliances.Utils.TimeManager;
import net.md_5.bungee.api.ChatColor;

public class Auction {
	
	public static List<Auction> auctions = new ArrayList<>();
	
    public UUID owner;
	public UUID last_person;
    
	public int id;
	public int price;
	public int raising;
	public int time;
	
	public ItemStack item;
	public ItemMeta m;

	public List<String> past_lore;
	
	public Timer timer;
	
	public Auction(int id, int time,UUID owner, UUID last_person,int price,int raising, ItemStack item){
		this.owner = owner;
		if(this.id == 0){
		if(auctionAlreadyExists(owner)){
			MessageManager.sendMessage(this.getOwnerAsPlayer(), "&cYou can't start two auctions at the same time");
			SoundManager.playSoundToPlayer(Sound.NOTE_BASS, Bukkit.getPlayer(owner));
			return;
		}
		}
		if(this.last_person != null){
			this.last_person = last_person;
		}
		if(item == null){
		if(this.getOwnerAsPlayer().getItemInHand().getType() == Material.AIR){
			MessageManager.sendMessage(this.getOwnerAsPlayer(), "&cYou can't sell air");
			SoundManager.playSoundToPlayer(Sound.NOTE_BASS, Bukkit.getPlayer(owner));
			return;
		}
		}
		if(time == 0){
			this.time = 86400 * 2;
		}else{
			this.time = time;
		}
		timer = new Timer(this);
		timer.runTaskTimer(Main.plugin, 0L, 20L);
		if(item != null){
		this.item = item;
		}else{
		this.item = this.getOwnerAsPlayer().getItemInHand();
		}
        this.past_lore = this.item.getItemMeta().getLore();
		this.getOwnerAsPlayer().setItemInHand(null);
		if(id == 0){
		this.id = new Random().nextInt(Integer.MAX_VALUE);
		}else{
		this.id = id;
		}
		this.price = price;
		this.raising = raising;
		this.m = this.item.getItemMeta();
		if(item == null){
		for(Player player: Bukkit.getOnlinePlayers()){
			if(m.hasDisplayName()){
			MessageManager.sendMessage(player, "&6" + Bukkit.getPlayer(owner).getName() + "&b has started an auction! (" + this.item.getItemMeta().getDisplayName() + "&b)");
			}else{
			MessageManager.sendMessage(player, "&6" + Bukkit.getPlayer(owner).getName() + "&b has started an auction!");
			}
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Starting price: &6" + price));
			SoundManager.playSoundToPlayer(Sound.CAT_MEOW, player);
		}
		}
		auctions.add(this);
	}
	
	public void saveAuction(){
		this.restoreItem();
		Main.AuctionConfig.set("#" + this.id + ".price", price);
		Main.AuctionConfig.set("#" + this.id + ".raising", raising);
		Main.AuctionConfig.set("#" + this.id + ".time", time);
		Main.AuctionConfig.set("#" + this.id + ".owner", owner.toString());
		if(this.last_person != null){
		Main.AuctionConfig.set("#" + this.id + ".last_person", last_person.toString());
		}
		ConfigManager.setItemStackInConfig(Main.AuctionConfig, "#" + this.id + ".item", item);
        ConfigManager.saveCustomConfig(new File(Main.plugin.getDataFolder(),"auction.yml"), Main.AuctionConfig);
	}
	
	public static void saveAuctions(){
		for(Auction auction: auctions){
			auction.saveAuction();
		}
		MessageManager.sendRemarkMessage("Auctions has been saved!");
	}
	
	public static void loadAuctions(){
		for(String id: Main.AuctionConfig.getKeys(false)){
			UUID last_person;
		    UUID owner = UUID.fromString(Main.AuctionConfig.getString(id + ".owner"));
		    int price = Main.AuctionConfig.getInt(id + ".price");
		    int raising = Main.AuctionConfig.getInt(id + ".raising");
		    int time = Main.AuctionConfig.getInt(id + ".time");
		    if(Main.AuctionConfig.get(id + ".last_person") != null){
		    last_person = UUID.fromString(Main.AuctionConfig.getString(id + ".last_person"));
		    }else{
		    last_person = null;
		    }
		    try {
				ItemStack item = ConfigManager.getItemStackFromConfig(Main.AuctionConfig, id + ".item");
				@SuppressWarnings("unused")
			    Auction auction = new Auction(Integer.parseInt(id.substring(1)),time,owner,last_person,price,raising,item);
			} catch (EmptyItemStackException e) {
				e.printStackTrace();
			}
		}
		MessageManager.sendRemarkMessage("Auctions has been loaded into the code");
	}

	public void bid(Player player){
        if(Coins.getPlayerCoins(player) < price + raising){
            MessageManager.sendMessage(player, "&cYou don't have enough coins to bid on this auction offer");
            SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
            return;
        }
        if(alreadyBade()){
        	Coins.addPlayerCoins(last_person, price);
        	if(!Bukkit.getPlayer(this.last_person).isOnline()) return;
        	if(!last_person.toString().equalsIgnoreCase(player.getUniqueId().toString())){
        	MessageManager.sendMessage(player, "&6" + player.getName() + "&c bade more on the auction of &6" + Bukkit.getPlayer(this.owner).getName());
        	}
        	this.last_person = player.getUniqueId();
        }
        this.last_person = player.getUniqueId();
        Coins.removePlayerCoins(player, price + raising);
        MessageManager.sendMessage(player, "&2You've raised the price on the auction of &6" + Bukkit.getPlayer(this.owner).getName());
        SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
        this.price = price + raising;
        if(!this.ownerIsOnline()) return;
        MessageManager.sendMessage(this.getOwnerAsPlayer(), "&6" + player.getName() + "&2 raised the price on your auction!");
	    this.getOwnerAsPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8Current price: &6" + price));
	    GUI.getGuiOfPlayer(player).updateGui(player, GUI.getGuiOfPlayer(player));
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
	
	public ItemStack generateInvItem(){
		if(m.hasLore()){
			List<String> lore = m.getLore();
			lore.add(ChatColor.translateAlternateColorCodes('&', ChatColor.RED + ""));
			lore.add(ChatColor.translateAlternateColorCodes('&', "&f--------------------"));
			lore.add(ChatColor.translateAlternateColorCodes('&', "&2Price: &6" + this.price));
			lore.add(ChatColor.translateAlternateColorCodes('&', "&2Next: &6" + (this.price + this.raising)));
			lore.add(ChatColor.translateAlternateColorCodes('&', "&2By: &6" + this.getOwnerAsPlayer().getName()));
			lore.add(ChatColor.translateAlternateColorCodes('&', "&2Remaining time: &6" + TimeManager.secondsToHoursMinutesSeconds(this.time, "&6", "&2")));
			lore.add(ChatColor.translateAlternateColorCodes('&', "&f--------------------"));
		    m.setLore(lore);   
		}else{
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.translateAlternateColorCodes('&', "&2Price: &6" + this.price));
			lore.add(ChatColor.translateAlternateColorCodes('&', "&2Next: &6" + (this.price + raising)));
			lore.add(ChatColor.translateAlternateColorCodes('&', "&2By: &6" + this.getOwnerAsPlayer().getName()));
			lore.add(ChatColor.translateAlternateColorCodes('&', "&2Remaining time: &6" + TimeManager.secondsToHoursMinutesSeconds(this.time, "&6", "&2")));
		    m.setLore(lore);
		}
		item.setItemMeta(m);
		return item;
	}
	
	public void closeAuction(){
        restoreItem();
		if(ownerIsOnline()){
		Player owner = this.getOwnerAsPlayer();
		if(this.last_person == null){
        MessageManager.sendMessage(owner, "&cYour auction has been closed with no bids");
        SoundManager.playSoundToPlayer(Sound.NOTE_BASS, owner);
        this.getOwnerAsPlayer().getInventory().addItem(item);
        auctions.remove(this);
		Main.AuctionConfig.set("#" + this.id, null);
        ConfigManager.saveCustomConfig(new File(Main.plugin.getDataFolder(),"auction.yml"), Main.AuctionConfig);
        return;
		}
		MessageManager.sendMessage(owner, "&2You've gained &6" + price + "&2 coins from your auction!");
		SoundManager.playSoundToPlayer(Sound.LEVEL_UP, owner);
		}
	    Player last_person = Bukkit.getPlayer(this.last_person);
		if(Bukkit.getPlayer(this.last_person).isOnline()){
	    MessageManager.sendMessage(last_person, "&2You've won an auction! &e#Do /auc rewards to get your rewards");
	    SoundManager.playSoundToPlayer(Sound.LEVEL_UP, last_person);
		}
	    if(AuctionPlayer.getAuctionPlayer(last_person) == null){
	    	AuctionPlayer player = new AuctionPlayer(last_person.getUniqueId());
	    	player.addReward(item);
	    }else{
	    	AuctionPlayer.getAuctionPlayer(last_person).addReward(item);
	    }
		
		Coins.addPlayerCoins(owner, price);
		auctions.remove(this);
		Main.AuctionConfig.set("#" + this.id, null);
        ConfigManager.saveCustomConfig(new File(Main.plugin.getDataFolder(),"auction.yml"), Main.AuctionConfig);
	}
	
	public static void removeAuction(Player player){
		if(!playerHasAuction(player)){
			MessageManager.sendMessage(player, "&cYou haven't an active auction in the auction house");
			return;
		}
		Auction auction = getAuctionFromPlayer(player);
		auction.restoreItem();
		player.getInventory().addItem(auction.item);
		Main.AuctionConfig.set("#" + auction.id, null);
        ConfigManager.saveCustomConfig(new File(Main.plugin.getDataFolder(),"auction.yml"), Main.AuctionConfig);
		auctions.remove(auction);
		MessageManager.sendMessage(auction.getOwnerAsPlayer(), "&2You've successfully removed your auction!");
	}
	
	public static void openInventory(Player player){
		GUI gui = new GUI(player,"AUCTION HOUSE",54);
        gui.openInv();
	}
	
	public void restoreItem(){
		m.setLore(past_lore);
		item.setItemMeta(m);
	}
	
	public void countdown(){
		this.time = this.time - 1;
		if(time == 0){
			timer.cancel();
			this.closeAuction();
		}
	}
	
	public static Auction getAuctionFromPlayer(Player player){
		for(Auction auction: auctions){
			if(auction.owner.toString().equalsIgnoreCase(player.getUniqueId().toString())){
				return auction;
			}
		}
		return null;
	}
	
	public static boolean playerHasAuction(Player player){
		for(Auction auction: auctions){
			if(auction.owner.toString().equalsIgnoreCase(player.getUniqueId().toString())){
				return true;
			}
		}
		return false;
	}
}

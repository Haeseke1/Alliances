package me.Haeseke1.Alliances.Auctions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Exceptions.EmptyItemStackException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class AuctionPlayer {
	
	public static List<AuctionPlayer> AuctionPlayers = new ArrayList<>();
	
	public UUID player;
	
	public List<ItemStack> rewards = new ArrayList<>();
	
	public AuctionPlayer(UUID player){
		this.player = player;
		if(!AuctionPlayers.contains(this)){
			AuctionPlayers.add(this);
			if(getAuctionPlayer()){
				return;
			}
			return;
		}
	}
	
	public boolean getAuctionPlayer(){
		if(Main.RewardsConfig.get(this.player.toString()) == null) return false;
		for(String number: Main.RewardsConfig.getConfigurationSection(this.player.toString()).getKeys(false)){
			try {
				ItemStack item = ConfigManager.getItemStackFromConfig(Main.RewardsConfig, this.player.toString() + "." + number);
				this.rewards.add(item);
			} catch (EmptyItemStackException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public static AuctionPlayer getAuctionPlayer(Player player){
		for(AuctionPlayer aucplayer: AuctionPlayers){
			if(aucplayer.player.toString().equalsIgnoreCase(player.getUniqueId().toString())){
				return aucplayer;
			}
		}
		return null;
	}
	
	public void saveAuctionPlayer(){
		Main.RewardsConfig.set(player.toString(), null);
		int count = 0;
		for(ItemStack item: this.rewards){
			ConfigManager.setItemStackInConfig(Main.RewardsConfig, player.toString() + "." + count, item);
			count ++;
		}
		ConfigManager.saveCustomConfig(new File(Main.plugin.getDataFolder(),"rewards.yml"), Main.RewardsConfig);
	}
	
	public static void loadAuctionPlayers(){
		for(String uuid: Main.RewardsConfig.getKeys(false)){
			@SuppressWarnings("unused")
			AuctionPlayer aucplayer = new AuctionPlayer(UUID.fromString(uuid));
		}
		MessageManager.sendRemarkMessage("Auction player data has been loaded");
	}
	
	public static void saveAuctionPlayers(){
		for(AuctionPlayer player: AuctionPlayers){
			player.saveAuctionPlayer();
		}
		MessageManager.sendRemarkMessage("Auction player data has been saved");
	}
	
	public void addReward(ItemStack item){
		this.rewards.add(item);
		this.saveAuctionPlayer();
	}
	
	public void removeReward(ItemStack item){
		if(!this.rewards.contains(item)) return;
		this.rewards.remove(item);
		this.saveAuctionPlayer();
	}
	
}

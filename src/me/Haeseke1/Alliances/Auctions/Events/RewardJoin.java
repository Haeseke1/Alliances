package me.Haeseke1.Alliances.Auctions.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Haeseke1.Alliances.Auctions.AuctionPlayer;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class RewardJoin implements Listener{

	private AuctionPlayer aucplayer;
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if(AuctionPlayer.getAuctionPlayer(event.getPlayer()) == null){
			aucplayer = new AuctionPlayer(player.getUniqueId());
		}else{
		    aucplayer = AuctionPlayer.getAuctionPlayer(player);
		}
		final String message;
		switch(aucplayer.rewards.size()){
		case 0:
			message = "&cYou've no pending rewards";
			break;
		case 1:
			message = "&bYou've &6" + aucplayer.rewards.size() + "&b pending reward! &e#Do /rewards to claim it";
			break;
		default:
			message = "&bYou've &6" + aucplayer.rewards.size() + "&b pending rewards! &e#Do /rewards to claim them";
			break;
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				MessageManager.sendMessage(player, message);
			}
			
		},20);	
	}
	
}

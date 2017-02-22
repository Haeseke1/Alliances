package me.Haeseke1.Alliances.Auctions.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Haeseke1.Alliances.Auctions.AuctionPlayer;
import me.Haeseke1.Alliances.Auctions.Schedulers.DelayedMessage;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class RewardJoin implements Listener{

	private AuctionPlayer aucplayer;
	
	private String message;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if(AuctionPlayer.getAuctionPlayer(event.getPlayer()) == null){
			aucplayer = new AuctionPlayer(player.getUniqueId());
		}else{
		    aucplayer = AuctionPlayer.getAuctionPlayer(player);
		}
		switch(aucplayer.rewards.size()){
		case 0:
			this.message = "&cYou've no pending rewards";
			break;
		case 1:
			this.message = "&bYou've &6" + aucplayer.rewards.size() + "&b pending reward! &e#Do /auc rewards to claim it";
			break;
		default:
			this.message = "&bYou've &6" + aucplayer.rewards.size() + "&b pending rewards! &e#Do /auc rewards to claim them";
			break;
		}
		DelayedMessage dmessage = new DelayedMessage(1,message,player);
		dmessage.runTaskTimer(Main.plugin, 0L, 20L);
	}
	
}

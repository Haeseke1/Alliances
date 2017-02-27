package me.Haeseke1.Alliances.Vote.Events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.VotifierEvent;

import me.Haeseke1.Alliances.Auctions.AuctionPlayer;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;
import me.Haeseke1.Alliances.Vote.VotePlayer;
import me.Haeseke1.Alliances.Vote.Commands.VoteCommand;

public class VoteEvent implements Listener{

	@EventHandler
	public void onVote(VotifierEvent event){
		String name = event.getVote().getUsername();
		@SuppressWarnings("deprecation")
		OfflinePlayer offplayer = Bukkit.getOfflinePlayer(name);
		if(VoteCommand.rewards.isEmpty()){
			if(offplayer.isOnline()){
				Player player = Bukkit.getPlayer(offplayer.getUniqueId());
				MessageManager.sendMessage(player, "&cNo vote rewards registered");
				SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
				}
		}
		int randomint = new Random().nextInt(VoteCommand.rewards.size());
		if(randomint > 0){
		randomint = randomint - 1;
		}
		Player player = Bukkit.getPlayer(name);
	    if(AuctionPlayer.getAuctionPlayer(player) == null){
	    	AuctionPlayer aucplayer = new AuctionPlayer(offplayer.getUniqueId());
	    	aucplayer.addReward(VoteCommand.rewards.get(randomint));
	    }else{
	    	AuctionPlayer.getAuctionPlayer(player).addReward(VoteCommand.rewards.get(randomint));
	    }
		VotePlayer vplayer = VotePlayer.getVotePlayer(offplayer.getUniqueId());
		vplayer.addVote();
		if(offplayer.isOnline()){
		MessageManager.sendMessage(player, "&2You've received a reward! &e#Do /rewards to claim it");
		MessageManager.sendMessage(player, "&8Weekly votes: &6" + vplayer.weekly_votes);
		MessageManager.sendMessage(player, "&8Monthly votes: &6" + vplayer.monthly_votes);
		MessageManager.sendMessage(player, "&8Total votes: &6" + vplayer.total_votes);
		SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
		}
	}
	
}

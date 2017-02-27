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
import me.Haeseke1.Alliances.Vote.Commands.VoteCommand;

public class VoteEvent implements Listener{

	@EventHandler
	public void onVote(VotifierEvent event){
		String name = event.getVote().getUsername();
		@SuppressWarnings("deprecation")
		OfflinePlayer offplayer = Bukkit.getOfflinePlayer(name);
		AuctionPlayer aucplayer = new AuctionPlayer(offplayer.getUniqueId());
		if(VoteCommand.rewards.isEmpty()){
			if(offplayer.isOnline()){
				Player player = Bukkit.getPlayer(offplayer.getUniqueId());
				MessageManager.sendMessage(player, "&cNo vote rewards registered");
				SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
				}
		}
		int randomint = new Random().nextInt(VoteCommand.rewards.size() - 1);
		aucplayer.addReward(VoteCommand.rewards.get(randomint));
		if(offplayer.isOnline()){
		Player player = Bukkit.getPlayer(offplayer.getUniqueId());
		MessageManager.sendMessage(player, "&2You've received a reward! &e#Do /rewards to claim it");
		SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
		}
	}
	
}

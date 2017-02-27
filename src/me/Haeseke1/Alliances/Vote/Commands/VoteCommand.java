package me.Haeseke1.Alliances.Vote.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Auctions.AuctionPlayer;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class VoteCommand implements CommandExecutor{

	public static List<ItemStack> rewards = new ArrayList<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("This command needs to be executed by a player");
			return true;
		}
		
		Player player = (Player) sender;
		if(sender.hasPermission("alliance.vote.*")){
		if(args.length == 2 && args[0].equalsIgnoreCase("add") && args[1].equalsIgnoreCase("reward")){
			if(player.getItemInHand().getType() == Material.AIR){
				MessageManager.sendMessage(player, "&cYou can't air to the vote rewards");
				SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
				return false;
			}
			rewards.add(player.getItemInHand());
			MessageManager.sendMessage(player, "&2You've added a reward to the vote system");
			SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
			return true;
		}
		if(args.length == 1 && args[0].equalsIgnoreCase("test")){
			String name = player.getName();
			@SuppressWarnings("deprecation")
			OfflinePlayer offplayer = Bukkit.getOfflinePlayer(name);
			AuctionPlayer aucplayer = new AuctionPlayer(offplayer.getUniqueId());
			if(VoteCommand.rewards.isEmpty()){
				MessageManager.sendMessage(player, "&cNo vote rewards registered");
				SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
				return true;
			}
			int randomint = new Random().nextInt(VoteCommand.rewards.size());
			if(randomint > 0){
			randomint = randomint - 1;
			}
			aucplayer.addReward(VoteCommand.rewards.get(randomint));
			if(offplayer.isOnline()){
			MessageManager.sendMessage(player, "&2You've received a reward! &e#Do /rewards to claim it");
			SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
			}
			return true;
		}
		if(args.length == 1 && args[0].equalsIgnoreCase("clear")){
			VoteCommand.rewards.clear();
			MessageManager.sendMessage(player, "&2You've successfully cleared the vote config");
			SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
			return true;
		}
		player.sendMessage(MessageManager.infoColorCode + "===== Vote =====");
		player.sendMessage(MessageManager.infoColorCode + "Commands:");
		player.sendMessage(MessageManager.infoColorCode	+ "/voteset add reward #Add a reward to the config");
		player.sendMessage(MessageManager.infoColorCode	+ "/voteset clear #Clear the vote config");
		player.sendMessage(MessageManager.infoColorCode	+ "/voteset test #Test the reward system");
		}else{
			MessageManager.sendMessage(player, "&cYou don't have the permission to execute this command");
			SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
			return true;
		}
		return false;
	}

}

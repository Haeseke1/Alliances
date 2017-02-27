package me.Haeseke1.Alliances.Vote;

import java.io.File;

import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Exceptions.EmptyItemStackException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Vote.Commands.VoteCommand;

public class VoteManager {

	public static void saveVoteRewards(){
		for(String number: Main.VoteConfig.getKeys(false)){
			Main.VoteConfig.set(number, null);
		}
		int count = 0;
		for(ItemStack item: VoteCommand.rewards){
			ConfigManager.setItemStackInConfig(Main.VoteConfig, Integer.toString(count), item);
			count++;
		}
		ConfigManager.saveCustomConfig(new File(Main.plugin.getDataFolder(),"vote.yml"), Main.VoteConfig);
		MessageManager.sendRemarkMessage("Saved the rewards for the config system");
	}
	
	public static void loadVoteRewards() throws EmptyItemStackException{
		for(String number: Main.VoteConfig.getKeys(false)){
			ItemStack item = ConfigManager.getItemStackFromConfig(Main.VoteConfig, number);
			VoteCommand.rewards.add(item);
		}	
		MessageManager.sendRemarkMessage("Load the rewards in the vote system");
	}
	
}

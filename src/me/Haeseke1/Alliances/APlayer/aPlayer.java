package me.Haeseke1.Alliances.APlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class aPlayer{
	
	
	public static List<aPlayer> online_Players = new ArrayList<aPlayer>();

	int coins;
	Player player;
	FileConfiguration file;
	File f;
	
	int wins;
	int losses;

	public aPlayer(Player player, File f, FileConfiguration file) {
		coins = Coins.getPlayerCoins(player);
		this.player = player;
		this.file = file;
		this.f = f;
		registerConfig();
		aPlayer.online_Players.add(this);
	}
	
	
	public void registerConfig(){
		
	}
	
	public void saveConfig(){
		try {
			file.save(f);
		} catch (IOException e) {
			MessageManager.sendAlertMessage("Could not save " + file.getName() + "!");
		}
	}
}

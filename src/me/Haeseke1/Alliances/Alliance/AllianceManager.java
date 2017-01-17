package me.Haeseke1.Alliances.Alliance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringListException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class AllianceManager {

	public static boolean createNewAlliance(String name, Player owner, AllianceType type) {
		if (!playerIsInAlli(owner)) {
			if(!alliExist(name)){
				int mCoins = 0;
				int mWins = 0;
				int mLoses = 0;
				Alliance alliance = new Alliance(name, owner.getUniqueId(), mWins, mLoses, mCoins, type);
				Main.alliances.add(alliance);
				alliance.getMembers().put(owner.getUniqueId(), "Owner");
				return true;
			}
			MessageManager.sendMessage(owner, "Alliance already exist.");
			return false;
		}
		MessageManager.sendMessage(owner, "You're already in an alliance.");
		return false;
	}
	

	public static boolean playerIsInAlli(Player player) {
		for (Alliance alli : Main.alliances) {
			if (alli.getMembers().containsKey(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean alliExist(String name) {
		for (Alliance alli : Main.alliances) {
			if (alli.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	
	public static void saveAlliance(){
		FileConfiguration f = Main.alliancesConfig;
		for(String s : f.getKeys(false)){
			f.set(s, null);
		}
		for(Alliance alli : Main.alliances){
			f.set(alli.getName() + ".Type", alli.getType().toString());
			f.set(alli.getName() + ".Coins", alli.getCoins());
			f.set(alli.getName() + ".Wins", alli.getWins());
			f.set(alli.getName() + ".Losses", alli.getLoses());
			f.set(alli.getName() + ".Owner", alli.getOwner().toString());
			f.set(alli.getName() + ".Exp", alli.getExp());
			List<String> list = new ArrayList<String>();
			for(Entry<UUID,String> entry : alli.getMembers().entrySet()){
				list.add(entry.getKey().toString() + "," + entry.getValue());
			}
			f.set(alli.getName() + ".Members", list);
		}
	}
	
	public static void registerAlliance(){
		FileConfiguration f = Main.alliancesConfig;
		for(String s : f.getKeys(false)){
			AllianceType type = null;
			UUID owner = null;
			int coins = 0;
			int wins = 0;
			int losses = 0;
			int level = 0;
			HashMap<UUID,String> members = new HashMap<UUID,String>();
			try {
				type = AllianceType.getType(ConfigManager.getStringFromConfig(f, s + ".Type"));
				owner = UUID.fromString(ConfigManager.getStringFromConfig(f, s + ".Owner"));
			} catch (EmptyStringException e) {
				e.printStackTrace();
			}
			try {
				coins = ConfigManager.getIntFromConfig(f, s + ".Coins");
				wins = ConfigManager.getIntFromConfig(f, s + ".Wins");
				losses = ConfigManager.getIntFromConfig(f, s + ".Losses");
				level = ConfigManager.getIntFromConfig(f, s + ".Level");
			} catch (EmptyIntException e) {
				e.printStackTrace();
			}
			try {
				List<String> list = ConfigManager.getStringListFromConfig(f, s + ".Members");
				for(String string : list){
					members.put(UUID.fromString(string.split(",")[0]), string.split(",")[1]);
				}
				
			} catch (EmptyStringListException e) {
				e.printStackTrace();
			}
			Alliance alli = new Alliance(s, owner, wins, losses, coins, type, members, level);
			Main.alliances.add(alli);
		}
	}
	
	public static Alliance getAlliance(Player player){
		for (Alliance alli : Main.alliances) {
			if (alli.getMembers().containsKey(player.getUniqueId())) {
				return alli;
			}
		}
		return null;
	}
	
	public static Alliance getAlliance(String alli_Name){
		for (Alliance alli : Main.alliances) {
			if (alli.getName().equalsIgnoreCase(alli_Name)){
				return alli;
			}
		}
		return null;
	}
	
}

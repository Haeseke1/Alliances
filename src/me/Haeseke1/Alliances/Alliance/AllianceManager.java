package me.Haeseke1.Alliances.Alliance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringListException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Main.SQL;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class AllianceManager {

	public static boolean createNewAlliance(String name, Player owner, AllianceType type) {
		if (playerIsInAlli(owner)) {
			String message = "&cYou're already in an alliance";
			MessageManager.sendMessage(owner, message);
			return false;
		}
		if(alliExist(name)){
			String message = "&6%alli_name%&c does already exists";
			message = message.replace("%alli_name%", name);
			MessageManager.sendMessage(owner, message);
			return false;
		}
		int mCoins = 0;
		int mWins = 0;
		int mLoses = 0;
		Alliance alliance = new Alliance(name, owner.getUniqueId(), mWins, mLoses, mCoins, type);
		Main.alliances.add(alliance);
		alliance.getMembers().put(owner.getUniqueId(), "Owner");
		return true;
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
			if (ChatColor.stripColor(alli.getName()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name)))){
				return true;
			}
		}
		return false;
	}
	
	
	public static void saveAlliance(){
		if(SQL.SQL){
			SQL.removeTable("alliance");
			for(Alliance alli : Main.alliances){
				try{
					if(SQL.tableContainsData("alliance", "Name", "\"" + alli.getName() + "\"")){
						SQL.setDataToTable("alliance", "Coins", alli.getCoins() + "", "Name", "\"" + alli.getName() + "\"");
						SQL.setDataToTable("alliance", "Wins", alli.getWins() + "", "Name", "\"" + alli.getName() + "\"");
						SQL.setDataToTable("alliance", "Loses", alli.getLoses() + "", "Name", "\"" + alli.getName() + "\"");
						SQL.setDataToTable("alliance", "Score", alli.getScore() + "", "Name", "\"" + alli.getName() + "\"");
					}else{
						SQL.addDataToTable("alliance", "null,"
								+ "\"" + alli.getName() + "\","
								+ alli.getCoins() + ","
								+ alli.getWins() + ","
								+ alli.getLoses() + ","
								+ "\"" + alli.getType().toString() + "\"" + ","
								+ alli.getScore());
					}
					ResultSet rs = SQL.tableGetData("alliance", "Name", "\"" + alli.getName() + "\"");
					rs.next();
					int id = rs.getInt(1);
					for(UUID uuid : alli.getMembers().keySet()){
						SQL.setDataToTable("aPlayer", "alliance", id + "" , "UUID", "\"" + uuid.toString() + "\"");
						SQL.setDataToTable("aPlayer", "Alliance_Admin", alli.getAdmins().contains(uuid) + "" , "UUID", "\"" + uuid.toString() + "\"");
						SQL.setDataToTable("aPlayer", "Alliance_Owner", alli.getOwner().equals(uuid) + "" , "UUID", "\"" + uuid.toString() + "\"");
						SQL.setDataToTable("aPlayer", "Alliance_Name", "\"" + alli.getMembers().get(uuid) + "\"" , "UUID", "\"" + uuid.toString() + "\"");
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}else{
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
				f.set(alli.getName() + ".Score", alli.getScore());
				List<String> list = new ArrayList<String>();
				for(Entry<UUID,String> entry : alli.getMembers().entrySet()){
					list.add(entry.getKey().toString() + "," + entry.getValue());
				}
				f.set(alli.getName() + ".Members", list);
			}
		}
	}
	
	public static void registerAlliance(){
		Main.alliances.clear();
		if(SQL.SQL){
			ResultSet rs = SQL.getTable("alliance");
			try {
				while(rs.next()){
					int id = rs.getInt(1);
					ResultSet rs2 = SQL.tableGetData("aPlayer", "alliance", id + "");
					HashMap<UUID,String> members = new HashMap<>();
					List<UUID> admins = new ArrayList<UUID>();
					UUID owner = null;
					while(rs2.next()){
						members.put(UUID.fromString(rs2.getString(2)), rs2.getString(10));
						if(rs2.getBoolean("Alliance_Admin")){
							admins.add(UUID.fromString(rs2.getString(2)));
						}
						if(rs2.getBoolean("Alliance_Owner")){
							owner = UUID.fromString(rs2.getString(2));
						}
					}
					Main.alliances.add(new Alliance(rs.getString(2), owner, rs.getInt(4), rs.getInt(5), rs.getInt(3), AllianceType.getType(rs.getString(6)), members, admins, rs.getInt(7)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			FileConfiguration f = Main.alliancesConfig;
			for(String s : f.getKeys(false)){
				AllianceType type = null;
				UUID owner = null;
				int coins = 0;
				int wins = 0;
				int losses = 0;
				int score = 0;
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
					score = ConfigManager.getIntFromConfig(f, s + ".Score");
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
				Alliance alli = new Alliance(s, owner, wins, losses, coins, type, members,new ArrayList<UUID>(), score);
				Main.alliances.add(alli);
			}
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
			if (ChatColor.stripColor(alli.getName()).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', alli_Name)))){
				return alli;
			}
		}
		return null;
	}
	public static int getMemberCount(Alliance alli){
		int count = 0;
		for(UUID uuid: alli.getMembers().keySet()){
			Player player = Bukkit.getPlayer(uuid);
			if(player != null){
			if(player.isOnline()){
				count ++;
			}
			}
		}
		return count;
	}
}

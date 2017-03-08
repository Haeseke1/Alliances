package me.Haeseke1.Alliances.Economy;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Main.SQL;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;
import net.md_5.bungee.api.ChatColor;

public class Coins {

	public static int defaultCoins;

	public static int getPlayerCoins(Player player) {
		if(SQL.SQL){
			try {
				if (SQL.tableContainsData("aPlayer", "UUID", "\"" + player.getUniqueId().toString() + "\"")) {
					ResultSet rs = SQL.tableGetData("aPlayer", "UUID", "\"" + player.getUniqueId().toString() + "\"");
					rs.next();
					return rs.getInt(6);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return defaultCoins;
		}else{
			try {
				return ConfigManager.getIntFromConfig(Main.coinsConfig, player.getUniqueId().toString());
			} catch (Exception e) {
				Main.coinsConfig.set(player.getUniqueId().toString(), defaultCoins);
				return defaultCoins;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static int getPlayerCoins(String player) {
		if(SQL.SQL){
			try {
				if (SQL.tableContainsData("aPlayer", "Name", "\"" + player + "\"")) {
					ResultSet rs = SQL.tableGetData("aPlayer", "Name", "\"" + player + "\"");
					rs.next();
					return rs.getInt(6);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return defaultCoins;
		}else{
			try {
				return ConfigManager.getIntFromConfig(Main.coinsConfig,
						Bukkit.getOfflinePlayer(player).getUniqueId().toString());
			} catch (Exception e) {
				Main.coinsConfig.set(Bukkit.getOfflinePlayer(player).getUniqueId().toString(), defaultCoins);
				return defaultCoins;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void setPlayerCoins(String player, int amount) {
		if(SQL.SQL){
			SQL.setDataToTable("aPlayer", "Coins", amount + "", "Name", "\"" + player + "\"");
		}else{
			Main.coinsConfig.set(Bukkit.getOfflinePlayer(player).getUniqueId().toString(), amount);
		}
	}
	
	public static void setPlayerCoins(Player player, int amount) {
		if(SQL.SQL){
			SQL.setDataToTable("aPlayer", "Coins", amount + "", "UUID", "\"" + player.getUniqueId().toString() + "\"");
		}else{
			Main.coinsConfig.set(player.getUniqueId().toString(), amount);
		}
		
	}

	public static int addPlayerCoins(Player player, int amount) {
		if(SQL.SQL){
			int coins = getPlayerCoins(player);
			coins += amount;
			setPlayerCoins(player, coins);
			return coins;
		}else{
			try {
				int i = ConfigManager.getIntFromConfig(Main.coinsConfig, player.getUniqueId().toString());
				i += amount;
				Main.coinsConfig.set(player.getUniqueId().toString(), i);
				return ConfigManager.getIntFromConfig(Main.coinsConfig, player.getUniqueId().toString());
			} catch (Exception e) {
				Main.coinsConfig.set(player.getUniqueId().toString(), defaultCoins + amount);
				try {
					return ConfigManager.getIntFromConfig(Main.coinsConfig, player.getUniqueId().toString());
				} catch (EmptyIntException e1) {
					e1.printStackTrace();
				}
			}
			return 0;
		}
	}

	public static int addPlayerCoins(UUID uuid, int amount) {
		if(SQL.SQL){
			int coins = getPlayerCoins(Bukkit.getPlayer(uuid));
			coins += amount;
			setPlayerCoins(Bukkit.getPlayer(uuid), coins);
			return coins;
		}else{
			try {
				int i = ConfigManager.getIntFromConfig(Main.coinsConfig, uuid.toString());
				i += amount;
				Main.coinsConfig.set(uuid.toString(), i);
				OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
				if (player.isOnline()) {
					MessageManager.sendMessage(player.getPlayer(),
							ChatColor.GREEN + "You've received " + amount + " coins!");
					SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player.getPlayer());
				}
				MessageManager.sendRemarkMessage(
						"Successfully added " + amount + " to " + player.getName() + "'s bank account!");
				File file = new File(Main.plugin.getDataFolder(), "coins.yml");
				ConfigManager.saveCustomConfig(file, Main.coinsConfig);
				return ConfigManager.getIntFromConfig(Main.coinsConfig, uuid.toString());
			} catch (Exception e) {
				Main.coinsConfig.set(uuid.toString(), defaultCoins + amount);
				try {
					return ConfigManager.getIntFromConfig(Main.coinsConfig, uuid.toString());
				} catch (EmptyIntException e1) {
					e1.printStackTrace();
				}
			}
			return 0;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static int addPlayerCoins(String player, int amount) {
		if(SQL.SQL){
			int coins = getPlayerCoins(player);
			coins += amount;
			setPlayerCoins(player, coins);
			return coins;
		}else{
			try {
				int i = ConfigManager.getIntFromConfig(Main.coinsConfig,
						Bukkit.getOfflinePlayer(player).getUniqueId().toString());
				i += amount;
				Main.coinsConfig.set(Bukkit.getOfflinePlayer(player).getUniqueId().toString(), i);
				return ConfigManager.getIntFromConfig(Main.coinsConfig,
						Bukkit.getOfflinePlayer(player).getUniqueId().toString());
			} catch (Exception e) {
				Main.coinsConfig.set(Bukkit.getOfflinePlayer(player).getUniqueId().toString(), defaultCoins + amount);
				try {
					return ConfigManager.getIntFromConfig(Main.coinsConfig,
							Bukkit.getOfflinePlayer(player).getUniqueId().toString());
				} catch (EmptyIntException e1) {
					e1.printStackTrace();
				}
			}
		}
		return 0;
	}

	public static boolean removePlayerCoins(Player player, int amount) {
		if(SQL.SQL){
			int coins = getPlayerCoins(player);
			if(coins >= amount){
				coins -= amount;
				setPlayerCoins(player, coins);
				return true;
			}
			return false;
		}else{
			try {
				if (ConfigManager.getIntFromConfig(Main.coinsConfig, player.getUniqueId().toString()) >= amount) {
					int i = ConfigManager.getIntFromConfig(Main.coinsConfig, player.getUniqueId().toString());
					i -= amount;
					Main.coinsConfig.set(player.getUniqueId().toString(), i);
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				if (defaultCoins >= amount) {
					Main.coinsConfig.set(player.getUniqueId().toString(), defaultCoins - amount);
					return true;
				} else {
					return false;
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static boolean removePlayerCoins(String player, int amount) {
		if(SQL.SQL){
			int coins = getPlayerCoins(player);
			if(coins >= amount){
				coins -= amount;
				setPlayerCoins(player, coins);
				return true;
			}
			return false;
		}else{
			try {
				if (ConfigManager.getIntFromConfig(Main.coinsConfig,
						Bukkit.getOfflinePlayer(player).getUniqueId().toString()) >= amount) {
					int i = ConfigManager.getIntFromConfig(Main.coinsConfig,
							Bukkit.getOfflinePlayer(player).getUniqueId().toString());
					i -= amount;
					Main.coinsConfig.set(Bukkit.getOfflinePlayer(player).getUniqueId().toString(), i);
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				if (defaultCoins >= amount) {
					Main.coinsConfig.set(Bukkit.getOfflinePlayer(player).getUniqueId().toString(),
							defaultCoins - amount);
					return true;
				} else {
					return false;
				}
			}
		}
	}
	
	
	public static void setAllianceCoins(String alli, int amount) {
		Alliance alliance = AllianceManager.getAlliance(alli);
		alliance.setCoins(amount);
	}
	
	public static void setAllianceCoins(Player player, int amount) {
		Alliance alliance = AllianceManager.getAlliance(player);
		alliance.setCoins(amount);
	}
	
	public static void setAllianceCoins(Alliance alli, int amount) {
		alli.setCoins(amount);
	}
	
	public static int addAllianceCoins(Player player, int amount) {
		Alliance alliance = AllianceManager.getAlliance(player);
		alliance.setCoins(alliance.getCoins() + amount);
		return alliance.getCoins() + amount;
	}

	public static int addAllianceCoins(String alli, int amount) {
		Alliance alliance = AllianceManager.getAlliance(alli);
		alliance.setCoins(alliance.getCoins() + amount);
		return alliance.getCoins() + amount;
	}
	
	public static int addAllianceCoins(Alliance alli, int amount) {
		alli.setCoins(alli.getCoins() + amount);
		return alli.getCoins() + amount;
	}

	public static boolean removeAllianceCoins(Player player, int amount) {
		Alliance alliance = AllianceManager.getAlliance(player);
		if(alliance.getCoins() >= amount){
			alliance.setCoins(alliance.getCoins() - amount);
			return true;
		}
		return false;
	}

	public static boolean removeAllianceCoins(String alli, int amount) {
		Alliance alliance = AllianceManager.getAlliance(alli);
		if(alliance.getCoins() >= amount){
			alliance.setCoins(alliance.getCoins() - amount);
			return true;
		}
		return false;
	}
	
	public static boolean removeAllianceCoins(Alliance alli, int amount) {
		if(alli.getCoins() >= amount){
			alli.setCoins(alli.getCoins() - amount);
			return true;
		}
		return false;
	}
}

package me.Haeseke1.Alliances.Economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;

public class Coins {

	public static int defaultCoins;

	public static int getPlayerCoins(Player player) {
		try {
			return ConfigManager.getIntFromConfig(Main.coinsConfig, player.getUniqueId().toString());
		} catch (Exception e) {
			Main.coinsConfig.set(player.getUniqueId().toString(), defaultCoins);
			return defaultCoins;
		}
	}

	@SuppressWarnings("deprecation")
	public static int getPlayerCoins(String player) {
		try {
			return ConfigManager.getIntFromConfig(Main.coinsConfig, Bukkit.getOfflinePlayer(player).getUniqueId().toString());
		} catch (Exception e) {
			Main.coinsConfig.set(Bukkit.getOfflinePlayer(player).getUniqueId().toString(), defaultCoins);
			return defaultCoins;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void setPlayerCoins(String player, int amount) {
		Main.coinsConfig.set(Bukkit.getOfflinePlayer(player).getUniqueId().toString(), amount);
	}
	
	public static void setPlayerCoins(Player player, int amount) {
		Main.coinsConfig.set(player.getUniqueId().toString(), amount);
	}

	public static int addPlayerCoins(Player player, int amount) {
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

	@SuppressWarnings("deprecation")
	public static int addPlayerCoins(String player, int amount) {
		try {
			int i = ConfigManager.getIntFromConfig(Main.coinsConfig, Bukkit.getOfflinePlayer(player).getUniqueId().toString());
			i += amount;
			Main.coinsConfig.set(Bukkit.getOfflinePlayer(player).getUniqueId().toString(), i);
			return ConfigManager.getIntFromConfig(Main.coinsConfig, Bukkit.getOfflinePlayer(player).getUniqueId().toString());
		} catch (Exception e) {
			Main.coinsConfig.set(Bukkit.getOfflinePlayer(player).getUniqueId().toString(), defaultCoins + amount);
			try {
				return ConfigManager.getIntFromConfig(Main.coinsConfig,
						Bukkit.getOfflinePlayer(player).getUniqueId().toString());
			} catch (EmptyIntException e1) {
				e1.printStackTrace();
			}
		}
		return 0;
	}

	public static boolean removePlayerCoins(Player player, int amount) {
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

	@SuppressWarnings("deprecation")
	public static boolean removePlayerCoins(String player, int amount) {
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
				Main.coinsConfig.set(Bukkit.getOfflinePlayer(player).getUniqueId().toString(), defaultCoins - amount);
				return true;
			} else {
				return false;
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

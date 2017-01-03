package me.Haeseke1.Alliances.Economy;

import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;

public class Coins {
	
	
	public static int defaultCoins;
	
	
	
	public static int getPlayerCoins(Player player){
		try {
			return ConfigManager.getIntFromConfig(Main.coins, player.getUniqueId().toString());
		} catch (EmptyStringException e) {
			Main.coins.set(player.getUniqueId().toString(), defaultCoins);
			return defaultCoins;
		}
	}
	

	public static int addPlayerCoins(Player player,int amount){
		try {
			Main.coins.set(player.getUniqueId().toString(), ConfigManager.getIntFromConfig(Main.coins, player.getUniqueId().toString() + amount));
			return ConfigManager.getIntFromConfig(Main.coins, player.getUniqueId().toString());
		} catch (EmptyStringException e) {
			Main.coins.set(player.getUniqueId().toString(), defaultCoins + amount);
			try {
				return ConfigManager.getIntFromConfig(Main.coins, player.getUniqueId().toString());
			} catch (EmptyStringException e1) {
				e1.printStackTrace();
			}
		}
		return 0;
	}
	
	
	public static boolean removePlayerCoins(Player player,int amount){
		try {
			if(ConfigManager.getIntFromConfig(Main.coins, player.getUniqueId().toString()) >= amount){
				int i = ConfigManager.getIntFromConfig(Main.coins, player.getUniqueId().toString());
				i -= amount;
				Main.coins.set(player.getUniqueId().toString(), i);
				return true;
			}else{
				return false;
			}
		} catch (EmptyStringException e) {
			if(defaultCoins >= amount){
				Main.coins.set(player.getUniqueId().toString(), defaultCoins - amount);
				return true;
			}else{
				return false;
			}
		}
	}
	
}

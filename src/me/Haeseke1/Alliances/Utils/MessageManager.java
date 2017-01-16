package me.Haeseke1.Alliances.Utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;

public class MessageManager {

	public static HashMap<String, String> messages = new HashMap<String, String>();
	
	public static String infoColorCode;
	public static String alertColorCode;
	public static String remarkColorCode;


	public static void sendMessage(Player player, String message) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.cmdlogo + message));
	}

	public static void sendInfoMessage(String message) {
		Bukkit.getConsoleSender().sendMessage(Main.cmdlogo + infoColorCode + message);
	}

	public static void sendAlertMessage(String message) {
		Bukkit.getConsoleSender().sendMessage(Main.cmdlogo + alertColorCode + message);
	}

	public static void sendBroadcast(String message) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.cmdlogo + message));
	}

	public static void sendRemarkMessage(String message) {
		Bukkit.getConsoleSender().sendMessage(Main.cmdlogo + remarkColorCode + message);
	}
	
	public static String translateColorCode(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	
	public static void registerMessages(){
		FileConfiguration file = Main.messageConfig;
		for(String s : file.getKeys(false)){
			try {
				messages.put(s.toLowerCase(), ConfigManager.getStringFromConfig(file, s));
			} catch (EmptyStringException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static String getMessage(String s){
		if(messages.containsKey(s.toLowerCase())){
			return messages.get(s.toLowerCase());
		}
		return null;
	}
}

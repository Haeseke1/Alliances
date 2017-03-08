package me.Haeseke1.Alliances.Utils;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Main.Main;

public class MessageManager {
	
	public static String infoColorCode;
	public static String alertColorCode;
	public static String remarkColorCode;


	public static void sendMessage(Player player, String message) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.cmdlogo + message));
	}

	public static void sendMessage(CommandSender player, String message) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.cmdlogo + message));
	}
	
	public static void sendInfoMessage(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.cmdlogo + infoColorCode + message));
	}

	public static void sendAlertMessage(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.cmdlogo + alertColorCode + message));
	}

	public static void sendBroadcast(String message) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.cmdlogo + message));
	}

	public static void sendRemarkMessage(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.cmdlogo + remarkColorCode + message));
	}
	
	public static String translateColorCode(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}

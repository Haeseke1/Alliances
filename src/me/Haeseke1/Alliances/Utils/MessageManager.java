package me.Haeseke1.Alliances.Utils;

import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Main.Main;
import net.md_5.bungee.api.ChatColor;

public class MessageManager {
	
	public static void sendInfoMessage(Player player,String message){
		player.sendMessage(Main.cmdlogo + message);
	}
	public static void sendAlertMessage(Player player,String message){
		player.sendMessage(Main.cmdlogo + ChatColor.RED + message);
	}
	public static void sendRemarkMessage(Player player,String message){
		player.sendMessage(Main.cmdlogo + ChatColor.GREEN +  message);
	}
	
	public static String translateColorCode(String message){
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
}

package me.Haeseke1.Alliances.Chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;

public class ChatEvent implements Listener{
	
	
	public static String format;
	
	
	@EventHandler
	private void playerchat2(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		if(AllianceManager.playerIsInAlli(player)){
			Alliance alli = AllianceManager.getAlliance(player);
			String rank = alli.getMembers().get(player.getUniqueId());
			String name = player.getDisplayName();
			String _format = format.replace("%alli_name%",ChatColor.GRAY + "<" + ChatColor.RESET + alli.getName() + ChatColor.GRAY + ">").replace("%player_name%", name).replace("%alli_rank%", rank);
			_format = ChatColor.translateAlternateColorCodes('&', _format);
			event.setFormat( _format + event.getMessage());
		}else{
			String name = player.getDisplayName();
			String _format = format.replace("%alli_name%", "").replace("%player_name%", name).replace("%alli_rank%", "");
			_format = ChatColor.translateAlternateColorCodes('&', _format);
			event.setFormat(_format + event.getMessage());
		}
	}
}

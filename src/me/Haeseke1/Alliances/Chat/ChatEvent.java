package me.Haeseke1.Alliances.Chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Main.Alliance;

@SuppressWarnings("deprecation")
public class ChatEvent implements Listener{
	
	
	public static String format;
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	private void playerChat(PlayerChatEvent event){
		Player player = event.getPlayer();
		if(AllianceManager.playerIsInAlli(player)){
			Alliance alli = AllianceManager.getAlliance(player);
			String rank = alli.getMembers().get(player.getUniqueId());
			String name = player.getDisplayName();
			String _format = format.replace("%alli_name%", alli.getName()).replace("%name%", name).replace("%alli_rank%", rank);
			_format = ChatColor.translateAlternateColorCodes('&', _format);
			event.setFormat( _format + event.getMessage());
		}else{
			String name = player.getDisplayName();
			String _format = format.replace("%name%", name);
			_format = ChatColor.translateAlternateColorCodes('&', _format);
			event.setFormat(_format + event.getMessage());
		}
	}
}

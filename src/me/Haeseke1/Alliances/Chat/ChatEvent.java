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
			event.setFormat(ChatColor.translateAlternateColorCodes('&', format.replaceFirst("%alli_name%", alli.getName()).replaceAll("%name%", name).replaceAll("%alli_rank%", rank)) + event.getMessage());
		}else{
			String name = player.getDisplayName();
			event.setFormat(ChatColor.translateAlternateColorCodes('&', format.replaceAll("%name%", name)) + event.getMessage());
		}
	}
}

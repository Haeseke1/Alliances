package me.Haeseke1.Alliances.Chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Main.Alliance;

@SuppressWarnings("deprecation")
public class ChatEvent implements Listener{
	
	@EventHandler
	private void playerChat(PlayerChatEvent event){
		Player player = event.getPlayer();
		if(AllianceManager.playerIsInAlli(player)){
			Alliance alli = AllianceManager.getAlliance(player);
			String rank = alli.getMembers().get(player.getUniqueId());
			event.setFormat(alli.getName() + " " + player.getDisplayName() + " " + rank + "> " + event.getMessage());
		}else{
			event.setFormat(player.getDisplayName() + ChatColor.RESET + "> " + event.getMessage());
		}
		
	}
	
	
}

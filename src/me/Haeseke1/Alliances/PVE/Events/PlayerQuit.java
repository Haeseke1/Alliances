package me.Haeseke1.Alliances.PVE.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Haeseke1.Alliances.PVE.GroupManager;

public class PlayerQuit implements Listener {
	
	@EventHandler
	private void playerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		if(GroupManager.hasGroup(player)){
			GroupManager.getGroup(player).disband();
		}
	}
}

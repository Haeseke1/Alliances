package me.Haeseke1.Alliances.Portals.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.Haeseke1.Alliances.Portals.Portal;

public class PortalMoveEvent implements Listener{

	@EventHandler
	public void onMove(PlayerMoveEvent event){
		for(Portal portal: Portal.portals){
			if(portal.playerIsInPortal(event.getPlayer())){
				portal.teleportPlayer(event.getPlayer());
				return;
			}
		}
	}
	
}

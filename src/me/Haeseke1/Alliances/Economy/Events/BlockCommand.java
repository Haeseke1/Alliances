package me.Haeseke1.Alliances.Economy.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BlockCommand implements Listener{

    @EventHandler
	public void blockBalance(PlayerCommandPreprocessEvent event){
		if(event.getMessage().contains("/balance")){ event.setCancelled(true); return;}
		if(event.getMessage().contains("/?")){ event.setCancelled(true); return;}
		if(event.getMessage().contains("/pl")){ event.setCancelled(true); return;}
		if(event.getMessage().contains("/plugin")){ event.setCancelled(true); return;}
	}
	
}

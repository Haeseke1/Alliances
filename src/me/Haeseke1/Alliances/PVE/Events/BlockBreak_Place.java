package me.Haeseke1.Alliances.PVE.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.Haeseke1.Alliances.PVE.GroupManager;

public class BlockBreak_Place implements Listener {
	
	@EventHandler
	private void blockbreak(BlockBreakEvent event){
		Player player = event.getPlayer();
		if(GroupManager.hasGroup(player)){
			event.setCancelled(true);
		}
	}
	
	
	@EventHandler
	private void blockplace(BlockPlaceEvent event){
		Player player = event.getPlayer();
		if(GroupManager.hasGroup(player)){
			event.setCancelled(true);
		}
	}
	

}

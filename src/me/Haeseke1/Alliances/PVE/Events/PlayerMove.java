package me.Haeseke1.Alliances.PVE.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.Haeseke1.Alliances.PVE.Arena;
import me.Haeseke1.Alliances.PVE.PVE;

public class PlayerMove implements Listener{
	
	@EventHandler
	private void playerMove(PlayerMoveEvent event){
		if(PVE.main == null){
			return;
		}
		for(Arena arena : PVE.main.arenas){
			if(arena.playing && arena.startCountdown){	
				if(arena.group.members.contains(event.getPlayer())){
					event.setCancelled(true);
				}
			}
		}
	}

}

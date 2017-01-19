package me.Haeseke1.Alliances.Arena;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import me.Haeseke1.Alliances.Exceptions.EmptyStringException;

public class ArenaEvents implements Listener{

	@EventHandler
	public void onBreakInArena(BlockBreakEvent event){
		Player player = event.getPlayer();
		if(ArenaManager.isInArena(player)){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlaceInArena(BlockPlaceEvent event){
		Player player = event.getPlayer();
		if(ArenaManager.isInArena(player)){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) throws EmptyStringException{
	  if(event.getEntity() instanceof Player){
		  Player player = (Player) event.getEntity();
		 if(ArenaManager.isInArena(player)){
		  Arena arena = ArenaManager.getArenaOfPlayer(player);
		  if(ArenaManager.checkStatus(arena.getName(), "COUNTING")){
			  event.setCancelled(true);
			  return;
		  }
		  if(player.getHealth() < 0){
			  ArenaManager.kickOnDeath(player);
		  }
	    }
	  }
	}
}

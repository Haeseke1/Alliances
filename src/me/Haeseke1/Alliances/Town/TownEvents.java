package me.Haeseke1.Alliances.Town;

import java.util.Iterator;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.Haeseke1.Alliances.Utils.MessageManager;



public class TownEvents implements Listener {
	
	
	
	@EventHandler
	private void onBlockBreak(BlockBreakEvent event){
		for(Town town : Town.towns){
			if(town.chunks.contains(event.getBlock().getLocation().getChunk())){
				if(!town.owner.getMembers().containsKey(event.getPlayer().getUniqueId())){
					event.setCancelled(true);
					String message = "&cYou can't break town blocks";
					message = message.replace("%town_name%", town.name);
					MessageManager.sendMessage(event.getPlayer(), message);
				}
			}
		}
	}

	@EventHandler
	private void onBlockPlace(BlockPlaceEvent event){
		for(Town town : Town.towns){
			if(town.chunks.contains(event.getBlock().getLocation().getChunk())){
				if(!town.owner.getMembers().containsKey(event.getPlayer().getUniqueId())){
					event.setCancelled(true);
					String message = "&cYou can't place town blocks";
					message = message.replace("%town_name%", town.name);
					MessageManager.sendMessage(event.getPlayer(), message);
				}
			}
		}
	}
	
	@EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> destroyed = event.blockList();
        Iterator<Block> it = destroyed.iterator();
        while (it.hasNext()) {
            Block block = it.next();
    		for(Town town : Town.towns){
    			if(town.chunks.contains(block.getLocation().getChunk())){
    				event.setCancelled(true);
    			}
    		}
        }
    }
	
	
	@EventHandler
	private void onPlayerMove(PlayerMoveEvent event){
		for(Town town : Town.towns){
			if(town.chunks.contains(event.getTo().getChunk())){
				if(!town.inTown.contains(event.getPlayer())){
					town.inTown.add(event.getPlayer());
					String message = "&2You entered &6%town_name%";
					message = message.replace("%town_name%", town.name);
					MessageManager.sendMessage(event.getPlayer(), message);
				}
			}else{
				if(town.inTown.contains(event.getPlayer())){
					town.inTown.remove(event.getPlayer());
					String message = "&cYou entered &6%town_name%";
					message = message.replace("%town_name%", town.name);
					MessageManager.sendMessage(event.getPlayer(), message);
				}
			}
		}
		
		
	}
	
	
}

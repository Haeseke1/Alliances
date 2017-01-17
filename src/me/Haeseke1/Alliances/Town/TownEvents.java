package me.Haeseke1.Alliances.Town;

import java.util.Iterator;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerMoveEvent;



public class TownEvents {
	
	
	
	@EventHandler
	private void onBlockBreak(BlockBreakEvent event){
		for(Town town : Town.towns){
			if(town.chunks.contains(event.getBlock().getLocation().getChunk())){
				if(!town.owner.getMembers().containsKey(event.getPlayer().getUniqueId())){
					event.setCancelled(true);
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
				}
			}else{
				if(town.inTown.contains(event.getPlayer())){
					town.inTown.remove(event.getPlayer());
				}
			}
		}
		
		
	}
	
	
}

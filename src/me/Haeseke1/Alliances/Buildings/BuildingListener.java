package me.Haeseke1.Alliances.Buildings;

import java.util.Iterator;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.Haeseke1.Alliances.Utils.MessageManager;

public class BuildingListener implements Listener {
	
	
	@EventHandler
	private void onBlockBreak(BlockBreakEvent event){
		for(Building building : Building.Allbuildings){
			if(building.chunk.equals(event.getBlock().getLocation().getChunk())){
				if(building.ymin <= event.getBlock().getY() && building.ymax >= event.getBlock().getY()){
					event.setCancelled(true);
					String message = "&cYou can't break buildings";
					MessageManager.sendMessage(event.getPlayer(), message);
				}
			}
		}
	}

	@EventHandler
	private void onBlockPlace(BlockPlaceEvent event){
		for(Building building : Building.Allbuildings){
			if(building.chunk.equals(event.getBlock().getLocation().getChunk())){
				if(building.ymin <= event.getBlock().getY() && building.ymax >= event.getBlock().getY()){
					event.setCancelled(true);
					String message = "&cYou can't place town blocks";
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
    		for(Building building : Building.Allbuildings){
    			if(building.chunk.equals(block.getLocation().getChunk())){
    				if(building.ymin <= block.getY() && building.ymax >= block.getY()){
    					event.setCancelled(true);
    				}
    			}
    		}
        }
    }
	
	

}

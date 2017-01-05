package me.Haeseke1.Alliances.Outpost;

import java.util.Iterator;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.Haeseke1.Alliances.Utils.MessageManager;

public class OutpostEvents implements Listener{
	
	@EventHandler
	private void onBlockBreak(BlockBreakEvent event){
		if(OutpostManager.checkBlock(event.getBlock().getLocation())){
			event.setCancelled(true);
			MessageManager.sendAlertMessage(event.getPlayer(), "You cannot break blocks from a outpost");
		}
	}

	@EventHandler
	private void onBlockPlace(BlockPlaceEvent event){
		if(OutpostManager.checkBlock(event.getBlock().getLocation())){
			event.setCancelled(true);
			MessageManager.sendAlertMessage(event.getPlayer(), "You cannot place blocks in a outpost");
		}
	}
	
	@EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> destroyed = event.blockList();
        Iterator<Block> it = destroyed.iterator();
        while (it.hasNext()) {
            Block block = it.next();
            if(OutpostManager.checkBlock(block.getLocation())){
            	it.remove();
            }
        }
    }
}

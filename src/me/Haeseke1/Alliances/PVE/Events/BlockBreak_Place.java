package me.Haeseke1.Alliances.PVE.Events;

import java.util.Iterator;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.Haeseke1.Alliances.Outpost.OutpostManager;
import me.Haeseke1.Alliances.PVE.Arena;
import me.Haeseke1.Alliances.PVE.GroupManager;
import me.Haeseke1.Alliances.PVE.PVE;

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
	
	@EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if(PVE.main == null){
        	return;
        }
        List<Block> destroyed = event.blockList();
        Iterator<Block> it = destroyed.iterator();
        while (it.hasNext()) {
            Block block = it.next();
            for(Arena arena : PVE.main.arenas)
			if(arena.world.equals(block.getWorld())){
				if(arena.xmin <= block.getX() && arena.xmax >= block.getX() &&
						arena.zmin <= block.getZ() && arena.zmax >= block.getZ()){
					it.remove();
				}
			}
        }
    }
}

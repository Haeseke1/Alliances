package me.Haeseke1.Alliances.Arena;

import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Haeseke1.Alliances.Outpost.OutpostManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class ArenaEvents implements Listener {
	
	@EventHandler
	private void onPlayerHit(EntityDamageEvent event){
		if(!(event.getEntity() instanceof Player)){
			return;
		}
		Player player = (Player) event.getEntity();
		for(Arena arena : Arena.arenas){
			if(arena.group1.contains(player)){
				if(arena.aliveGroup1.contains(player)){
					if(event.getDamage() >= player.getHealth()){
						event.setCancelled(true);
						player.setHealth(player.getMaxHealth());
						player.teleport(arena.waitRoom);
						arena.aliveGroup1.remove(player);
						arena.sendAllMessage(ChatColor.GOLD + player.getName() + " died there are " + arena.aliveGroup1.size() + " left of group " + arena.nameG1 + "!");
					}
				}else{
					event.setCancelled(true);
				}
			}
			if(arena.group2.contains(player)){
				if(arena.aliveGroup2.contains(player)){
					if(event.getDamage() >= player.getHealth()){
						event.setCancelled(true);
						player.setHealth(player.getMaxHealth());
						player.teleport(arena.waitRoom);
						arena.aliveGroup2.remove(player);
						arena.sendAllMessage(ChatColor.GOLD + player.getName() + " died there are " + arena.aliveGroup2.size() + " left of group " + arena.nameG2 + "!");
					}
				}else{
					event.setCancelled(true);
				}
			}
		}
	}
	
	
	@EventHandler
	private void onPlayerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		for(Arena arena : Arena.arenas){
			if(arena.group1.contains(player)){
				player.teleport(player.getLocation().getWorld().getSpawnLocation());
			}
			if(arena.group2.contains(player)){
				player.teleport(player.getLocation().getWorld().getSpawnLocation());
			}
		}
	}
	
	
	
	
	@EventHandler
	private void onBlockBreak(BlockBreakEvent event){
		if(ArenaManager.checkLocation(event.getBlock().getLocation())){
			event.setCancelled(true);
			MessageManager.sendAlertMessage(event.getPlayer(), "You cannot break blocks from a arena");
		}
	}

	@EventHandler
	private void onBlockPlace(BlockPlaceEvent event){
		if(ArenaManager.checkLocation(event.getBlock().getLocation())){
			event.setCancelled(true);
			MessageManager.sendAlertMessage(event.getPlayer(), "You cannot place blocks in a arena");
		}
	}
	
	@EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> destroyed = event.blockList();
        Iterator<Block> it = destroyed.iterator();
        while (it.hasNext()) {
            Block block = it.next();
            if(ArenaManager.checkLocation(block.getLocation())){
            	it.remove();
            }
        }
    }
	
	
	
}

package me.Haeseke1.Alliances.WorldGuard.Events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.Haeseke1.Alliances.WorldGuard.Regions.Region;

public class Blocks implements Listener{

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		Player player = event.getPlayer();
		if(player.hasPermission("worldguard.bypass")) return;
		if(Region.getRegionOfPlayer(player) == null) return;
		Region region = Region.getRegionOfPlayer(player);
		if(!region.getSetting("can-place-blocks")){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Player player = event.getPlayer();
		if(player.hasPermission("worldguard.bypass")) return;
		if(Region.getRegionOfPlayer(player) == null) return;
		Region region = Region.getRegionOfPlayer(player);
		if(!region.getSetting("can-break-blocks")){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(Region.getRegionOfPlayer(player) == null) return;
		Region region = Region.getRegionOfPlayer(player);
		if(player.hasPermission("worldguard.bypass")) return;
		if(event.getAction() == Action.LEFT_CLICK_BLOCK){
			if(event.getClickedBlock().getType() == Material.ITEM_FRAME){
				if(!region.getSetting("can-break-blocks")){
					event.setCancelled(true);
				}
			}
		}
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(event.getClickedBlock() == null) return;
		Block block = event.getClickedBlock();
		if(block.getType() == Material.CHEST || block.getType() == Material.ENDER_CHEST){
			if(!region.getSetting("can-right-click-chest")){
				event.setCancelled(true);
			}
		}
		if(block.getType() == Material.ANVIL){
			if(!region.getSetting("can-right-click-anvil")){
				event.setCancelled(true);
			}
		}
		if(block.getType() == Material.ENCHANTMENT_TABLE){
			if(!region.getSetting("can-right-click-enchant")){
				event.setCancelled(true);
			}
		}
		if(block.getType() == Material.WORKBENCH){
			if(!region.getSetting("can-right-click-crafting")){
				event.setCancelled(true);
			}
		}
		if(block.getType() == Material.FURNACE){
			if(!region.getSetting("can-right-click-furnace")){
				event.setCancelled(true);
			}
		}
	}
	
}

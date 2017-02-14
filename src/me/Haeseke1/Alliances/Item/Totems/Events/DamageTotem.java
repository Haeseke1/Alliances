package me.Haeseke1.Alliances.Item.Totems.Events;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.Haeseke1.Alliances.Item.Totems.HealingTotem;

public class DamageTotem implements Listener{

	@EventHandler
	public void onTotemDamage(PlayerInteractEvent event){
		if(!(event.getAction().equals(Action.LEFT_CLICK_BLOCK))) return;
		if(event.getClickedBlock() == null) return;
		Block block = event.getClickedBlock();
		if(isHealingTotem(block)){
			HealingTotem totem = this.getTotem(block);
			totem.damage();
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockDamage(BlockExplodeEvent event){
		event.setCancelled(true);
		Block block = event.getBlock();
		if(isHealingTotem(block)){
			HealingTotem totem = this.getTotem(block);
			totem.damage();
			event.setCancelled(true);
		}
	}
	
	public boolean isHealingTotem(Block block){
		for(HealingTotem totem: HealingTotem.htotems.values()){
			for(Block totemblock: totem.blocks){
				if(totemblock.equals(block)){
					return true;
				}
			}
		}
		return false;
	}
	
	public HealingTotem getTotem(Block block){
		for(HealingTotem totem: HealingTotem.htotems.values()){
			for(Block totemblock: totem.blocks){
				if(totemblock.equals(block)){
					return totem;
				}
			}
		}
		return null;
	}
}

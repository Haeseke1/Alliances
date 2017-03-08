package me.Haeseke1.Alliances.WorldGuard.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

import me.Haeseke1.Alliances.WorldGuard.Regions.Region;

public class Slaying implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event){
		if(!(event.getDamager() instanceof Player)) return;
		Player player = (Player) event.getDamager();
		if(player.hasPermission("worldguard.bypass")) return;
		if(event.getEntity() instanceof Player){
			Region region = Region.getRegionOfPlayer(player);
			if(!region.getSetting("can-harm-players")){
				event.setCancelled(true);
			}
		}else{
			Region region = Region.getRegionOfPlayer(player);
			if(region == null) return;
			if(!region.getSetting("can-harm-mobs")){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event){
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		Region region = Region.getRegionOfPlayer(player);
		if(region == null) return;
		if(event.getCause() == DamageCause.FALL){
			if(!region.getSetting("can-receive-fall-damage")){
				event.setCancelled(true);
			}
		}
		if(event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.FIRE_TICK || event.getCause() == DamageCause.LAVA){
			if(!region.getSetting("can-receive-fire-damage")){
				event.setCancelled(true);
			}
		}
		if(event.getCause() == DamageCause.DROWNING){
			if(!region.getSetting("can-drown")){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		if(Region.getRegionOfPlayer(event.getPlayer()) == null){
			return;
		}else{
			event.getPlayer().sendMessage(Region.getRegionOfPlayer(event.getPlayer()).displayname);
		}
	}
	
}

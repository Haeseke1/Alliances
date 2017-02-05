package me.Haeseke1.Alliances.PVE.Events;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import me.Haeseke1.Alliances.PVE.Arena;
import me.Haeseke1.Alliances.PVE.PVE;
import net.md_5.bungee.api.ChatColor;

public class EntityHit implements Listener {
	
	@EventHandler
	private void entityHit(EntityDamageEvent event){
		if(!(event.getEntity() instanceof LivingEntity)){
			return;
		}
		LivingEntity le = (LivingEntity) event.getEntity();
		if(le instanceof Player){
			Player player = (Player) le;
			if(PVE.main == null){
				return;
			}
			for(Arena arena : PVE.main.arenas){
				if(arena.group != null && arena.group.members.contains(player)){
					if(!arena.playerAlive.contains(player)){
						event.setCancelled(true);
						return;
					}
					if(event.getFinalDamage() >= player.getHealth()){
						player.setGameMode(GameMode.SPECTATOR);
						player.sendMessage(ChatColor.RED + "You died");
						player.setHealth(player.getMaxHealth());
						player.setFlying(false);
						player.setAllowFlight(false);
						arena.playerAlive.remove(player);
						if(arena.playerAlive.isEmpty()){
							arena.stopArena(false);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	private void entityDeath(EntityDeathEvent event) {
		if (!(event.getEntity() instanceof LivingEntity)) {
			return;
		}
		LivingEntity le = (LivingEntity) event.getEntity();
		if (PVE.main == null) {
			return;
		}
		for (Arena arena : PVE.main.arenas) {
			if (arena.alive.contains(le)) {
				arena.alive.remove(le);
				if(arena.alive.isEmpty()){
					arena.stopArena(true);
				}
			}
		}
		return;
	}

}

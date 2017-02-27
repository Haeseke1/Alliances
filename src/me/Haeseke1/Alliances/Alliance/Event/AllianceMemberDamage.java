package me.Haeseke1.Alliances.Alliance.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class AllianceMemberDamage implements Listener{

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event){
		if(event.getEntity() instanceof Player){
			if(event.getDamager() instanceof Player){
				Player player = (Player) event.getEntity();
				Player killer = (Player) event.getDamager();
				if(AllianceManager.getAlliance(player).getName().equalsIgnoreCase(AllianceManager.getAlliance(killer).getName())){
					MessageManager.sendMessage(killer, "&cYou can't hurt your own alliance members");
					event.setCancelled(true);
				}
			}
		}
	}
	
}

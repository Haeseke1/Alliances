package me.Haeseke1.Alliances.WorldGuard.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import me.Haeseke1.Alliances.WorldGuard.Regions.Region;

public class FoodChange implements Listener {

	@EventHandler
	public void onChange(FoodLevelChangeEvent event){
		Player player = (Player) event.getEntity();
		if(Region.getRegionOfPlayer(player) == null) return;
		Region region = Region.getRegionOfPlayer(player);
		if(!region.getSetting("can-lose-food")){
			event.setCancelled(true);
		}
	}
	
}

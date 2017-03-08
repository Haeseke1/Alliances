package me.Haeseke1.Alliances.WorldGuard.Events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.WorldGuard.Regions.Region;

public class Moving implements Listener{

	@EventHandler
	public void onMove(PlayerMoveEvent event){
		Region region;
		Player player = event.getPlayer();
		Block from = event.getFrom().getBlock();
		Block to = event.getTo().getBlock();
		if(Region.getRegionOfLocation(from.getLocation()) == null && Region.getRegionOfLocation(to.getLocation()) == null) return;
		if(Region.getRegionOfLocation(from.getLocation()) != null && Region.getRegionOfLocation(to.getLocation()) == null){
			region = Region.getRegionOfLocation(from.getLocation());
			if(!region.getSetting("show-message-on-leave")) return;
			MessageManager.sendMessage(player, "&4You've left the " + region.displayname + " &4region");
			return;
		}
		if(Region.getRegionOfLocation(from.getLocation()) == null && Region.getRegionOfLocation(to.getLocation()) != null){
			region = Region.getRegionOfLocation(to.getLocation());
			if(!region.getSetting("show-message-on-enter")) return;
			MessageManager.sendMessage(player, "&2You've entered the " + region.displayname + " &2region");
			return;
		}
	}
	
}

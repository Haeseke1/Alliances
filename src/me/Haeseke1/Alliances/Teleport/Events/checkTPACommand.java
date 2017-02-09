package me.Haeseke1.Alliances.Teleport.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.PlayerManager;

public class checkTPACommand implements Listener{

	@EventHandler
	public void checkCommand(PlayerCommandPreprocessEvent event){
        String[] args = event.getMessage().split(" ");
		if(args[0].equalsIgnoreCase("/tpa")){
            if(!PlayerManager.isPlayerOnline(args[1])){
            	MessageManager.sendMessage(event.getPlayer(), "&cThis player is not online!");
            	event.setCancelled(true);
            	return;
            }
			Player target = PlayerManager.getPlayer(args[1]);
            if(AllianceManager.getAlliance(event.getPlayer()).getName() != AllianceManager.getAlliance(target).getName()){
            	MessageManager.sendMessage(event.getPlayer(), "&cYou can only send a tp request to alliance members");
            	event.setCancelled(true);
            }
		}
	}
	
}

package me.Haeseke1.Alliances.PVE.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.Haeseke1.Alliances.PVE.ArenaManager;
import me.Haeseke1.Alliances.PVE.Group;
import me.Haeseke1.Alliances.PVE.GroupManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class PlayerCommand implements Listener {

	
	@EventHandler
	private void onPlayerCommand(PlayerCommandPreprocessEvent event){
		if(!GroupManager.hasGroup(event.getPlayer())){
			return;
		}
		Group group = GroupManager.getGroup(event.getPlayer());
		Player player = event.getPlayer();
		if(ArenaManager.hasArena(group)){
			event.setCancelled(true);
			MessageManager.sendMessage(player, ChatColor.RED + "You cannot use this command in the PVE arena!");
		}else{
			if(!event.getMessage().equalsIgnoreCase("/pve settings") && 
					!event.getMessage().equalsIgnoreCase("/pve ready") && 
					!event.getMessage().equalsIgnoreCase("/pve leave")){
				event.setCancelled(true);
				MessageManager.sendMessage(player, ChatColor.RED + "You cannot use this command in the PVE lobby!");
			}
		}
		
	}
}

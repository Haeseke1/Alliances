package me.Haeseke1.Alliances.Mounts.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Haeseke1.Alliances.Mounts.Mount;
import me.Haeseke1.Alliances.Mounts.Commands.MountCommand;

public class Leave implements Listener{

	@EventHandler
	public void leave(PlayerQuitEvent event){
		for(Mount mount: MountCommand.mounts.values()){
			if(mount.owner.equals(event.getPlayer())){
				if(mount.horse.getPassenger() != null){
					mount.horse.setPassenger(null);
					mount.removeMount();
				}
			}
		}
	}
	
}

package me.Haeseke1.Alliances.Mounts.Scheduler;

import org.bukkit.Sound;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Mounts.Mount;
import me.Haeseke1.Alliances.Mounts.Commands.MountCommand;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;
import net.md_5.bungee.api.ChatColor;

public class Updater implements Runnable{
	
	@Override
	public void run() {
	   for(Mount mount: MountCommand.mounts.values()){
		   Player player = mount.owner;
		   Horse horse = mount.horse;
		   if(horse.getLocation().distance(player.getLocation()) > 20){
			   mount.removeMount();
		       MessageManager.sendMessage(player, ChatColor.RED + "Your mount died");
		       SoundManager.playSoundToPlayer(Sound.NOTE_BASS_DRUM, player);
		   }
	   }
	}
	
}

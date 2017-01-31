package me.Haeseke1.Alliances.Mounts.Events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Mounts.Mount;
import me.Haeseke1.Alliances.Mounts.Commands.MountCommand;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;
import net.md_5.bungee.api.ChatColor;

public class Death implements Listener{

	@EventHandler
	public void onEntity(EntityDeathEvent event){
	    if(!(event.getEntity().getType().equals(EntityType.HORSE))){ return;}
	    Horse horse = (Horse) event.getEntity();
	    for(Mount mount: MountCommand.mounts.values()){
	    	if(mount.horse == horse){
	    	Player player = mount.owner;
	    	MessageManager.sendMessage(player, ChatColor.RED + "Your mount died");
	    	SoundManager.playSoundToPlayer(Sound.NOTE_SNARE_DRUM, player);
	    	MountCommand.mounts.remove(player);
	    	event.getDrops().clear();
	    	event.getDrops().add(new ItemStack(Material.AIR));
	    	event.setDroppedExp(0);
	    	}
	    }
  	}

}

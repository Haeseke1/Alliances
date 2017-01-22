package me.Haeseke1.Alliances.Utils;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundManager {
	
	public static void playSound(Sound sound, Location loc){
		loc.getWorld().playSound(loc,sound , 1, 1);
	}
	
	public static void playSoundToPlayer(Sound sound, Player player){
		player.playSound(player.getLocation(), sound, 1, 1);
	}
}

package me.Haeseke1.Alliances.Utils;

import org.bukkit.Location;
import org.bukkit.Sound;

public class SoundManager {
	
	public static void playSound(Sound sound, Location loc){
		loc.getWorld().playSound(loc,sound , 1, 1);
	}
	
}

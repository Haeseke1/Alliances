package me.Haeseke1.Alliances.Utils;

import org.bukkit.Location;

public class LocationManager {
	
	
	
	public static boolean checkCoordinates(Location loc1, Location loc2){
		if(loc1.getX() == loc2.getX() && loc1.getY() == loc2.getY() && loc1.getZ() == loc2.getZ()){
			return true;
		}
		return false;
	}

}

package me.Haeseke1.Alliances.Utils;

import net.md_5.bungee.api.ChatColor;

public class TimeManager {

	public static String secondsToHoursMinutesSeconds(double timeInSeconds,String color1, String color2){
		int hours = (int) timeInSeconds / 3600;
        double hoursdouble = (timeInSeconds / 3600) - hours;
        int minutes = (int) (hoursdouble * 3600 / 60);
        double minutesdouble = minutes - (hoursdouble * 3600 / 60);
        int seconds = (int) (-1 * (minutesdouble * 3600 / 60));
	    return ChatColor.translateAlternateColorCodes('&', color1 + (int) hours + color2 +"h " + color1 + minutes + color2 + "m " + color1 + seconds + color2 +"s");
	}
	
}

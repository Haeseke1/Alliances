package me.Haeseke1.Alliances.Arena;

import org.bukkit.configuration.file.FileConfiguration;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Main.Main;

public class TeamManager {

	private static FileConfiguration arenaConfig = Main.arenaConfig;
	
	public static String[] teamNumbers = {"1","2"};
	public static boolean teamExists(Alliance al, String arenaname){
		for(String number: teamNumbers){
        if(al.getName().equalsIgnoreCase(arenaConfig.getString("Arenas." + arenaname.toLowerCase() + ".spawns.team" + number + ".alliance"))){
        	return true;
           }
		}
		return false;
	}
	
	public static int getTeamNumber(Alliance al, String arenaname){
		for(String number: teamNumbers){
        if(al.getName().equalsIgnoreCase(arenaConfig.getString("Arenas." + arenaname.toLowerCase() + ".spawns.team" + number + ".alliance"))){
        	return Integer.parseInt(number);
           }
		}
		return 0;
	}
	
}

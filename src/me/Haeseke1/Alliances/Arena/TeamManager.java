package me.Haeseke1.Alliances.Arena;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import net.md_5.bungee.api.ChatColor;

public class TeamManager {

	private static FileConfiguration arenaConfig = Main.arenaConfig;
	
	public static String[] teamNumbers = {"1","2"};
	public static boolean teamExists(Alliance al, String arenaname){
		for(String number: teamNumbers){
        if(ChatColor.stripColor(al.getName()).equalsIgnoreCase(arenaConfig.getString("Arenas." + arenaname.toLowerCase() + ".spawns.team" + number + ".alliance"))){
        	return true;
           }
		}
		return false;
	}
	
	public static int getTeamNumber(Alliance al, String arenaname){
		for(String number: teamNumbers){
        if(ChatColor.stripColor(al.getName()).equalsIgnoreCase(arenaConfig.getString("Arenas." + arenaname.toLowerCase() + ".spawns.team" + number + ".alliance"))){
        	return Integer.parseInt(number);
           }
		}
		return 0;
	}
	
	public static boolean teamIsFree(int number, String arenaName) throws EmptyStringException{
		if(ConfigManager.getStringFromConfig(arenaConfig, "Arenas." + arenaName.toLowerCase() + ".spawns.team" + number + ".alliance").equalsIgnoreCase("")){
			return true;
		}
		return false;
	}
	
	public static boolean checkTeams(String arenaname) throws EmptyStringException{
		Arena arena = ArenaManager.getArenaByName(arenaname);
		Alliance team1 = AllianceManager.getAlliance(ConfigManager.getStringFromConfig(arenaConfig, "Arenas." + arenaname.toLowerCase() + ".spawns.team1.alliance"));
		Alliance team2 = AllianceManager.getAlliance(ConfigManager.getStringFromConfig(arenaConfig, "Arenas." + arenaname.toLowerCase() + ".spawns.team2.alliance"));
        int count1 = 0;
        int count2 = 0;
        for(UUID playerUUID: arena.getPlayersInArena().keySet()){
        	Player player = Bukkit.getPlayer(playerUUID);
        	if(AllianceManager.getAlliance(player) == team1){
        		count1 ++;
        	}
        	if(AllianceManager.getAlliance(player) == team2){
        		count2 ++;
        	}
        }
        if(count1 == 0 || count2 == 0){
        	return false;
        }
        return true;
	}
}

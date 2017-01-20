package me.Haeseke1.Alliances.Town;

import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Exp.Exp;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;
import sun.rmi.transport.proxy.CGIHandler;

public class TownManager {
	
	private static FileConfiguration allianceConfig = Main.alliancesConfig;
	
	public static void createTown(Player player, Alliance alli, Chunk chunk, String name){
		if(!alli.getOwner().equals(player.getUniqueId())){
			String message = MessageManager.getMessage("Town_Already_Exist");
			message = message.replace("%town_name%", name);
			MessageManager.sendMessage(player, message);
			return;
		}
		if(Exp.getLevel(alli.getExp()) < 3 || alli.getTowns().size() > 0){
			String message = MessageManager.getMessage("Town_Cannot_Create_Town");
			message = message.replace("%town_name%", name);
			MessageManager.sendMessage(player, message);
			return;
		}
		if(isClaimed(chunk)){
			String message = MessageManager.getMessage("Town_Already_Claimed");
			message = message.replace("%town_name%", name);
			MessageManager.sendMessage(player, message);
			return;
		}
		alli.addTown(new Town(name, chunk, alli));
	}
	
	public static boolean isClaimed(Chunk chunk){
		for(Town town : Town.towns){
			if(town.chunks.contains(chunk)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNextTo(Chunk chunk, Town town){
		for(Chunk cChunk : town.chunks){
			if((cChunk.getX() - 1 == chunk.getX() || cChunk.getX() + 1 == chunk.getX()) && cChunk.getZ() == chunk.getZ()){
				return true;
			}
			if((cChunk.getZ() - 1 == chunk.getZ() || cChunk.getZ() + 1 == chunk.getZ()) && cChunk.getX() == chunk.getX()){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isTown(String name){
		for(Town town : Town.towns){
			if(town.name.equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
	
	public static Town getTown(String name){
		for(Town town : Town.towns){
			if(town.name.equalsIgnoreCase(name)){
				return town;
			}
		}
		return null;
	}

    public static void registerTowns(){
    	for(Alliance al: Main.alliances){
    		int count = 0;
    		for(Town town: al.getTowns()){
    			int chunkCount = 0;
    			for(Chunk chunk: town.chunks){
    				allianceConfig.set(al.getName() + ".towns." + count + ".chunks." + chunkCount + ".x", chunk.getX());
    				allianceConfig.set(al.getName() + ".towns." + count + ".chunks." + chunkCount + ".z", chunk.getZ());
    				allianceConfig.set(al.getName() + ".towns." + count + ".chunks." + chunkCount + ".world", chunk.getWorld().getName());
    				chunkCount ++;
    			}
    			count ++;
    		}
    	}
    }
    
    public static void loadTowns(){
    	for(String alliancename: allianceConfig.getConfigurationSection("").getKeys(false)){
    		Alliance al = AllianceManager.getAlliance(alliancename);
    	    
    	}
    }
}

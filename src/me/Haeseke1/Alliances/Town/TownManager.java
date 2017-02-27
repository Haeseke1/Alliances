package me.Haeseke1.Alliances.Town;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Buildings.Building;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class TownManager {
	


	public static int Town_Create_Payment;
	public static int Town_Claim_Payment;
	public static int Claim_Limit;
	

	
	public static void createTown(Player player, Alliance alli, Chunk chunk, String name){
		if(isUnclaimable(player.getLocation().getChunk())){
			MessageManager.sendMessage(player, ChatColor.RED + "This land is unclaimable!");
			return;
		}
		if(alli.getCoins() < Town_Create_Payment){
			String message = "&cYou don't have enough money";
			message = message.replace("%payment%", "" +  Town_Create_Payment);
			MessageManager.sendMessage(player, message);
			return;
		}
		if(!alli.getOwner().equals(player.getUniqueId())){
			String message = "&cYou are not the owner of this alliance!";
			message = message.replace("%town_name%", name);
			MessageManager.sendMessage(player, message);
			return;
		}
		if(isClaimed(chunk)){
			String message = "&cThis town is already claimed";
			message = message.replace("%town_name%", name);
			MessageManager.sendMessage(player, message);
			return;
		}
		MessageManager.sendMessage(player, ChatColor.GREEN + "You created town " + name);
		Coins.removeAllianceCoins(player, Town_Create_Payment);
		alli.addTown(new Town(name, chunk, alli));
	}
	
	public static boolean claimLand(Player player, Town town){
		if(isUnclaimable(player.getLocation().getChunk())){
			MessageManager.sendMessage(player, ChatColor.RED + "This land is unclaimable!");
			return false;
		}
		if(town.owner.getCoins() < Town_Claim_Payment){
			String message = "&cYou don't have enough money";
			message = message.replace("%payment%", "" +  Town_Claim_Payment);
			MessageManager.sendMessage(player, message);
			return false;
		}
		if(TownManager.isClaimed(player.getLocation().getChunk())){
			String message = "&cThis town is already claimed";
			MessageManager.sendMessage(player, message);
			return false;
		}
		if(!TownManager.isNextTo(player.getLocation().getChunk(), town)){
			String message = "&cThis claim isn't connected to the other ones";
			MessageManager.sendMessage(player, message);
			return false;
		}
		if(town.chunks.size() >= Claim_Limit){
			String message = "&cThis town reached the claim limit";
			MessageManager.sendMessage(player, message);
			return false;
		}
		Coins.removeAllianceCoins(player, Town_Claim_Payment);
		town.addChunck(player.getLocation().getChunk());
		MessageManager.sendMessage(player, ChatColor.GREEN + "You claimed land for your town " + town.name);
		return true;
	}
	
	public static boolean isClaimed(Chunk chunk){
		for(Town town : Town.towns){
			if(town.hasChunk(chunk)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isUnclaimable(Chunk chunk){
		if(Town.unclaimable.contains(chunk)){
			return true;
		}
		return false;
	}
	
	public static int addUnclaimable(Location loc1, Location loc2){
		int xmin = 0;
		int zmin = 0;
		int xmax = 0;
		int zmax = 0;
		if(loc1.getBlockX() > loc2.getBlockX()){
			xmin = loc2.getBlockX();
			xmax = loc1.getBlockX();
		}else{
			xmin = loc1.getBlockX();
			xmax = loc2.getBlockX();
		}
		if(loc1.getBlockZ() > loc2.getBlockZ()){
			zmin = loc2.getBlockZ();
			zmax = loc1.getBlockZ();
		}else{
			zmin = loc1.getBlockZ();
			zmax = loc2.getBlockZ();
		}
		int amount = 0;
		for(int x = xmin; x < xmax; x++){
			for(int z = zmin; z < zmax; z++){
				Chunk chunk = loc1.getWorld().getChunkAt(new Location(loc1.getWorld(), x, loc1.getY(), z));
				if(!isUnclaimable(chunk)){
					Town.unclaimable.add(chunk);
					amount ++;
				}
			}
		}
		return amount;
	}
	
	public static boolean hasBuilding(Building b){
		for(Town town : Town.towns){
			if(town.hasBuilding(b)){
				return true;
			}
		}
		return false;
	}
	
	
	public static Town getTown(Building b){
		for(Town town : Town.towns){
			if(town.hasBuilding(b)){
				return town;
			}
		}
		return null;
	}
	
	public static Town getTown(Chunk chunk){
		for(Town town : Town.towns){
			if(town.hasChunk(chunk)){
				return town;
			}
		}
		return null;
		
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
	
	public static boolean isNextTo(Chunk chunk, Chunk cChunk){
		if((cChunk.getX() - 1 == chunk.getX() || cChunk.getX() + 1 == chunk.getX()) && cChunk.getZ() == chunk.getZ()){
			return true;
		}
		if((cChunk.getZ() - 1 == chunk.getZ() || cChunk.getZ() + 1 == chunk.getZ()) && cChunk.getX() == chunk.getX()){
			return true;
		}
		return false;
	}
	
	public static boolean isTown(String name){
		for(Town town : Town.towns){
			if(ChatColor.stripColor(town.name).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name)))){
				return true;
			}
		}
		return false;
	}
	
	public static Town getTown(String name){
		for(Town town : Town.towns){
			if(ChatColor.stripColor(town.name).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name)))){
				return town;
			}
		}
		return null;
	}

    public static void saveTowns(){
    	FileConfiguration f = Main.alliancesConfig;
    	for(Alliance al: Main.alliances){
    		for(Town town: al.getTowns()){
    			int chunkCount = 0;
    			for(Chunk chunk: town.chunks){
    				f.set(al.getName() + ".towns." + town.name + ".chunks." + chunkCount + ".x", chunk.getX());
    				f.set(al.getName() + ".towns." + town.name + ".chunks." + chunkCount + ".z", chunk.getZ());
    				f.set(al.getName() + ".towns." + town.name + ".chunks." + chunkCount + ".world", chunk.getWorld().getName());
    				chunkCount ++;
    			}
    		}
    	}
    }
    
	public static void registerTowns() {
		Town.towns.clear();
		FileConfiguration f = Main.alliancesConfig;
		for (String alliancename : f.getConfigurationSection("").getKeys(false)) {
			Alliance al = AllianceManager.getAlliance(alliancename);
			if (f.getConfigurationSection(alliancename + ".towns") != null) {
				for (String townName : f.getConfigurationSection(alliancename + ".towns").getKeys(false)) {
					List<Chunk> chunkList = new ArrayList<Chunk>();
					for (String chunk : f.getConfigurationSection(alliancename + ".towns." + townName + ".chunks").getKeys(false)) {
						int chunkNumber = Integer.parseInt(chunk);
						int x = f.getInt(alliancename + ".towns." + townName + ".chunks." + chunkNumber + ".x");
						int z = f.getInt(alliancename + ".towns." + townName + ".chunks." + chunkNumber + ".z");
						String world = f.getString(alliancename + ".towns." + townName + ".chunks." + chunkNumber + ".world");
						chunkList.add(Bukkit.getWorld(world).getChunkAt(x, z));
					}
					new Town(townName, chunkList, al);
				}
				MessageManager.sendAlertMessage("Towns have been registered");
			}
		}
	}
}

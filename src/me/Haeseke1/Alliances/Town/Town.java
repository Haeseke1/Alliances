package me.Haeseke1.Alliances.Town;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Buildings.Building;
import me.Haeseke1.Alliances.Buildings.BuildingManager;

public class Town {
	
	public static List<Town> towns = new ArrayList<Town>();
	
	public static List<Chunk> unclaimable = new ArrayList<Chunk>();
	
	public List<Building> buildings = new ArrayList<Building>();
	
	public List<Chunk> chunks = new ArrayList<Chunk>();
	public Alliance owner;
	
	public List<Player> inTown = new ArrayList<Player>();
	
	public String name;
	public String nameWithColorCodes;
	
	
	public Town(String name, Chunk chunk, Alliance alli) {
		if(BuildingManager.hasBuilding(chunk)){
			addBuilding(BuildingManager.getBuilding(chunk));
		}
		this.chunks.add(chunk);
		this.owner = alli;
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.nameWithColorCodes = name;
		alli.addTown(this);
		towns.add(this);
	}
	
	public Town(String name, List<Chunk> chunk, Alliance alli) {
		this.chunks = chunk;
		this.owner = alli;
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.nameWithColorCodes = name;
		alli.addTown(this);
		towns.add(this);
	}
	
	public void addChunck(Chunk chunk) {
		if(BuildingManager.hasBuilding(chunk)){
			addBuilding(BuildingManager.getBuilding(chunk));
		}
		chunks.add(chunk);
	}
	
	public boolean hasChunk(Chunk chunk){
		if(chunks.contains(chunk)){
			return true;
		}
		return false;
	}
	
	public void addBuilding(Building b){
		buildings.add(b);
	}
	
	public boolean hasBuilding(Building b){
		if(buildings.contains(b)){
			return true;
		}
		return false;
	}
	
	
	public boolean canBuild(Chunk chunk){
		for(Building b : buildings){
			if(b.chunk.equals(chunk)){
				return false;
			}
		}
		return true;
	}
	
	
}

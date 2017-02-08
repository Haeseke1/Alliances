package me.Haeseke1.Alliances.Buildings;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;

public class Building {
	
	public static List<Building> buildings = new ArrayList<Building>();
	
	public Location mainBlock;
	public int y;
	public Chunk chunk;
	public BuildingType type;
	
	
	public Building(Location mainBlock, Chunk chunk, int y, BuildingType type) {
		this.y = y;
		this.mainBlock = mainBlock;
		this.chunk = chunk;
		this.type = type;
		buildings.add(this);
	}
	
	
	
	
	
	
	
	
	
	

}

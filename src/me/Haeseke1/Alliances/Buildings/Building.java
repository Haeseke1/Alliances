package me.Haeseke1.Alliances.Buildings;

import org.bukkit.Chunk;
import org.bukkit.Location;

public class Building {
	
	public Location mainBlock;
	public int y;
	public Chunk chunk;
	public BuildingType type;
	
	
	public Building(Location mainBlock, Chunk chunk, int y, BuildingType type) {
		this.y = y;
		this.mainBlock = mainBlock;
		this.y = y;
		this.type = type;
	}
	
	
	
	
	
	
	
	
	
	

}

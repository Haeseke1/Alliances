package me.Haeseke1.Alliances.Buildings;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;

import me.Haeseke1.Alliances.Town.Town;

public class Building {
	
	public static List<Building> buildings = new ArrayList<Building>();
	public static List<Building> Allbuildings = new ArrayList<Building>();
	
	public Location mainBlock;
	public int ymin;
	public int ymax;
	public Chunk chunk;
	public BuildingType type;
	
	
	
	public Building(Location mainBlock, Chunk chunk, int ymin, int ymax, BuildingType type, boolean isChosen ) {
		this.ymin = ymin;
		this.ymax = ymax;
		this.mainBlock = mainBlock;
		this.chunk = chunk;
		this.type = type;
		for(Town t : Town.towns){
			if(t.hasChunk(chunk)){
				t.addBuilding(this);
			}
		}
		Allbuildings.add(this);
		if(!isChosen){
			buildings.add(this);
		}
	}
	
	
	
	
	
	
	
	

}

package me.Haeseke1.Alliances.Town;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;

public class Town {
	
	public static List<Town> towns = new ArrayList<Town>();
	
	
	public List<Chunk> chunks = new ArrayList<Chunk>();
	public Alliance owner;
	
	public List<Player> inTown = new ArrayList<Player>();
	
	public String name;
	
	
	public Town(String name, Chunk chunk, Alliance alli) {
		this.chunks.add(chunk);
		this.owner = alli;
		this.name = name;
		towns.add(this);
	}
	
	public void addChunck(Chunk chunk) {
		chunks.add(chunk);
	}
	
	
}

package me.Haeseke1.Alliances.PVE;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

public class Arena {
	
	
	public List<Location> mobSpawns = new ArrayList<Location>();
	public List<Location> playerSpawns = new ArrayList<Location>();
	
	public int xmin;
	public int xmax;
	public int zmin;
	public int zmax;
	public World world;
	
	public boolean playing = false;
	public ArenaStatus as = ArenaStatus.MAINTENANCE;
	
	
	public Arena(Location loc1, Location loc2) {
		world = loc1.getWorld();
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
	}
	
	public Arena(World world, int xmin, int xmax, int zmin, int zmax, List<Location> playerSpawns, List<Location> mobSpawns) {
		this.world = world;
		this.xmin = xmin;
		this.xmax = xmax;
		this.zmin = zmin;
		this.zmax = zmax;
		this.mobSpawns = mobSpawns;
		this.playerSpawns = playerSpawns;
		checkStatus();
	}
	
	
	
	public void checkStatus(){
		if(playerSpawns.size() == 4 && mobSpawns.size() > 4){
			as = ArenaStatus.READY;
		}
	}
	
	
	
	
	
	
}

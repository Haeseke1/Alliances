package me.Haeseke1.Alliances.PVE;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class PVE {
	
	public static PVE main;
	
	public List<Arena> arenas = new ArrayList<Arena>();
	
	public Location lobby;
	
	public List<Group> queue = new ArrayList<Group>();
	
	public PVE(Location lobby) {
		this.lobby = lobby;
		main = this;
	}
	
	public void addQueue(Group group){
		queue.add(group);
	}
	
	public boolean removeQueue(Group group){
		if(!inQueue(group)){
			return false;
		}
		queue.remove(group);
		return true;
	}
	
	public boolean inQueue(Group group){
		return queue.contains(group);
	}
	
}

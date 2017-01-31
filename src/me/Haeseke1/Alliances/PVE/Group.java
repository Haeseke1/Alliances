package me.Haeseke1.Alliances.PVE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Group {
	
	public static List<Group> groups = new ArrayList<Group>();
	
	public List<Player> members = new ArrayList<Player>();
	public Player owner;
	public Settings settings;
	
	public HashMap<Player, Location> memberLocations = new HashMap<>();
	
	public Group(List<Player> members, Player owner) {
		this.members = members;
		this.owner = owner;
		this.settings = new Settings();
		for(Player player : members){
			memberLocations.put(player, player.getLocation());
			player.teleport(PVE.main.lobby);
		}
		groups.add(this);
	}
	
	
	public void disband(){
		groups.remove(this);
		PVE.main.removeQueue(this);
		for(Player player : members){
			player.teleport(memberLocations.get(player));
		}
	}
	
	
	
}

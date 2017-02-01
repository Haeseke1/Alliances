package me.Haeseke1.Alliances.PVE;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

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
		if(group.settings.mobCount() >= 5 + 5 * group.members.size() && group.settings.mobCount() <= 20 + 5 * group.members.size()){
			queue.add(group);
		}
		group.sendPlayersMessage(ChatColor.RED + "Your total enemy's must be between " + 5 + 5 * group.members.size() + " and " + 20 + 5 * group.members.size());
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
	
	public boolean arenaAlreadyExist(String name){
		for(Arena arena : arenas){
			if(arena.name.equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
	
	public Arena getArena(String name){
		for(Arena arena : arenas){
			if(arena.name.equalsIgnoreCase(name)){
				return arena;
			}
		}
		return null;
	}
	
}

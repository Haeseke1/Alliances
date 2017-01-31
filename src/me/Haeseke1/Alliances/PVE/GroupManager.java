package me.Haeseke1.Alliances.PVE;

import org.bukkit.entity.Player;

public class GroupManager {
	
	
	public static boolean hasGroup(Player player){
		for(Group group : Group.groups){
			if(group.members.contains(player)){
				return true;
			}
		}
		return false;
	}
	
	public static Group getGroup(Player player){
		for(Group group : Group.groups){
			if(group.members.contains(player)){
				return group;
			}
		}
		return null;
	}
	
	
	
}

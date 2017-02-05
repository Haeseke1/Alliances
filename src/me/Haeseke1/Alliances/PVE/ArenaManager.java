package me.Haeseke1.Alliances.PVE;

public class ArenaManager {
	
	public static Arena getArena(Group group){
		for(Arena arena : PVE.main.arenas){
			if(arena.group.equals(group)){
				return arena;
			}
		}
		return null;
	}
	
	public static boolean hasArena(Group group){
		return getArena(group) != null;
	}
	
}

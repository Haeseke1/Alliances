package me.Haeseke1.Alliances.Arena;

import org.bukkit.ChatColor;

public class ArenaTimer implements Runnable{

	@Override
	public void run() {
		for(Arena arena : Arena.arenas){
			if(arena.isStarting){
				if(arena.time == 0){
					arena.isStarting = false;
					arena.time = 10;
				}
				arena.sendAllMessage(ChatColor.BOLD + "" + ChatColor.DARK_RED + arena.time);
			}
			if(arena.isFighting){
				if(arena.isFighting){
					if(arena.aliveGroup1.isEmpty()){
						arena.endGame();
					}
					if(arena.aliveGroup2.isEmpty()){
						arena.endGame();
					}
				}
			}
		}
		for(Arena_Sign as : Arena_Sign.arena_signs){
			as.update();
		}
	}

}

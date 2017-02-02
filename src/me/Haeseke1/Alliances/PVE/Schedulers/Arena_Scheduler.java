package me.Haeseke1.Alliances.PVE.Schedulers;

import me.Haeseke1.Alliances.PVE.Arena;
import me.Haeseke1.Alliances.PVE.Mob_Manager;
import me.Haeseke1.Alliances.PVE.PVE;
import net.md_5.bungee.api.ChatColor;

public class Arena_Scheduler implements Runnable{

	@Override
	public void run() {
		if(PVE.main == null){
			return;
		}
		for(Arena arena : PVE.main.arenas){
			if(arena.startCountdown){
				if(arena.countDown <= 0){
					arena.countDown = 10;
					arena.startCountdown = false;
					arena.fight();
				}
				arena.group.sendPlayersMessage(ChatColor.AQUA + "The fight will start in " + ChatColor.GOLD + arena.countDown + ChatColor.AQUA + " seconds");
				arena.countDown -= 1;
			}
		}
		Mob_Manager.control_Mobs();
	}
	
	

}

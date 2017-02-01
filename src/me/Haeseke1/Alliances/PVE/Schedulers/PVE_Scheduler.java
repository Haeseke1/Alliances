package me.Haeseke1.Alliances.PVE.Schedulers;

import me.Haeseke1.Alliances.PVE.Arena;
import me.Haeseke1.Alliances.PVE.ArenaStatus;
import me.Haeseke1.Alliances.PVE.PVE;

public class PVE_Scheduler implements Runnable{

	@Override
	public void run() {
		if(PVE.main == null){
			return;
		}
		PVE pve = PVE.main;
		if(pve.queue.isEmpty()){
			return;
		}
		for(Arena arena : pve.arenas){
			if(arena.as.equals(ArenaStatus.READY) && !arena.playing){
				arena.startArena(pve.queue.get(0));
				pve.queue.remove(0);
				return;
			}
		}
	}

}

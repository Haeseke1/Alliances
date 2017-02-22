package me.Haeseke1.Alliances.Alliance;

import me.Haeseke1.Alliances.Alliance.Type.Caith_Sith;
import me.Haeseke1.Alliances.Alliance.Type.Gnome;
import me.Haeseke1.Alliances.Alliance.Type.Imp;
import me.Haeseke1.Alliances.Alliance.Type.Leprechaun;
import me.Haeseke1.Alliances.Alliance.Type.Sylph;
import me.Haeseke1.Alliances.Alliance.Type.Undine;

public class Bonus_Timer implements Runnable {
	int timer = 0;
	
	@Override
	public void run() {
		Caith_Sith.update();
		Gnome.update();
		Imp.update();
		Sylph.update();
		Leprechaun.update();
		timer++;
		if(timer == 3){
			Undine.update();
			timer = 0;
		}
		
	}
	
}


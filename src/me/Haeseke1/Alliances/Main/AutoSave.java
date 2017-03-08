package me.Haeseke1.Alliances.Main;

import java.util.Calendar;

import me.Haeseke1.Alliances.Vote.VotePlayer;

public class AutoSave implements Runnable{

	@Override
	public void run() {
		VotePlayer.month_int = Calendar.MONTH;
		VotePlayer.week_int = Calendar.WEEK_OF_YEAR;
		Main.saveAllCustomConfigs();
	}

}

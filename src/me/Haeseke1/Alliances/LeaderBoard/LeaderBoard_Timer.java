package me.Haeseke1.Alliances.LeaderBoard;

import me.Haeseke1.Alliances.LeaderBoard.Type.Alliance_LeaderBoard;
import me.Haeseke1.Alliances.LeaderBoard.Type.Player_LeaderBoard;

public class LeaderBoard_Timer implements Runnable{

	@Override
	public void run() {
		Alliance_LeaderBoard.alli_Leaderboard.update();
		Player_LeaderBoard.player_LeaderBoard.update();
	}

}

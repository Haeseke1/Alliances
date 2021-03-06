package me.Haeseke1.Alliances.LeaderBoard;

import me.Haeseke1.Alliances.LeaderBoard.Type.All_Time_Vote_LeaderBoard;
import me.Haeseke1.Alliances.LeaderBoard.Type.Alliance_LeaderBoard;
import me.Haeseke1.Alliances.LeaderBoard.Type.Monthly_Vote_LeaderBoard;
import me.Haeseke1.Alliances.LeaderBoard.Type.Player_LeaderBoard;
import me.Haeseke1.Alliances.LeaderBoard.Type.Weekly_Vote_LeaderBoard;

public class LeaderBoard_Timer implements Runnable{

	@Override
	public void run() {
		Alliance_LeaderBoard.alli_Leaderboard.update();
		Player_LeaderBoard.player_LeaderBoard.update();
		All_Time_Vote_LeaderBoard.all_Time_Vote_LeaderBoard.update();
		Monthly_Vote_LeaderBoard.monthly_Vote_LeaderBoard.update();
		Weekly_Vote_LeaderBoard.weekly_Vote_LeaderBoard.update();
	}

}

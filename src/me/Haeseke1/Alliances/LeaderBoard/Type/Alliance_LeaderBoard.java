package me.Haeseke1.Alliances.LeaderBoard.Type;

import java.util.List;

import me.Haeseke1.Alliances.LeaderBoard.Head_Board;
import me.Haeseke1.Alliances.LeaderBoard.LeaderBoard;
import me.Haeseke1.Alliances.LeaderBoard.Place;
import net.md_5.bungee.api.ChatColor;

public class Alliance_LeaderBoard extends LeaderBoard{
	
	public static Alliance_LeaderBoard alli_Leaderboard;
	
	public Alliance_LeaderBoard(List<Place> places) {
		super(places);
		alli_Leaderboard = this;
	}
	
	public void addPlace(int location, Head_Board hb){
		places.add(new Place(ChatColor.RED + "---Alliance---", null, "0", hb, location));
	}
	
	
	
}

package me.Haeseke1.Alliances.LeaderBoard;

import net.md_5.bungee.api.ChatColor;

public class Place {
	
	public String leaderboardName;
	public String keeper;
	public Head_Board head_board;
	public String score;
	public int location;
	
	public Place(String leaderboardName, String keeper, String score, Head_Board hb, int location) {
		this.keeper = keeper;
		this.score = score;
		this.head_board = hb;
		this.location = location;
		if(hb != null){
			hb.setLineMessage(0, leaderboardName);
			hb.setLineMessage(1, "&6&l#" + location);
			hb.setLineMessage(2, ChatColor.YELLOW + keeper);
			hb.setLineMessage(3, ChatColor.AQUA + score);
		}
	}
	
	public void setHead_board(Head_Board hb){
		this.head_board = hb;
		hb.setLineMessage(0, leaderboardName);
		hb.setLineMessage(1, "&6&l#" + location);
		hb.setLineMessage(2, ChatColor.YELLOW + keeper);
		hb.setLineMessage(3, ChatColor.AQUA + score);
	}
	
	public void setKeeper(String keeper){
		this.keeper = keeper;
		if(this.head_board != null){
			this.head_board.setLineMessage(0, leaderboardName);
			this.head_board.setLineMessage(1, "&6&l#" + location);
			this.head_board.setLineMessage(2, ChatColor.YELLOW + keeper);
			this.head_board.setLineMessage(3, ChatColor.AQUA + score);
		}
	}
	
	public void setScore(String score){
		this.score = score;
		if(this.head_board != null){
			this.head_board.setLineMessage(0, leaderboardName);
			this.head_board.setLineMessage(1, "&6&l#" + location);
			this.head_board.setLineMessage(2, ChatColor.YELLOW + keeper);
			this.head_board.setLineMessage(3, ChatColor.AQUA + score);
		}
	}
	
}

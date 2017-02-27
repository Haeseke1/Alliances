package me.Haeseke1.Alliances.LeaderBoard.Type;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;

import me.Haeseke1.Alliances.LeaderBoard.Head_Board;
import me.Haeseke1.Alliances.LeaderBoard.LeaderBoard;
import me.Haeseke1.Alliances.LeaderBoard.Place;
import me.Haeseke1.Alliances.Vote.VotePlayer;
import net.md_5.bungee.api.ChatColor;

public class Monthly_Vote_LeaderBoard extends LeaderBoard{
	
	public static Monthly_Vote_LeaderBoard monthly_Vote_LeaderBoard;
	
	public Monthly_Vote_LeaderBoard(List<Place> places) {
		super(places);
		monthly_Vote_LeaderBoard = this;
	}
	
	public void addPlace(int location, Head_Board hb){
		places.add(new Place(ChatColor.RED + "-Monthly Vote-", null, ChatColor.AQUA + "0", hb, location));
	}
	
	public void update(){
		for(Place place : this.places){
			place.setKeeper(null);
			place.setScore(ChatColor.AQUA + "0");
		}
		Collections.sort(VotePlayer.voteplayers, new Comparator<VotePlayer>() {

			@Override
			public int compare(VotePlayer o1, VotePlayer o2) {
				return o1.monthly_votes - o2.monthly_votes;
			}
			
		});
		Collections.reverse(VotePlayer.voteplayers);
		for(Place place : this.places){
			if(VotePlayer.voteplayers.size() >= place.location){
				place.setKeeper(ChatColor.DARK_GREEN + Bukkit.getOfflinePlayer(VotePlayer.voteplayers.get(place.location - 1).uuid).getName());
				place.setScore(ChatColor.AQUA + "" + VotePlayer.voteplayers.get(place.location - 1).monthly_votes);
				place.head_board.setOwner(Bukkit.getOfflinePlayer(VotePlayer.voteplayers.get(place.location - 1).uuid).getName());
			}
		}
	}
	
	
}

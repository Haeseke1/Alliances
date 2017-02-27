package me.Haeseke1.Alliances.LeaderBoard.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Haeseke1.Alliances.LeaderBoard.Head_Board;
import me.Haeseke1.Alliances.LeaderBoard.LeaderBoard;
import me.Haeseke1.Alliances.LeaderBoard.Place;
import me.Haeseke1.Alliances.Main.Main;
import net.md_5.bungee.api.ChatColor;

public class Player_LeaderBoard extends LeaderBoard{

	
	public static Player_LeaderBoard player_LeaderBoard;
	
	public Player_LeaderBoard(List<Place> places){
	    super(places);
		player_LeaderBoard = this;
	}
	
	public void addPlace(int location, Head_Board hb){
		places.add(new Place(ChatColor.RED + "---Players---", null, ChatColor.AQUA + "0", hb, location));
	}
	
	public void update(){
		for(Place place : this.places){
			place.setKeeper(null);
			place.setScore(ChatColor.AQUA + "0");
		}
		if(!new File(Main.plugin.getDataFolder(),"PlayerData").exists()){
			return;
		}
		List<player_score> list = new ArrayList<player_score>();
		for(File f : new File(Main.plugin.getDataFolder(),"PlayerData").listFiles()){
			FileConfiguration file = YamlConfiguration.loadConfiguration(f);
			list.add(new player_score(file.getString("Name"), file.getInt("Score")));
		}
		Collections.sort(list, new player_score());
		Collections.reverse(list);
		for(Place place : this.places){
			if(list.size() >= place.location){
				place.setKeeper(list.get(place.location - 1).name);
				place.setScore(ChatColor.AQUA + "" + list.get(place.location - 1).score);
				place.head_board.setOwner(list.get(place.location - 1).name);
			}
		}
	}
	
	
	public class player_score implements Comparator<player_score>{
		
		public String name;
		public int score;
		
		public player_score() {
			
		}
		
		public player_score(String name, int score) {
			this.name = name;
			this.score = score;
		}

		@Override
		public int compare(player_score o1, player_score o2) {
			return o1.score - o2.score;
		}

	}
	
	
	
	
}

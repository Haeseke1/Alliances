package me.Haeseke1.Alliances.LeaderBoard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.configuration.file.FileConfiguration;

import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.LeaderBoard.Type.All_Time_Vote_LeaderBoard;
import me.Haeseke1.Alliances.LeaderBoard.Type.Alliance_LeaderBoard;
import me.Haeseke1.Alliances.LeaderBoard.Type.Player_LeaderBoard;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;

public class LeaderBoard {
	
	
	public List<Place> places = new ArrayList<Place>();
	
	public LeaderBoard(List<Place> places) {
		this.places = places;
	}
	
	
	public static boolean addPlace(String leaderboard, int place, Head_Board hb){
		switch(leaderboard.toLowerCase()){
		case "alliance":
			Alliance_LeaderBoard.alli_Leaderboard.addPlace(place, hb);
			return true;
		case "player":
			Player_LeaderBoard.player_LeaderBoard.addPlace(place, hb);
			return true;
		case "avote":
			All_Time_Vote_LeaderBoard.all_Time_Vote_LeaderBoard.addPlace(place, hb);
			return true;
		default:
			return false;
		}
	}
	
	public static void registerLeaderboard(){
		FileConfiguration file = Main.LeaderboardConfig;
		new Alliance_LeaderBoard(new ArrayList<Place>());
		if(file.contains("Alliance")){
			for(String keys : file.getConfigurationSection("Alliance").getKeys(false)){
				Location b1 = null;
				Location b2 = null;
				int i = 1;
				try {
					 b1 = ConfigManager.getLocationFromConfig(file, "Alliance." + keys + ".Sign");
					 b2 = ConfigManager.getLocationFromConfig(file, "Alliance." + keys + ".Skull");
					 i = ConfigManager.getIntFromConfig(file, "Alliance." + keys + ".Place");
				} catch (EmptyLocationException | EmptyIntException e) {
					e.printStackTrace();
					continue;
				}
				if(b1.getBlock().getState() instanceof Sign && b2.getBlock().getState() instanceof Skull){
					LeaderBoard.addPlace("alliance", i, new Head_Board((Sign) b1.getBlock().getState(),(Skull) b2.getBlock().getState()));
				}
			}
		}
		new Player_LeaderBoard(new ArrayList<Place>());
		if(file.contains("Player")){
			for(String keys : file.getConfigurationSection("Player").getKeys(false)){
				Location b1 = null;
				Location b2 = null;
				int i = 1;
				try {
					 b1 = ConfigManager.getLocationFromConfig(file, "Player." + keys + ".Sign");
					 b2 = ConfigManager.getLocationFromConfig(file, "Player." + keys + ".Skull");
					 i = ConfigManager.getIntFromConfig(file, "Player." + keys + ".Place");
				} catch (EmptyLocationException | EmptyIntException e) {
					e.printStackTrace();
					continue;
				}
				if(b1.getBlock().getState() instanceof Sign && b2.getBlock().getState() instanceof Skull){
					LeaderBoard.addPlace("player", i, new Head_Board((Sign) b1.getBlock().getState(),(Skull) b2.getBlock().getState()));
				}
			}
		}
		new All_Time_Vote_LeaderBoard(new ArrayList<Place>());
		if(file.contains("A_Vote")){
			for(String keys : file.getConfigurationSection("A_Vote").getKeys(false)){
				Location b1 = null;
				Location b2 = null;
				int i = 1;
				try {
					 b1 = ConfigManager.getLocationFromConfig(file, "A_Vote." + keys + ".Sign");
					 b2 = ConfigManager.getLocationFromConfig(file, "A_Vote." + keys + ".Skull");
					 i = ConfigManager.getIntFromConfig(file, "A_Vote." + keys + ".Place");
				} catch (EmptyLocationException | EmptyIntException e) {
					e.printStackTrace();
					continue;
				}
				if(b1.getBlock().getState() instanceof Sign && b2.getBlock().getState() instanceof Skull){
					LeaderBoard.addPlace("player", i, new Head_Board((Sign) b1.getBlock().getState(),(Skull) b2.getBlock().getState()));
				}
			}
		}
	}
	
	public static void saveLeaderboard(){
		FileConfiguration file = Main.LeaderboardConfig;
		file.set("Alliance", null);
		int i = 0;
		for(Place place : Alliance_LeaderBoard.alli_Leaderboard.places){
			ConfigManager.setLocationFromConfig(file, "Alliance." + i + ".Sign", place.head_board.sign.getLocation());
			ConfigManager.setLocationFromConfig(file, "Alliance." + i + ".Skull", place.head_board.skull.getLocation());
			file.set("Alliance." + i + ".Place", place.location);
			i++;
		}
		file.set("Player", null);
		i = 0;
		for(Place place : Player_LeaderBoard.player_LeaderBoard.places){
			ConfigManager.setLocationFromConfig(file, "Player." + i + ".Sign", place.head_board.sign.getLocation());
			ConfigManager.setLocationFromConfig(file, "Player." + i + ".Skull", place.head_board.skull.getLocation());
			file.set("Player." + i + ".Place", place.location);
			i++;
		}
		file.set("A_Vote", null);
		i = 0;
		for(Place place : All_Time_Vote_LeaderBoard.all_Time_Vote_LeaderBoard.places){
			ConfigManager.setLocationFromConfig(file, "A_Vote." + i + ".Sign", place.head_board.sign.getLocation());
			ConfigManager.setLocationFromConfig(file, "A_Vote." + i + ".Skull", place.head_board.skull.getLocation());
			file.set("A_Vote." + i + ".Place", place.location);
			i++;
		}
	}
	
}

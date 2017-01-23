package me.Haeseke1.Alliances.APlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.ScoreBoard.aScoreBoardManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class aPlayer{
	
	
	public static List<aPlayer> online_Players = new ArrayList<aPlayer>();

	public int coins;
	public Player player;
	public FileConfiguration file;
	public File f;
	
	public int wins;
	public int losses;
	
	public Scoreboard scoreboard;
	public List<String> scores = new ArrayList<String>();
	public Objective sideBar;

	public aPlayer(Player player, File f, FileConfiguration file) {
		coins = Coins.getPlayerCoins(player);
		this.player = player;
		this.file = file;
		this.f = f;
		this.wins = 0;
		this.losses = 0;
		registerConfig();
		online_Players.add(this);
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		player.setScoreboard(scoreboard);
	}
	
	
	public void registerConfig(){
		
	}
	
	public void saveConfig(){
		try {
			file.save(f);
		} catch (IOException e) {
			MessageManager.sendAlertMessage("Could not save " + file.getName() + "!");
		}
	}
	
	public void addWin(){
		this.wins = this.wins + 1;
	}
	
    public int getWins(){
    	return this.wins;
    }
    
    public int getLoses(){
    	return this.losses;
    }
    
    
    
    
    
    
    
    public void addScore(String s){
    	scores.add(s);
    }
    
    public void resetScore(){
		for (String str : scores) {
			scoreboard.resetScores(str);
		}
		scores.clear();
    }
    
    
	public void setAllianceScoreBoard() {
		if (sideBar == null) {
			sideBar = scoreboard.registerNewObjective("dummy", "test");
			sideBar.setDisplaySlot(DisplaySlot.SIDEBAR);
			sideBar.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "Alliance" + ChatColor.GOLD + "  ===");
		}
		sideBar.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "Alliance" + ChatColor.GOLD + "  ===");
		if (!AllianceManager.playerIsInAlli(player)) {
			if(!scores.contains(ChatColor.RED + "No Alliance")){
				resetScore();
				aScoreBoardManager.setScore(this, ChatColor.RED + "No Alliance", 20, sideBar, ChatColor.RED, null);
			}
			return;
		}
		Alliance alli = AllianceManager.getAlliance(player);
		if(!scores.contains(ChatColor.translateAlternateColorCodes('&', alli.getName())) ||
				!scores.contains(ChatColor.AQUA + "" + AllianceManager.getMemberCount(alli)) ||
						!scores.contains(ChatColor.AQUA + "" + alli.getWins() + "W") ||
						!scores.contains(ChatColor.AQUA + "" + alli.getLoses() + "L") ||
						!scores.contains(ChatColor.AQUA + "" + alli.getCoins() + " coins")){
			resetScore();
			aScoreBoardManager.setScore(this, ChatColor.GREEN + "Alliance:", 18, sideBar, ChatColor.GREEN,ChatColor.translateAlternateColorCodes('&', alli.getName()));
			aScoreBoardManager.setScore(this, ChatColor.GREEN + "Online:", 15, sideBar, ChatColor.BLUE,ChatColor.AQUA + "" + AllianceManager.getMemberCount(alli));
			aScoreBoardManager.setScore(this, ChatColor.GREEN + "Wins:", 12, sideBar, ChatColor.YELLOW,ChatColor.AQUA + "" + alli.getWins() + "W");
			aScoreBoardManager.setScore(this, ChatColor.GREEN + "Loses:", 9, sideBar, ChatColor.DARK_PURPLE,ChatColor.AQUA + "" + alli.getLoses() + "L");
			aScoreBoardManager.setScore(this, ChatColor.GREEN + "Coins:", 6, sideBar, ChatColor.LIGHT_PURPLE,ChatColor.AQUA + "" + alli.getCoins() + " coins");
		}
		return;
	}

	public void setPlayerScoreBoard() {
		if (sideBar == null) {
			sideBar = scoreboard.registerNewObjective("dummy", "test");
			sideBar.setDisplaySlot(DisplaySlot.SIDEBAR);
			sideBar.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "Player" + ChatColor.GOLD + "  ===");
		}
		sideBar.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "Player" + ChatColor.GOLD + "  ===");
		Alliance alli = AllianceManager.getAlliance(player);
	  if(alli == null){
		if(!scores.contains(ChatColor.AQUA + "" + wins + "W") ||
		   !scores.contains(ChatColor.AQUA + "" + losses + "L") ||
		   !scores.contains(ChatColor.AQUA + "" + Coins.getPlayerCoins(player) + " coins") ||
		   !scores.contains(ChatColor.RED + "No alliance")){
		resetScore();
		aScoreBoardManager.setScore(this, ChatColor.GREEN + "Alliance:", 15, sideBar, ChatColor.BLACK, ChatColor.RED + "No alliance");
		aScoreBoardManager.setScore(this, ChatColor.GREEN + "Wins:", 12, sideBar, ChatColor.YELLOW, ChatColor.AQUA + "" + wins + "W");
		aScoreBoardManager.setScore(this, ChatColor.GREEN + "Loses:", 9, sideBar, ChatColor.DARK_PURPLE, ChatColor.AQUA + "" + losses + "L");
		aScoreBoardManager.setScore(this, ChatColor.GREEN + "Coins:", 6, sideBar, ChatColor.LIGHT_PURPLE, ChatColor.AQUA + "" + Coins.getPlayerCoins(player) + " coins");
		return;
		}
	  }else{
			if(!scores.contains(ChatColor.AQUA + "" + wins + "W") ||
					   !scores.contains(ChatColor.AQUA + "" + losses + "L") ||
					   !scores.contains(ChatColor.AQUA + "" + Coins.getPlayerCoins(player) + " coins") ||
					   !scores.contains(ChatColor.translateAlternateColorCodes('&', alli.getName()))){
					resetScore();
					aScoreBoardManager.setScore(this, ChatColor.GREEN + "Alliance:", 15, sideBar, ChatColor.BLACK, ChatColor.translateAlternateColorCodes('&', alli.getName()));
					aScoreBoardManager.setScore(this, ChatColor.GREEN + "Wins:", 12, sideBar, ChatColor.YELLOW, ChatColor.AQUA + "" + wins + "W");
					aScoreBoardManager.setScore(this, ChatColor.GREEN + "Loses:", 9, sideBar, ChatColor.DARK_PURPLE, ChatColor.AQUA + "" + losses + "L");
					aScoreBoardManager.setScore(this, ChatColor.GREEN + "Coins:", 6, sideBar, ChatColor.LIGHT_PURPLE, ChatColor.AQUA + "" + Coins.getPlayerCoins(player) + " coins");
					return;
					}		  
	  }
		return;
	}
    
    
    
    
    
    
}

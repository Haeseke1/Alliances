package me.Haeseke1.Alliances.ScoreBoard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.ScoreBoard.Update.Counter;
import net.md_5.bungee.api.ChatColor;

public class aScoreBoard {

	public static Counter updater;
	
	public static void startUpdater(){
		updater = new Counter();
        updater.runTaskTimer(Main.plugin, 0L, 10L);
	}
	
	public static void stopUpdate(){
		updater.cancel();
	}
	
	public static Scoreboard createScoreBoard(Player player){
		ScoreboardManager sbm = Bukkit.getScoreboardManager();
		Scoreboard board = sbm.getNewScoreboard();
		Objective obj = board.registerNewObjective("dummy", "test");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "Alliances" + ChatColor.GOLD + "  ===");
		Score space = obj.getScore(ChatColor.RED.toString());
		space.setScore(10);
		if(!AllianceManager.playerIsInAlli(player)){
		Score score = obj.getScore(ChatColor.RED + "No Alliance");
		score.setScore(9);
		return board;
		}
		Score score = obj.getScore(ChatColor.GREEN + "Alliance:");
		score.setScore(9);
		Alliance alliance = AllianceManager.getAlliance(player);
		Score score1 = obj.getScore(ChatColor.GOLD + AllianceManager.getAlliance(player).getName());
		score1.setScore(8);
		Score online = obj.getScore(ChatColor.GREEN + "Online:");
		online.setScore(6);
		int count = AllianceManager.getMemberCount(alliance);
		Score space2 = obj.getScore(ChatColor.RED.toString());
		space2.setScore(7);
		Score score2 = obj.getScore(ChatColor.AQUA + "" + count);
		score2.setScore(5);
		return board;
	}
	
	public static void updateScoreboard(Player player){
		player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
		Scoreboard board = player.getScoreboard();
		Objective obj = board.registerNewObjective("dummy", "test");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "Alliances" + ChatColor.GOLD + "  ===");
		Score space = obj.getScore(ChatColor.AQUA.toString());
		space.setScore(10);
		if(!AllianceManager.playerIsInAlli(player)){
		Score score = obj.getScore(ChatColor.RED + "No Alliance");
		score.setScore(9);
		player.setScoreboard(board);
		return;
		}
		Score score = obj.getScore(ChatColor.GREEN + "Alliance:");
		score.setScore(9);
		Alliance alliance = AllianceManager.getAlliance(player);
		Score score1 = obj.getScore(ChatColor.GOLD + AllianceManager.getAlliance(player).getName());
		score1.setScore(8);
		Score online = obj.getScore(ChatColor.GREEN + "Online:");
		online.setScore(6);
		int count = AllianceManager.getMemberCount(alliance);
		Score space2 = obj.getScore(ChatColor.GOLD.toString());
		space2.setScore(7);
		Score score2 = obj.getScore(ChatColor.AQUA + "" + count);
		score2.setScore(5);
		Score space3 = obj.getScore(ChatColor.BLUE.toString());
		space3.setScore(4);
		Score coins = obj.getScore(ChatColor.GREEN + "Coins:");
		coins.setScore(3);
		Score score3 = obj.getScore(ChatColor.AQUA + "" + Coins.getPlayerCoins(player));
		score3.setScore(2);
		Score space4 = obj.getScore(ChatColor.DARK_PURPLE.toString());
		space4.setScore(1);
		Score wins = obj.getScore(ChatColor.GREEN + "Wins:");
		wins.setScore(0);
		Score space5 = obj.getScore(ChatColor.LIGHT_PURPLE.toString());
		space5.setScore(-1);
		Score loses = obj.getScore(ChatColor.GREEN + "Loses:");
		loses.setScore(-2);
		Alliance alli = AllianceManager.getAlliance(player);
		Score score5 = obj.getScore(ChatColor.AQUA + "" + alli.getLoses());
		score5.setScore(-3);
		Score score4 = obj.getScore(ChatColor.AQUA + "" + alli.getWins());
		score4.setScore(-1);
		player.setScoreboard(board);
		return;
	}
}

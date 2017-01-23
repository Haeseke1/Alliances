package me.Haeseke1.Alliances.ScoreBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.Haeseke1.Alliances.APlayer.APlayerManager;
import me.Haeseke1.Alliances.APlayer.aPlayer;
import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.ScoreBoard.Update.Counter;
import net.md_5.bungee.api.ChatColor;

public class aScoreBoard {

	public static Counter updater;
	public static Scoreboard board;
	public static ScoreboardManager sbm;

	private static Objective obj = null;

	public static HashMap<Player, List<String>> scores = new HashMap<>();
	
	public static int timer = 0;
	
	public static boolean playerScoreboard = false;
	public static boolean alliScoreboard = true;
	

	public static void startUpdater() {
		updater = new Counter();
		updater.runTaskTimer(Main.plugin, 0L, 10L);
		sbm = Bukkit.getScoreboardManager();
		board = sbm.getMainScoreboard();
	}

	public static void stopUpdate() {
		updater.cancel();
	}

	public static void setAllianceScoreBoard(Player player) {
		if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) {
			obj = board.registerNewObjective("dummy", "test");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "Alliance"
					+ ChatColor.GOLD + "  ===");
		}
		obj = board.getObjective(DisplaySlot.SIDEBAR);
		if (scores.containsKey(player)) {
			for (String str : scores.get(player)) {
				board.resetScores(str);
			}
		}
		if (!AllianceManager.playerIsInAlli(player)) {
			setScore(player, ChatColor.RED + "No Alliance", 20, obj, ChatColor.RED, null);
			return;
		}
		Alliance alli = AllianceManager.getAlliance(player);
		setScore(player, ChatColor.GREEN + "Alliance:", 18, obj, ChatColor.GREEN,ChatColor.translateAlternateColorCodes('&', alli.getName()));
		setScore(player, ChatColor.GREEN + "Online:", 15, obj, ChatColor.BLUE,ChatColor.AQUA + "" + AllianceManager.getMemberCount(alli));
		setScore(player, ChatColor.GREEN + "Wins:", 12, obj, ChatColor.YELLOW,ChatColor.AQUA + "" + alli.getWins() + "W");
		setScore(player, ChatColor.GREEN + "Loses:", 9, obj, ChatColor.DARK_PURPLE,ChatColor.AQUA + "" + alli.getLoses() + "L");
		setScore(player, ChatColor.GREEN + "Coins:", 6, obj, ChatColor.LIGHT_PURPLE,ChatColor.AQUA + "" + alli.getCoins() + " coins");
		return;
	}

	public static void setPlayerScoreBoard(Player player) {
		if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) {
			obj = board.registerNewObjective("dummy", "test");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "Player" + ChatColor.GOLD + "  ===");
		}
		obj = board.getObjective(DisplaySlot.SIDEBAR);
		if (scores.containsKey(player)) {
			for (String str : scores.get(player)) {
				board.resetScores(str);
			}
		}
		Alliance alli = AllianceManager.getAlliance(player);
		setScore(player, ChatColor.GREEN + "Alliance:", 15, obj, ChatColor.GREEN,
				ChatColor.translateAlternateColorCodes('&', alli.getName()));
		aPlayer aplayer = APlayerManager.getAPlayer(player);
		setScore(player, ChatColor.GREEN + "Wins:", 12, obj, ChatColor.YELLOW,
				ChatColor.AQUA + "" + aplayer.getWins() + "W");
		setScore(player, ChatColor.GREEN + "Loses:", 9, obj, ChatColor.DARK_PURPLE,
				ChatColor.AQUA + "" + aplayer.getLoses() + "L");
		setScore(player, ChatColor.GREEN + "Coins:", 6, obj, ChatColor.LIGHT_PURPLE,
				ChatColor.AQUA + "" + Coins.getPlayerCoins(player) + " coins");
		return;
	}

	public static void setScore(Player player, String scorename, int score, Objective obj, ChatColor color,
			String value) {
		List<String> currentScores = new ArrayList<>();
		Score online = obj.getScore(scorename);
		online.setScore(score);
		if (value != null) {
			Score valueS = obj.getScore(value);
			valueS.setScore(score - 1);
			Score space = obj.getScore(color.toString());
			space.setScore(score - 2);
			currentScores.add(value);
			currentScores.add(space.toString());
			scores.put(player, currentScores);
			return;
		}
		Score space = obj.getScore(color.toString());
		space.setScore(score - 1);
		currentScores.add(space.toString());
		currentScores.add(scorename);
		scores.put(player, currentScores);
		return;
	}
}

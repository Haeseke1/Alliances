package me.Haeseke1.Alliances.Vote;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class VotePlayer {
	
	public static List<VotePlayer> voteplayers = new ArrayList<>();

	public int weekly_votes;
	public int monthly_votes;
	public int total_votes;
	
	public int month;
	public int week;
	
	public static int month_int = Calendar.MONTH;
	public static int week_int = Calendar.WEEK_OF_YEAR;
	
	public UUID uuid;
	
	public VotePlayer(UUID playerUUID,int weekly,int monthly,int total,int week,int month){
		if(voteplayers.contains(this)){
			return;
		}
		MessageManager.sendAlertMessage("month_int: " + month_int + " week_int: " + week_int);
		this.uuid = playerUUID;
		if (week_int == week) {
			weekly_votes = weekly;
		} else {
			weekly_votes = 0;
		}
		if (month_int == month) {
			monthly_votes = monthly;
		} else {
			monthly_votes = 0;
		}
		this.month = month_int;
		this.week = week_int;
		total_votes = total;
		voteplayers.add(this);
	}
	
	public VotePlayer(UUID playerUUID){
		this.uuid = playerUUID;
		weekly_votes = 0;
		monthly_votes = 0;
		total_votes = 0;
		voteplayers.add(this);
	}
	
	public void addVote(){
		this.monthly_votes = this.monthly_votes + 1;
		this.weekly_votes = this.weekly_votes + 1;
		this.total_votes = this.total_votes + 1;
	}
	
	public static VotePlayer getVotePlayer(UUID playerUUID){
		for(VotePlayer vplayer: VotePlayer.voteplayers){
			if(vplayer.uuid.equals(playerUUID)){
				return vplayer;
			}
		}
		return new VotePlayer(playerUUID);
	}
	
	public static VotePlayer loadVotePlayer(UUID playerUUID){
		if(Main.StatsConfig.contains(playerUUID.toString())){
			int w_votes = Main.StatsConfig.getInt(playerUUID.toString() + ".w_votes");
			int m_votes = Main.StatsConfig.getInt(playerUUID.toString() + ".m_votes");
			int t_votes = Main.StatsConfig.getInt(playerUUID.toString() + ".t_votes");
			int week = Main.StatsConfig.getInt(playerUUID.toString() + ".week");
			int month = Main.StatsConfig.getInt(playerUUID.toString() + ".month");
			VotePlayer vplayer = new VotePlayer(playerUUID,w_votes,m_votes,t_votes,week,month);
			return vplayer;
		}
		VotePlayer vplayer = new VotePlayer(playerUUID);
		return vplayer;
	}
	
	public static void saveVotePlayers(){
		for(VotePlayer vplayer: VotePlayer.voteplayers){
			if(vplayer.month != VotePlayer.month_int){
				vplayer.monthly_votes = 0;
				vplayer.month = VotePlayer.month_int;
			}
			if(vplayer.week != VotePlayer.week_int){
				vplayer.weekly_votes = 0;
				vplayer.week = VotePlayer.week_int;
			}
			Main.StatsConfig.set(vplayer.uuid.toString() + ".w_votes", vplayer.weekly_votes);
			Main.StatsConfig.set(vplayer.uuid.toString() + ".m_votes", vplayer.monthly_votes);
			Main.StatsConfig.set(vplayer.uuid.toString() + ".t_votes", vplayer.total_votes);
			Main.StatsConfig.set(vplayer.uuid.toString() + ".week", vplayer.week);
			Main.StatsConfig.set(vplayer.uuid.toString() + ".month", vplayer.month);
		}
		ConfigManager.saveCustomConfig(new File(Main.plugin.getDataFolder(),"stats.yml"), Main.StatsConfig);
		MessageManager.sendRemarkMessage("Registered the voteplayers in the config");
	}
	
	public static void loadVotePlayers(){
		for(String uuid: Main.StatsConfig.getKeys(false)){
			UUID playerUUID = UUID.fromString(uuid);
		    loadVotePlayer(playerUUID);
		}
		MessageManager.sendRemarkMessage("Loaded the voteplayers in the code");
	}
	
}

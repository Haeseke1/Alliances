package me.Haeseke1.Alliances.Vote;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Main.SQL;
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
	
	public VotePlayer(UUID playerUUID,int weekly,int monthly,int total){
		this.uuid = playerUUID;
		weekly_votes = weekly;
		monthly_votes = monthly;
		total_votes = total;
		voteplayers.add(this);
	}
	
	public VotePlayer(UUID playerUUID){
		if(SQL.SQL){
			SQL.addDataToTable("voteplayer", "\"" + playerUUID.toString() + "\", 0, 0, 0");
		}
		this.uuid = playerUUID;
		weekly_votes = 0;
		monthly_votes = 0;
		total_votes = 0;
		voteplayers.add(this);
	}
	
	public void addVote(){
		if(SQL.SQL){
			ResultSet rs = SQL.tableGetData("voteplayer", "UUID", "\"" + uuid.toString() + "\"");
			try {
				rs.next();
				SQL.setDataToTable("voteplayer", "Weekly", (rs.getInt("Weekly") + 1) + "" , "UUID", "\"" + uuid.toString() + "\"");
				SQL.setDataToTable("voteplayer", "Monthly", (rs.getInt("Monthly") + 1) + "", "UUID", "\"" + uuid.toString() + "\"");
				SQL.setDataToTable("voteplayer", "All_Time", (rs.getInt("All_Time") + 1) + "", "UUID", "\"" + uuid.toString() + "\"");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = SQL.tableGetData("voteplayer", "UUID", "\"" + uuid.toString() + "\"");
			try {
				rs.next();
				this.monthly_votes = rs.getInt("Monthly");
				this.weekly_votes = rs.getInt("Weekly");
				this.total_votes = rs.getInt("All_Time");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		this.monthly_votes++;
		this.weekly_votes++;
		this.total_votes++;
		
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
		if(SQL.SQL){
			return;
		}
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
		if(SQL.SQL){
			ResultSet rs = SQL.getTable("voteplayer");
			try {
				while(rs.next()){
					new VotePlayer(UUID.fromString(rs.getString("UUID")), rs.getInt("Weekly"), rs.getInt("Monthly"), rs.getInt("All_Time"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			for(String uuid: Main.StatsConfig.getKeys(false)){
				UUID playerUUID = UUID.fromString(uuid);
			    loadVotePlayer(playerUUID);
			}
		}
		MessageManager.sendRemarkMessage("Loaded the voteplayers in the code");
	}
	
}

package me.Haeseke1.Alliances.Outpost;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Outpost {
	
	public static int rewardTime;
	public static int Coin_Reward;
	
	public static List<Outpost> outposts = new ArrayList<Outpost>();
	
	
	public String name;
	
	public int xmin;
	public int zmin;
	public int xmax;
	public int zmax;
	public World world;
	
	public Alliance owner;
	
	public List<Player> players = new ArrayList<Player>();
	
	
	public Outpost(String name, Location loc1, Location loc2, Alliance owner) {
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.owner = owner;
		this.world = loc1.getWorld();
		if(loc1.getBlockX() > loc2.getBlockX()){
			xmin = loc2.getBlockX();
			xmax = loc1.getBlockX();
		}else{
			xmin = loc1.getBlockX();
			xmax = loc2.getBlockX();
		}
		if(loc1.getBlockZ() > loc2.getBlockZ()){
			zmin = loc2.getBlockZ();
			zmax = loc1.getBlockZ();
		}else{
			zmin = loc1.getBlockZ();
			zmax = loc2.getBlockZ();
		}
		outposts.add(this);
	}
	
	public Outpost(String name, World world, int xmin, int xmax, int zmin, int zmax, Alliance alli) {
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.world = world;
		this.xmin = xmin;
		this.xmax = xmax;
		this.zmin = zmin;
		this.zmax = zmax;
		this.owner = alli;
		outposts.add(this);
	}
	
	
	
	
	boolean oldContested = false;
	boolean contested = false;
	int contestedTime = 0;
	
	int currentReward = 0;
	
	public void update(){
		List<Player> players = new ArrayList<Player>();
		contested = false;
		for(Player player : Bukkit.getOnlinePlayers()){
			if(OutpostManager.getOutpost(player.getLocation()) != null && OutpostManager.getOutpost(player.getLocation()).equals(this)){
				players.add(player);
			}
		}
		
		if(owner == null){
			for(Player player : players){
				if(AllianceManager.playerIsInAlli(player)){
					owner = AllianceManager.getAlliance(player);
					owner.sendPlayersMessage(ChatColor.DARK_GREEN + "Your alliance secured a outpost!");
					break;
				}
			}
			if(owner == null){
				return;
			}
		}
		
		boolean hasOwner = false;
		for(Player player : players){
			if(AllianceManager.getAlliance(player) != null && AllianceManager.getAlliance(player).equals(owner)){
				hasOwner = true;
			}
		}
		if(!hasOwner){
			owner.sendPlayersMessage(ChatColor.RED + "Your alliance lost a outpost!");
			owner = null;
			currentReward = 0;
			return;
		}
		
		for(Player player : players){
			if(AllianceManager.getAlliance(player) == null || !AllianceManager.getAlliance(player).equals(owner)){
				contested = true;
				oldContested = true;
				contestedTime++;
				break;
			}
		}
		if(contestedTime == 5){
			for(Player player : players){
				MessageManager.sendMessage(player, ChatColor.RED + "This outpost is contested by another person!");
			}
			contestedTime = 0;
		}
		if(!contested){
			if(currentReward == 0)
				owner.sendPlayersMessage(ChatColor.YELLOW + "0% completed until next reward!");
			if(currentReward == rewardTime / 4)
				owner.sendPlayersMessage(ChatColor.YELLOW + "25% completed until next reward!");
			if(currentReward == rewardTime / 2)
				owner.sendPlayersMessage(ChatColor.YELLOW + "50% completed until next reward!");
			if(currentReward == rewardTime / 4 * 3)
				owner.sendPlayersMessage(ChatColor.YELLOW + "75% completed until next reward!");
			if(currentReward == rewardTime){
				owner.sendPlayersMessage(ChatColor.DARK_GREEN + "Your alliance is rewarded with " + ChatColor.YELLOW + Coin_Reward + " coins!");
				Coins.addAllianceCoins(owner, Coin_Reward);
				currentReward = 0;
			}
			currentReward++;
			if(oldContested){
				owner.sendPlayersMessage(ChatColor.DARK_GREEN + "The outpost is safe!");
				oldContested = false;
			}
		}
	}
}

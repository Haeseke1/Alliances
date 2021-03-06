package me.Haeseke1.Alliances.PVE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.Haeseke1.Alliances.APlayer.APlayerManager;
import me.Haeseke1.Alliances.APlayer.aPlayer;
import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Economy.Coins;
import net.md_5.bungee.api.ChatColor;

public class Arena {
	
	public List<LivingEntity> alive = new ArrayList<LivingEntity>();
	public List<Player> playerAlive = new ArrayList<Player>();
	
	public List<Location> mobSpawns = new ArrayList<Location>();
	public List<Location> playerSpawns = new ArrayList<Location>();
	
	public int xmin;
	public int xmax;
	public int zmin;
	public int zmax;
	public World world;
	
	public boolean busy = false;
	public ArenaStatus as = ArenaStatus.MAINTENANCE;
	
	public String name;
	
	public int countDown = 10;
	public boolean startCountdown = false;
	
	public Group group;
	
	public boolean playing = false;
	
	public Arena(String name, Location loc1, Location loc2) {
		this.name = name;
		world = loc1.getWorld();
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
		PVE.main.arenas.add(this);
	}
	
	public Arena(String name, World world, int xmin, int xmax, int zmin, int zmax, List<Location> playerSpawns, List<Location> mobSpawns) {
		this.name = name;
		this.world = world;
		this.xmin = xmin;
		this.xmax = xmax;
		this.zmin = zmin;
		this.zmax = zmax;
		this.mobSpawns = mobSpawns;
		this.playerSpawns = playerSpawns;
		checkStatus();
		PVE.main.arenas.add(this);
	}
	
	
	
	public void checkStatus(){
		if(playerSpawns.size() == 4 && mobSpawns.size() > 4){
			as = ArenaStatus.READY;
		}
	}
	
	public void startArena(Group group){
		busy = true;
		int i = 0;
		for(Player player : group.members){
			aPlayer aplayer = APlayerManager.getAPlayer(player);
			aplayer.is_in_pve_lobby = false;
			aplayer.is_in_pve_arena = true;
			aplayer.firstRun = true;
			player.teleport(playerSpawns.get(i),TeleportCause.ENDER_PEARL);
			i++;
		}
		this.playerAlive = new ArrayList<Player>();
		for(Player player : group.members){
			this.playerAlive.add(player);
		}
		this.group = group;
		startCountdown = true;
	}
	
	public void forceStopArena(){
		for(LivingEntity le : alive){
			le.remove();
		}
		alive = new ArrayList<>();
		if(group != null){
			group.disband();
			group = null;
		}
		playing = false;
		busy = false;
		playerAlive = new ArrayList<Player>();
	}
	
	
	public void stopArena(boolean playerWon){
		for(LivingEntity le : alive){
			le.remove();
		}
		alive = new ArrayList<>();
		if(playerWon){
			for(Player player : group.members){
				Coins.addPlayerCoins(player, group.settings.getCoinReward() / group.members.size());
				aPlayer aplayer = APlayerManager.getAPlayer(player);
				aplayer.addPlayerScore(group.settings.getCoinReward() / group.members.size());
				aplayer.addPVE_Score(group.settings.getCoinReward() / group.members.size());
			}
			if(AllianceManager.getAlliance(group.members.get(0)) != null){
				Alliance alli = AllianceManager.getAlliance(group.members.get(0));
				alli.addScore(group.settings.getCoinReward());
			}
			group.sendPlayersMessage(ChatColor.GREEN + "Your group survived! " + ChatColor.GOLD + "(+" + (int) (group.settings.getCoinReward() / group.members.size()) + " coins)");
		}else{
			for(Player player : group.members){
				if(Coins.removePlayerCoins(player, (int) (group.settings.getCoinReward() * 1.5) / group.members.size())){
					player.sendMessage(ChatColor.RED + "Your group lost! " + ChatColor.GOLD + "(-" + (int) ((group.settings.getCoinReward() * 1.5) / group.members.size()) + " coins)");
				}else{
					player.sendMessage(ChatColor.RED + "Your group lost! " + ChatColor.GOLD + "(-" + Coins.getPlayerCoins(player) + " coins)");
					Coins.removePlayerCoins(player, Coins.getPlayerCoins(player));
				}	
			}
		}
		countDown = 10;
		startCountdown = false;
		group.disband();
		group = null;
		playing = false;
		busy = false;
		playerAlive = new ArrayList<Player>();		
	}
	
	public void fight(){
		playing = true;
		Settings set = group.settings;
		int loc = 0;
		for(Entry<Integer, Integer> entry : set.zombies.entrySet()){
			for(int i = 0; i < entry.getValue();i++){
				alive.add(Mob_Manager.spawnZombie(entry.getKey(), mobSpawns.get(loc)));
				if(loc == mobSpawns.size() - 1){
					loc = 0;
				}else{
					loc++;
				}
			}
		}
		for(Entry<Integer, Integer> entry : set.blazes.entrySet()){
			for(int i = 0; i < entry.getValue();i++){
				alive.add(Mob_Manager.spawnBlaze(entry.getKey(), mobSpawns.get(loc)));
				if(loc == mobSpawns.size() - 1){
					loc = 0;
				}else{
					loc++;
				}
			}
		}
		for(Entry<Integer, Integer> entry : set.creepers.entrySet()){
			for(int i = 0; i < entry.getValue();i++){
				alive.add(Mob_Manager.spawnCreeper(entry.getKey(), mobSpawns.get(loc)));
				if(loc == mobSpawns.size() - 1){
					loc = 0;
				}else{
					loc++;
				}
			}
		}
		for(Entry<Integer, Integer> entry : set.endermans.entrySet()){
			for(int i = 0; i < entry.getValue();i++){
				alive.add(Mob_Manager.spawnEnderman(entry.getKey(), mobSpawns.get(loc)));
				if(loc == mobSpawns.size() - 1){
					loc = 0;
				}else{
					loc++;
				}
			}
		}
		for(Entry<Integer, Integer> entry : set.skeletons.entrySet()){
			for(int i = 0; i < entry.getValue();i++){
				alive.add(Mob_Manager.spawnSkeleton(entry.getKey(), mobSpawns.get(loc)));
				if(loc == mobSpawns.size() - 1){
					loc = 0;
				}else{
					loc++;
				}
			}
		}
		for(Entry<Integer, Integer> entry : set.spiders.entrySet()){
			for(int i = 0; i < entry.getValue();i++){
				alive.add(Mob_Manager.spawnSpider(entry.getKey(), mobSpawns.get(loc)));
				if(loc == mobSpawns.size() - 1){
					loc = 0;
				}else{
					loc++;
				}
			}
		}
		for(Entry<Integer, Integer> entry : set.wither_skeletons.entrySet()){
			for(int i = 0; i < entry.getValue();i++){
				alive.add(Mob_Manager.spawnWither_Skeleton(entry.getKey(), mobSpawns.get(loc)));
				if(loc == mobSpawns.size() - 1){
					loc = 0;
				}else{
					loc++;
				}
			}
		}
		for(Entry<Integer, Integer> entry : set.withers.entrySet()){
			for(int i = 0; i < entry.getValue();i++){
				alive.add(Mob_Manager.spawnWither(entry.getKey(), mobSpawns.get(loc)));
				if(loc == mobSpawns.size() - 1){
					loc = 0;
				}else{
					loc++;
				}
			}
		}
		for(Entry<Integer, Integer> entry : set.zombie_pigmans.entrySet()){
			for(int i = 0; i < entry.getValue();i++){
				alive.add(Mob_Manager.spawnZombie_Pigman(entry.getKey(), mobSpawns.get(loc)));
				if(loc == mobSpawns.size() - 1){
					loc = 0;
				}else{
					loc++;
				}
			}
		}
	}
	
}

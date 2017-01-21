package me.Haeseke1.Alliances.Arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Arena {

	private HashMap<UUID,Alliance> mPlayersInArena;
	private List<Location> mSpawns;
    private int mSize;
    private int mCountdown;
    private Location mCorner1;
    private Location mCorner2;
    private String mName;
    private String mStatus;
	
	public Arena(String name,int arenaSize,int countdown,Location corner1,Location corner2){
		mPlayersInArena = new HashMap<>();
		mSize = arenaSize;
		mCountdown = countdown;
		mName = name;
		mCorner1 = corner1;
		mCorner2 = corner2;
		mStatus = "UNDER_MAINTANCE";
		mSpawns = new ArrayList<>();
	}
	
	public HashMap<UUID,Alliance> getPlayersInArena() {
		return mPlayersInArena;
	}
	
	public int getSize(){
		return mSize;
	}
	
	public int getCountdown(){
		return mCountdown;
	}
	
	public String getName(){
		return mName;
	}
	
	public String getStatus(){
		return mStatus;
	}
	
	public Location getCorner1(){
		return mCorner1;
	}
	
	public Location getCorner2(){
		return mCorner2;
	}
	
	public int getCurrentSize(){
		return mPlayersInArena.size();
	}
	
	public void sendMessage(String message){
		for(UUID playerUUID: mPlayersInArena.keySet()){
			Player player = Bukkit.getPlayer(playerUUID);
			MessageManager.sendMessage(player, message);
		}
	}
	
	public void sendMessage(String message, Player exception){
		for(UUID playerUUID: mPlayersInArena.keySet()){
			Player player = Bukkit.getPlayer(playerUUID);
			if(player != exception){
			 MessageManager.sendMessage(player, message);
			  }
		}
	}
}

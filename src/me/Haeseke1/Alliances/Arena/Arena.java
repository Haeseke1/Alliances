package me.Haeseke1.Alliances.Arena;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

public class Arena {

	private List<UUID> mPlayersInArena;
	private List<Location> mSpawns;
    private int mSize;
    private int mCountdown;
    private int mCorner1;
    private int mCorner2;
    private String mName;
    private String mStatus;
	
	public Arena(String name,int arenaSize,int countdown,int corner1,int corner2){
		mPlayersInArena = new ArrayList<>();
		mSize = arenaSize;
		mCountdown = countdown;
		mName = name;
		mCorner1 = corner1;
		mCorner2 = corner2;
		mStatus = "UNDER MAINTANCE";
		mSpawns = new ArrayList<>();
	}
	
	public List<UUID> getPlayersInArena() {
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
	
	public int getCorner1(){
		return mCorner1;
	}
	
	public int getCorner2(){
		return mCorner2;
	}
}

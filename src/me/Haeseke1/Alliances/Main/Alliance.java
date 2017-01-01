package me.Haeseke1.Alliances.Main;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class Alliance {
	
	private Player mOwner;
	private int mWins;
	private int mLoses;
	private int mCoins;
	private HashMap<Player,String> mMembers;
	
	public Alliance(Player owner, HashMap<Player,String> members, int wins, int loses, int coins){
		members = new HashMap<Player, String>();
		this.mOwner = owner;
	    this.mWins = wins;
	    this.mLoses = loses;
	    this.mCoins = coins;
	    this.mMembers = members;
	}
	
	public Player getOwner(){
		return mOwner;
	}
	
	public HashMap<Player,String> getMembers(){
	    return mMembers;
	}
	
	public int getWins(){
		return mWins;
	}
	
	public int getLoses(){
		return mLoses;
	}
	
	public int getCoins(){
		return mCoins;
	}
}

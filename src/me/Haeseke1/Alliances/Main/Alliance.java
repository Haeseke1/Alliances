package me.Haeseke1.Alliances.Main;

import java.util.HashMap;
import java.util.UUID;


import me.Haeseke1.Alliances.Alliance.AllianceType;

public class Alliance {

	private UUID mOwner;
	private int mWins;
	private int mLoses;
	private int mCoins;
	private HashMap<UUID, String> mMembers;
	private String name;
	private AllianceType type;
	
	public Alliance(String name, UUID owner, int wins, int loses, int coins, AllianceType type) {
		mMembers = new HashMap<UUID, String>();
		this.name = name;
		this.mOwner = owner;
		this.mWins = wins;
		this.mLoses = loses;
		this.mCoins = coins;
		this.type = type;
	}

	public Alliance(String name, UUID owner, int wins, int loses, int coins, AllianceType type,HashMap<UUID,String> mMembers) {
		this.mMembers = mMembers;
		this.name = name;
		this.mOwner = owner;
		this.mWins = wins;
		this.mLoses = loses;
		this.mCoins = coins;
		this.type = type;
	}
	
	public UUID getOwner() {
		return mOwner;
	}

	public HashMap<UUID, String> getMembers() {
		return mMembers;
	}

	public int getWins() {
		return mWins;
	}

	public int getLoses() {
		return mLoses;
	}

	public int getCoins() {
		return mCoins;
	}

	public String getName() {
		return name;
	}

	public AllianceType getType() {
		return type;
	}
	
}

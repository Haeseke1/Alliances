package me.Haeseke1.Alliances.Main;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Alliance {

	private Player mOwner;
	private int mWins;
	private int mLoses;
	private int mCoins;
	private HashMap<UUID, String> mMembers;

	public Alliance(Player owner, int wins, int loses, int coins) {
		mMembers = new HashMap<UUID, String>();
		this.mOwner = owner;
		this.mWins = wins;
		this.mLoses = loses;
		this.mCoins = coins;
	}

	public Player getOwner() {
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
}

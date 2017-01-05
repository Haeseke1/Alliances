package me.Haeseke1.Alliances.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Alliance.AllianceType;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Alliance {

	private UUID mOwner;
	private int mWins;
	private int mLoses;
	private int mCoins;
	private HashMap<UUID, String> mMembers;
	private String name;
	private AllianceType type;
	
	private List<ItemStack> outpostRewards = new ArrayList<ItemStack>();
	
	public Alliance(String name, UUID owner, int wins, int loses, int coins, AllianceType type) {
		mMembers = new HashMap<UUID, String>();
		this.name = name;
		this.mOwner = owner;
		this.mWins = wins;
		this.mLoses = loses;
		this.mCoins = coins;
		this.type = type;
	}

	public Alliance(String name, UUID owner, int wins, int loses, int coins, AllianceType type, HashMap<UUID,String> mMembers) {
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
	
	public void sendPlayersAlertMessage(String message){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(mMembers.containsKey(player.getUniqueId())){
				MessageManager.sendAlertMessage(player, message);
			}
		}
	}
	
	public void sendPlayersInfoMessage(String message){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(mMembers.containsKey(player.getUniqueId())){
				MessageManager.sendInfoMessage(player, message);
			}
		}
	}
	
	public void sendPlayersRemarkMessage(String message){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(mMembers.containsKey(player.getUniqueId())){
				MessageManager.sendRemarkMessage(player, message);
			}
		}
	}
	
	public void addReward(ItemStack item){
		outpostRewards.add(item);
	}
	
	public boolean claimReward(Player player){
		while(player.getInventory().firstEmpty() >= 0){
			if(!outpostRewards.isEmpty()){
				player.getInventory().addItem(outpostRewards.get(0));
				outpostRewards.remove(0);
			}else{
				return true;
			}
		}
		return false;
	}
	
}

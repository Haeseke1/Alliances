package me.Haeseke1.Alliances.Alliance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Utils.MessageManager;

public class Alliance {

	private UUID mOwner;
	private int mWins;
	private int mLoses;
	private int mCoins;
	private HashMap<UUID, String> mMembers;
	private String name;
	private final AllianceType type;
	
	private List<UUID> admins = new ArrayList<UUID>();
	
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
	
	public void setOwner(UUID owner){
		this.mOwner = owner;
	}

	public HashMap<UUID, String> getMembers() {
		return mMembers;
	}
	
	public void addMember(UUID uuid){
		this.mMembers.put(uuid, "Member");
	}

	public void setMembers(HashMap<UUID, String> mMembers) {
		this.mMembers = mMembers;
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
	
	
	public void sendPlayersMessage(String message){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(mMembers.containsKey(player.getUniqueId())){
				MessageManager.sendMessage(player, message);
			}
		}
	}
	
	
	public void addReward(ItemStack item){
		outpostRewards.add(item);
	}
	
	public List<ItemStack> getReward(){
		return this.outpostRewards;
	}
	
	public void setReward(List<ItemStack> rewards){
		this.outpostRewards = rewards;
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

	public List<UUID> getAdmins() {
		return admins;
	}

	public void addAdmins(UUID admin) {
		this.admins.add(admin);
	}
	public void setAdmins(List<UUID> admins){
		this.admins = admins;
	}
	
}

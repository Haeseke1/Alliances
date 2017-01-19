package me.Haeseke1.Alliances.Alliance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Town.Town;
import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class Alliance {

	private UUID mOwner;
	private int mWins;
	private int mLoses;
	private int mCoins;
	private HashMap<UUID, String> mMembers;
	private String name;
	private final AllianceType type;
	private int exp;
	
	private List<Town> town = new ArrayList<Town>();
	
	
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
		this.exp = 0;
	}

	public Alliance(String name, UUID owner, int wins, int loses, int coins, AllianceType type, HashMap<UUID,String> mMembers, int exp) {
		this.mMembers = mMembers;
		this.name = name;
		this.mOwner = owner;
		this.mWins = wins;
		this.mLoses = loses;
		this.mCoins = coins;
		this.type = type;
		this.exp = exp;
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

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public List<Town> getTown() {
		return town;
	}

	public void setTown(List<Town> town) {
		this.town = town;
	}
	
	public void addTown(Town town){
		this.town.add(town);
	}
	
	public void addLose(Player playerInArena){
		this.mLoses = this.mLoses + 1;
		for(UUID playerUUID: this.getMembers().keySet()){
			Player player = Bukkit.getPlayer(playerUUID);
			if(player.isOnline()){
				MessageManager.sendMessage(player, ChatColor.GOLD + playerInArena.getName() + ChatColor.RED + " lost an arena fight " + ChatColor.GOLD + "(+1 lose)");
			}
		}
	}
	
}

package me.Haeseke1.Alliances.Alliance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.APlayer.APlayerManager;
import me.Haeseke1.Alliances.Arena.Arena;
import me.Haeseke1.Alliances.Arena.ArenaManager;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.PVE.Group;
import me.Haeseke1.Alliances.Town.Town;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;
import net.md_5.bungee.api.ChatColor;

public class Alliance implements Comparator<Alliance>{

	private UUID mOwner;
	private int mWins;
	private int mLoses;
	private int mCoins;
	private HashMap<UUID, String> mMembers;
	private String name;
	private final AllianceType type;
	
	private int Score;
	
	private List<Town> towns = new ArrayList<Town>();
	
	
	private List<UUID> admins = new ArrayList<UUID>();
	
	private List<ItemStack> outpostRewards = new ArrayList<ItemStack>();
	
	public boolean PVE = false;
	public int PVE_amount = 0;
	
	public List<Player> PVE_players = new ArrayList<Player>();
	
	public Alliance() {
		type = null;
	}
	
	public Alliance(String name, UUID owner, int wins, int loses, int coins, AllianceType type) {
		mMembers = new HashMap<UUID, String>();
		mMembers.put(owner, "Owner");
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.mOwner = owner;
		this.mWins = wins;
		this.mLoses = loses;
		this.mCoins = coins;
		this.type = type;
		this.Score = 0;
	}

	public Alliance(String name, UUID owner, int wins, int loses, int coins, AllianceType type, HashMap<UUID,String> mMembers, int score) {
		this.mMembers = mMembers;
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.mOwner = owner;
		this.mWins = wins;
		this.mLoses = loses;
		this.mCoins = coins;
		this.type = type;
		this.Score = score;
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
	
	public void setCoins(int coins) {
		this.mCoins = coins;
	}

	public String getName() {
		return name;
	}

	public AllianceType getType() {
		return type;
	}
	
	
	public void sendPlayersMessage(String message, Player exception){
	  if(exception != null){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(mMembers.containsKey(player.getUniqueId()) && player != exception){
				MessageManager.sendMessage(player, message);
			}
		}
		return;
	  }
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
				if(outpostRewards.get(0) == null){
					outpostRewards.remove(0);
					continue;
				}
				player.getInventory().addItem(outpostRewards.get(0));
				outpostRewards.remove(0);
			}else{
				return true;
			}
		}
		return false;
	}

	public void playSound(Sound sound){
		for(UUID uuid: this.mMembers.keySet()){
			Player player = Bukkit.getPlayer(uuid);
			SoundManager.playSoundToPlayer(sound, player);
		}
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

	public List<Town> getTowns() {
		return towns;
	}

	public void setTown(List<Town> town) {
		this.towns = town;
	}
	
	public void addTown(Town town){
		this.towns.add(town);
	}
	
	public void addLose(Player playerInArena){
		this.mLoses = this.mLoses + 1;
		this.sendPlayersMessage(ChatColor.RED + "Your alliance lost an arena fight " + ChatColor.GOLD + "(+1 lose)",playerInArena);
		MessageManager.sendMessage(playerInArena, ChatColor.RED + "Your team lost the arena fight! " + ChatColor.GOLD + "(- " + Main.settingsConfig.getInt("Arenas_lose_player_reward") +")");
		try {
			if(ConfigManager.getIntFromConfig(Main.settingsConfig, "Arena_lose_alliance_reward_exp") != 0){
			int lostexp = ConfigManager.getIntFromConfig(Main.settingsConfig, "Arena_lose_alliance_reward_exp");
			this.sendPlayersMessage(ChatColor.RED + "(-" + lostexp + " alliance exp)");
			}
			if(ConfigManager.getIntFromConfig(Main.settingsConfig, "Arena_lose_alliance_reward_coins") != 0){
				int lostCoins = ConfigManager.getIntFromConfig(Main.settingsConfig, "Arena_lose_alliance_reward_coins");
				this.sendPlayersMessage(ChatColor.RED + "(-" + lostCoins + " alliance coins)");
				this.setCoins(this.getCoins() - lostCoins);
			}
		} catch (EmptyIntException e1) {
			e1.printStackTrace();
		}
		Arena arena = ArenaManager.getArenaOfPlayer(playerInArena);
		try {
		   if(ArenaManager.checkSign(arena.getName())){
			ArenaManager.updateSign(ArenaManager.getSign(arena.getName()), arena);
		   }
		} catch (IndexOutOfBoundsException | EmptyStringException | EmptyLocationException e) {
			e.printStackTrace();
		}
		this.playSound(Sound.NOTE_BASS_DRUM);
		APlayerManager.getAPlayer(playerInArena).addLose();
	}
	
	public void addWin(){
		this.mWins = this.mWins + 1;
		this.playSound(Sound.ORB_PICKUP);
		try {
			if(ConfigManager.getIntFromConfig(Main.settingsConfig, "Arena_win_alliance_reward_exp") != 0){
			int gainedexp = ConfigManager.getIntFromConfig(Main.settingsConfig, "Arena_win_alliance_reward_exp");
			this.sendPlayersMessage(ChatColor.GREEN + "(+" + gainedexp + " alliance exp)");
			}
			if(ConfigManager.getIntFromConfig(Main.settingsConfig, "Arena_win_alliance_reward_coins") != 0){
				int gainedCoins = ConfigManager.getIntFromConfig(Main.settingsConfig, "Arena_win_alliance_reward_coins");
				this.sendPlayersMessage(ChatColor.GREEN + "(+" + gainedCoins + " alliance coins)");
				this.setCoins(this.getCoins() + gainedCoins);
			}
		} catch (EmptyIntException e1) {
			e1.printStackTrace();
		}
	}
	
	public void addPVE_Player(Player player){
		PVE_players.add(player);
		if(PVE_players.size() == PVE_amount){
			new Group(PVE_players, PVE_players.get(0));
			PVE = false;
			PVE_players = new ArrayList<Player>();
		}
	}
	
	public boolean removePVE_Player(Player player){
		if(PVE_players.contains(player)){
			PVE_players.remove(player);
			if(PVE_players.isEmpty()){
				PVE = false;
			}
			return true;
		}
		return false;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	}
	
	public void addScore(int score){
		Score += score;
	}

	@Override
	public int compare(Alliance alli, Alliance alli2) {
		return alli.Score - alli2.Score;
	}
	
	public static void sendInfo(Player player, String name){
		Alliance alli;
		if(name != null){
	    alli = AllianceManager.getAlliance(name);
		if(alli == null){
			MessageManager.sendMessage(player, "&cThis alliance doesn't exists");
			SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
			return;
		}
		}else{
		if(!AllianceManager.playerIsInAlli(player)){
			MessageManager.sendMessage(player, "&cYou aren't in an alliance");
			SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
			return;
		}
		alli = AllianceManager.getAlliance(player);
		}
		player.sendMessage(" ");
		MessageManager.sendMessage(player, "&c&l === &6" + alli.name + "&c&l ===");
		MessageManager.sendMessage(player, "&6 Score: &b" + alli.Score);
		MessageManager.sendMessage(player, "&6 Coins: &b" + alli.mCoins);
        String members = "";
        for(UUID uuid: alli.getMembers().keySet()){
        	OfflinePlayer offplayer = Bukkit.getOfflinePlayer(uuid);
        	if(offplayer.isOnline()){
        	if(members.isEmpty()){
        		members = "&2" + offplayer.getName(); 
        		continue;
        	}
        	members = members +  ",&2" + offplayer.getName(); 
        	continue;
        	}
        	if(members.isEmpty()){
        		members = "&c" + offplayer.getName(); 
        		continue;
        	}
        	members = members + ",&c" + offplayer.getName(); 
        	continue;
        }
		MessageManager.sendMessage(player, "&6 Members: " + members);
		MessageManager.sendMessage(player, "&2 Wins&6: &b" + alli.mWins);
		MessageManager.sendMessage(player, "&c Loses&6: &b" + alli.mLoses);
		
	}
	
}

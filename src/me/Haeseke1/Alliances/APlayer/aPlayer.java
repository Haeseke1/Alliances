	package me.Haeseke1.Alliances.APlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Exceptions.EmptyItemStackException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.PVE.Arena;
import me.Haeseke1.Alliances.PVE.ArenaManager;
import me.Haeseke1.Alliances.PVE.Group;
import me.Haeseke1.Alliances.PVE.GroupManager;
import me.Haeseke1.Alliances.PVE.PVE;
import me.Haeseke1.Alliances.ScoreBoard.aScoreBoardManager;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class aPlayer{
	
	
	public static List<aPlayer> online_Players = new ArrayList<aPlayer>();

	public int coins;
	public Player player;
	public FileConfiguration file;
	
	public int wins = 0;
	public int loses = 0;
	
	public Scoreboard scoreboard;
	public List<String> scores = new ArrayList<String>();
	public Objective sideBar;
	public Objective belowName;
	public Team team;

	public boolean is_in_pve_lobby = false;
	public boolean is_in_pve_arena = false;
	public boolean firstRun = true;
	
	public final int MAX_MANA = 20;
	public double mana = 20;
	public double manaregen = 1.0;
	public String manaString;
	
	public ArrayList<ItemStack> rewards = new ArrayList<>();
	
	public aPlayer(Player player, FileConfiguration file) {
		coins = Coins.getPlayerCoins(player);
		this.player = player;
		this.file = file;
		this.wins = 0;
		this.loses = 0;
		registerConfig();
		online_Players.add(this);
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		player.setScoreboard(scoreboard);
	}
	
	
	public void registerConfig(){
	  if(!(file.contains("Wins"))){ return;}
	  if(!(file.contains("Loses"))){  return;}
	  if(!(file.contains("Mana_regen"))){ return;}
	  this.wins = file.getInt("Wins");
	  this.loses = file.getInt("Loses");
	  this.manaregen = file.getDouble("Mana_regen");
	  try {
		this.registerRewards();
	} catch (EmptyItemStackException e) {
		e.printStackTrace();
	}
	}
	
	public void saveConfig(){
		file.set("Wins", this.wins);
		file.set("Loses", this.loses);
		file.set("Mana_regen", this.manaregen);
		File file = new File(Main.plugin.getDataFolder() + File.separator + "PlayerData",player.getUniqueId() + ".yml");
 	    ConfigManager.saveCustomConfig(file,this.file);
 	    saveRewards();
	}
	
	public boolean playerHasRewards(){
		if(Main.RewardsConfig.get(player.getUniqueId().toString()) != null) return true;
		return false;
	}
	
	public void registerRewards() throws EmptyItemStackException{
		if(!this.playerHasRewards()) return;
		for(String number: Main.RewardsConfig.getConfigurationSection(player.getUniqueId().toString()).getKeys(false)){
			ItemStack currentitem = ConfigManager.getItemStackFromConfig(Main.RewardsConfig, player.getUniqueId().toString() + "." + number);
		    this.rewards.add(currentitem);
		}
	}
	
	public void saveRewards(){
		if(this.rewards.size() == 0) return;
		if(playerHasRewards()){
		Main.RewardsConfig.set(player.getUniqueId().toString(), null);
		}
		for(ItemStack item: this.rewards){
			int size = Main.RewardsConfig.getConfigurationSection(player.getUniqueId().toString()).getKeys(false).size();
			ConfigManager.setItemStackInConfig(Main.RewardsConfig, player.getUniqueId().toString() + "." + size, item);
		}
	}
	
	public void addWin(){
		this.wins = this.wins + 1;
	}
	
	public void addLose(){
		this.loses = this.loses + 1;
	}
	
    public int getWins(){
    	return this.wins;
    }
    
    public int getLoses(){
    	return this.loses;
    }

    public void addScore(String s){
    	scores.add(s);
    }
    
    public void resetScore(){
		for (String str : scores) {
			scoreboard.resetScores(str);
		}
		scores.clear();
    }
    
    
	public void setAllianceScoreBoard() {
		if (sideBar == null) {
			sideBar = scoreboard.registerNewObjective("dummy", "test");
			sideBar.setDisplaySlot(DisplaySlot.SIDEBAR);
			sideBar.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "Alliance"
					+ ChatColor.GOLD + "  ===");
		}
	
		sideBar.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "Alliance"
				+ ChatColor.GOLD + "  ===");
		if (!AllianceManager.playerIsInAlli(player)) {
			if (!scores.contains(ChatColor.RED + "No Alliance")) {
				resetScore();
				aScoreBoardManager.setScore(this, ChatColor.RED + "No Alliance", 20, sideBar, ChatColor.RED + "", null);
			}
			return;
		}
		Alliance alli = AllianceManager.getAlliance(player);
		if (!scores.contains(alli.getName())
				|| !scores.contains(ChatColor.AQUA + "" + AllianceManager.getMemberCount(alli))
				|| !scores.contains(ChatColor.AQUA + "" + alli.getWins() + "W")
				|| !scores.contains(ChatColor.AQUA + "" + alli.getLoses() + "L")
				|| !scores.contains(ChatColor.AQUA + "" + alli.getCoins() + " coins")) {
			resetScore();
			aScoreBoardManager.setScore(this, ChatColor.GREEN + "Alliance:", 18, sideBar, ChatColor.GREEN + "",
					ChatColor.translateAlternateColorCodes('&', alli.getName()));
			aScoreBoardManager.setScore(this, ChatColor.GREEN + "Online:", 15, sideBar, ChatColor.BLUE + "",
					ChatColor.AQUA + "" + AllianceManager.getMemberCount(alli));
			aScoreBoardManager.setScore(this, ChatColor.GREEN + "Wins:", 12, sideBar, ChatColor.YELLOW + "",
					ChatColor.AQUA + "" + alli.getWins() + "W");
			aScoreBoardManager.setScore(this, ChatColor.GREEN + "Loses:", 9, sideBar, ChatColor.DARK_PURPLE + "",
					ChatColor.AQUA + "" + alli.getLoses() + "L");
			aScoreBoardManager.setScore(this, ChatColor.GREEN + "Coins:", 6, sideBar, ChatColor.LIGHT_PURPLE + "",
					ChatColor.AQUA + "" + alli.getCoins() + " coins");
		}
		return;
	}

	public void setPlayerScoreBoard() {
		if (sideBar == null) {
			sideBar = scoreboard.registerNewObjective("dummy", "test");
			sideBar.setDisplaySlot(DisplaySlot.SIDEBAR);
			sideBar.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "Player"
					+ ChatColor.GOLD + "  ===");
		}
		sideBar.setDisplayName(
				ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "Player" + ChatColor.GOLD + "  ===");
		Alliance alli = AllianceManager.getAlliance(player);
		if (alli == null) {
			if (!scores.contains(ChatColor.AQUA + "" + wins + "W")
					|| !scores.contains(ChatColor.AQUA + "" + loses + "L")
					|| !scores.contains(ChatColor.AQUA + "" + Coins.getPlayerCoins(player) + " coins")
					|| !scores.contains(ChatColor.RED + "No alliance")) {
				resetScore();
				aScoreBoardManager.setScore(this, ChatColor.GREEN + "Alliance:", 15, sideBar, ChatColor.BLACK + "",
						ChatColor.RED + "No alliance");
				aScoreBoardManager.setScore(this, ChatColor.GREEN + "Wins:", 12, sideBar, ChatColor.YELLOW + "",
						ChatColor.AQUA + "" + wins + "W");
				aScoreBoardManager.setScore(this, ChatColor.GREEN + "Loses:", 9, sideBar, ChatColor.DARK_PURPLE + "",
						ChatColor.AQUA + "" + loses + "L");
				aScoreBoardManager.setScore(this, ChatColor.GREEN + "Coins:", 6, sideBar, ChatColor.LIGHT_PURPLE + "",
						ChatColor.AQUA + "" + Coins.getPlayerCoins(player) + " coins");
				return;
			}
		} else {
			if (!scores.contains(ChatColor.AQUA + "" + wins + "W")
					|| !scores.contains(ChatColor.AQUA + "" + loses + "L")
					|| !scores.contains(ChatColor.AQUA + "" + Coins.getPlayerCoins(player) + " coins")
					|| !scores.contains(alli.getName())) {
				resetScore();
				aScoreBoardManager.setScore(this, ChatColor.GREEN + "Alliance:", 15, sideBar, ChatColor.BLACK + "",
						alli.getName());
				aScoreBoardManager.setScore(this, ChatColor.GREEN + "Wins:", 12, sideBar, ChatColor.YELLOW + "",
						ChatColor.AQUA + "" + wins + "W");
				aScoreBoardManager.setScore(this, ChatColor.GREEN + "Loses:", 9, sideBar, ChatColor.DARK_PURPLE + "",
						ChatColor.AQUA + "" + loses + "L");
				aScoreBoardManager.setScore(this, ChatColor.GREEN + "Coins:", 6, sideBar, ChatColor.LIGHT_PURPLE + "",
						ChatColor.AQUA + "" + Coins.getPlayerCoins(player) + " coins");
				return;
			}
		}  
		return;
	}
	public void setPlayerPVEArenaScoreboard(){
		if (sideBar == null) {
			sideBar = scoreboard.registerNewObjective("dummy", "test");
			sideBar.setDisplaySlot(DisplaySlot.SIDEBAR);
		}
		sideBar.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "PVE Arena" + ChatColor.GOLD + "  ===");
		if(!GroupManager.hasGroup(player)){
			return;
		}
		Group group = GroupManager.getGroup(player);
		if(!ArenaManager.hasArena(group)){
			return;
		}
		Arena arena = ArenaManager.getArena(group);
		aScoreBoardManager.setScore(this, ChatColor.GOLD + "Players alive: " + arena.playerAlive.size(), 24, sideBar, " ", null);
		for(Player player : arena.playerAlive){
			if(player.getHealth() <= player.getMaxHealth() / 4){
				aScoreBoardManager.setScore(this, ChatColor.RED + player.getName() + ChatColor.GRAY + " (" + (int)player.getHealth() + "/" + (int)player.getMaxHealth() + ")", 22, sideBar, " ", null);
			}else{
				aScoreBoardManager.setScore(this, ChatColor.GREEN + player.getName() + ChatColor.GRAY + " (" + (int)player.getHealth() + "/" + (int)player.getMaxHealth() + ")", 22, sideBar, " ", null);
			}
		}
		
		aScoreBoardManager.setScore(this, ChatColor.RED + "Enemy's alive: " + arena.alive.size(), 20, sideBar, "  ", null);
		HashMap<String, Integer> mobs = new HashMap<String,Integer>();
		for(LivingEntity le : arena.alive){
			if(mobs.containsKey(le.getCustomName())){
				mobs.replace(le.getCustomName(), mobs.get(le.getCustomName() + 1));
				continue;
			}
			mobs.put(le.getCustomName(), 1);
		}
		for(Entry<String,Integer> entry : mobs.entrySet()){
			aScoreBoardManager.setScore(this, ChatColor.RED + entry.getKey() + " " + entry.getValue(), 20, sideBar, "  ", null);
		}
	}
	
	public void updatePlayerPVEArenaScoreboard(){
		if (sideBar == null) {
			sideBar = scoreboard.registerNewObjective("dummy", "test");
			sideBar.setDisplaySlot(DisplaySlot.SIDEBAR);
		}
		sideBar.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "PVE Arena" + ChatColor.GOLD + "  ===");
		if(!GroupManager.hasGroup(player)){
			return;
		}
		Group group = GroupManager.getGroup(player);
		if(!ArenaManager.hasArena(group)){
			return;
		}
		Arena arena = ArenaManager.getArena(group);
		aScoreBoardManager.updateScore(this, ChatColor.GOLD + "Players alive: " + arena.playerAlive.size(), 24, sideBar, " ", null);
		for(Player player : arena.playerAlive){
			if(player.getHealth() <= player.getMaxHealth() / 4){
				aScoreBoardManager.updateScore(this, ChatColor.RED + player.getName() + ChatColor.GRAY + " (" + (int)player.getHealth() + "/" + (int)player.getMaxHealth() + ")", 22, sideBar, " ", null);
			}else{
				aScoreBoardManager.updateScore(this, ChatColor.GREEN + player.getName() + ChatColor.GRAY + " (" + (int)player.getHealth() + "/" + (int)player.getMaxHealth() + ")", 22, sideBar, " ", null);
			}
		}
		
		aScoreBoardManager.updateScore(this, ChatColor.RED + "Enemy's alive: " + arena.alive.size(), 20, sideBar, "  ", null);
		HashMap<String, Integer> mobs = new HashMap<String,Integer>();
		for(LivingEntity le : arena.alive){
			if(mobs.containsKey(le.getCustomName())){
				mobs.replace(le.getCustomName(), mobs.get(le.getCustomName()) + 1);
				continue;
			}
			mobs.put(le.getCustomName(), 1);
		}
		for(Entry<String,Integer> entry : mobs.entrySet()){
			aScoreBoardManager.updateScore(this, ChatColor.RED + entry.getKey(), 20, sideBar, "  ", null);
		}
	}
	
	public void setPlayerPVELobbyScoreboard(){
		if (sideBar == null) {
			sideBar = scoreboard.registerNewObjective("dummy", "test");
			sideBar.setDisplaySlot(DisplaySlot.SIDEBAR);
		}
		sideBar.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "PVE Lobby" + ChatColor.GOLD + "  ===");
		if(!GroupManager.hasGroup(player)){
			return;
		}
		Group group = GroupManager.getGroup(player);
		aScoreBoardManager.setScore(this, ChatColor.GOLD + "Coins: " + ChatColor.AQUA + "" + Coins.getPlayerCoins(player) + " coins", 36, sideBar,"", null);
		aScoreBoardManager.setScore(this, ChatColor.GOLD + "Score: " + ChatColor.AQUA + "WIP", 33, sideBar, ChatColor.AQUA + "", null);
		aScoreBoardManager.setScore(this, ChatColor.GOLD + "Groups in the queue: " + PVE.main.queue.size(), 30, sideBar, ChatColor.BLACK + " ", null);
		
		aScoreBoardManager.setScore(this, ChatColor.GREEN + "Coins on win: " + group.settings.getCoinReward(), 30, sideBar, ChatColor.BLACK + " ", null);
		
		aScoreBoardManager.setScore(this, ChatColor.RED + "Coins on lose: " + (int) (group.settings.getCoinReward() * 1.5), 27, sideBar, ChatColor.BLACK + " ", null);
		
		for(Entry<Integer, Integer> zombie : group.settings.zombies.entrySet()){
			if(zombie.getValue() != 0){
				aScoreBoardManager.setScore(this, ChatColor.DARK_GREEN + "Zombie LV" + zombie.getKey() + ": " + zombie.getValue(), 24, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> skeleton : group.settings.skeletons.entrySet()){
			if(skeleton.getValue() != 0){
				aScoreBoardManager.setScore(this, ChatColor.GRAY + "Skeleton LV" + skeleton.getKey() + ": " + skeleton.getValue(), 21, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> spider : group.settings.spiders.entrySet()){
			if(spider.getValue() != 0){
				aScoreBoardManager.setScore(this, ChatColor.DARK_RED + "Spider LV" + spider.getKey() + ": " + spider.getValue(), 18, sideBar, ChatColor.AQUA + "",null);
			}
		}
		for(Entry<Integer, Integer> creeper : group.settings.creepers.entrySet()){
			if(creeper.getValue() != 0){
				aScoreBoardManager.setScore(this, ChatColor.GREEN + "Creeper LV" + creeper.getKey() + ": " + creeper.getValue(), 15, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> zombie_pigman : group.settings.zombie_pigmans.entrySet()){
			if(zombie_pigman.getValue() != 0){
				aScoreBoardManager.setScore(this, ChatColor.RED + "Zombie Pigman LV" + zombie_pigman.getKey() + ": " + zombie_pigman.getValue(), 12, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> blaze : group.settings.blazes.entrySet()){
			if(blaze.getValue() != 0){
				aScoreBoardManager.setScore(this, ChatColor.GOLD + "Blaze LV" + blaze.getKey() + ": " + blaze.getValue(), 9, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> wither_skeleton : group.settings.wither_skeletons.entrySet()){
			if(wither_skeleton.getValue() != 0){
				aScoreBoardManager.setScore(this, ChatColor.DARK_GRAY + "Wither Skeleton LV" + wither_skeleton.getKey() + ": " + wither_skeleton.getValue(), 6, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> enderman : group.settings.endermans.entrySet()){
			if(enderman.getValue() != 0){
				aScoreBoardManager.setScore(this, ChatColor.BLACK + "Enderman LV" + enderman.getKey() + ": " + enderman.getValue(), 3, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> wither : group.settings.withers.entrySet()){
			if(wither.getValue() != 0){
				aScoreBoardManager.setScore(this, ChatColor.BLACK + "" + ChatColor.BOLD + "Wither LV" + wither.getKey() + ": " + wither.getValue(), 0, sideBar, ChatColor.AQUA + "", null);
			}
		}
		return;
	}
	
	public void updatePlayerPVELobbyScoreboard(){
		if (sideBar == null) {
			sideBar = scoreboard.registerNewObjective("dummy", "test");
			sideBar.setDisplaySlot(DisplaySlot.SIDEBAR);
		}
		sideBar.setDisplayName(ChatColor.GOLD + "===  " + ChatColor.GOLD + "" + ChatColor.BOLD + "PVE Lobby" + ChatColor.GOLD + "  ===");
		aScoreBoardManager.updateScore(this, ChatColor.GOLD + "Coins:", 36, sideBar, ChatColor.AQUA + "", ChatColor.AQUA + "" + Coins.getPlayerCoins(player) + " coins");
		aScoreBoardManager.updateScore(this, ChatColor.GOLD + "Score:", 33, sideBar, ChatColor.BLACK + "", ChatColor.AQUA + "WIP");
		
		aScoreBoardManager.updateScore(this, ChatColor.GREEN + "Coins on win: WIP", 30, sideBar, ChatColor.BLACK + " ", null);
		
		aScoreBoardManager.updateScore(this, ChatColor.RED + "Coins on lose: WIP", 27, sideBar, ChatColor.BLACK + " ", null);
		if(!GroupManager.hasGroup(player)){
			return;
		}
		Group group = GroupManager.getGroup(player);
		for(Entry<Integer, Integer> zombie : group.settings.zombies.entrySet()){
			if(zombie.getValue() != 0){
				aScoreBoardManager.updateScore(this, ChatColor.DARK_GREEN + "Zombie LV" + zombie.getKey() + ": " + zombie.getValue(), 24, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> skeleton : group.settings.skeletons.entrySet()){
			if(skeleton.getValue() != 0){
				aScoreBoardManager.updateScore(this, ChatColor.GRAY + "Skeleton LV" + skeleton.getKey() + ": " + skeleton.getValue(), 21, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> spider : group.settings.spiders.entrySet()){
			if(spider.getValue() != 0){
				aScoreBoardManager.updateScore(this, ChatColor.DARK_RED + "Spider LV" + spider.getKey() + ": " + spider.getValue(), 18, sideBar, ChatColor.AQUA + "",null);
			}
		}
		for(Entry<Integer, Integer> creeper : group.settings.creepers.entrySet()){
			if(creeper.getValue() != 0){
				aScoreBoardManager.updateScore(this, ChatColor.GREEN + "Creeper LV" + creeper.getKey() + ": " + creeper.getValue(), 15, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> zombie_pigman : group.settings.zombie_pigmans.entrySet()){
			if(zombie_pigman.getValue() != 0){
				aScoreBoardManager.updateScore(this, ChatColor.RED + "Zombie Pigman LV" + zombie_pigman.getKey() + ": " + zombie_pigman.getValue(), 12, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> blaze : group.settings.blazes.entrySet()){
			if(blaze.getValue() != 0){
				aScoreBoardManager.updateScore(this, ChatColor.GOLD + "Blaze LV" + blaze.getKey() + ": " + blaze.getValue(), 9, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> wither_skeleton : group.settings.wither_skeletons.entrySet()){
			if(wither_skeleton.getValue() != 0){
				aScoreBoardManager.updateScore(this, ChatColor.DARK_GRAY + "Wither Skeleton LV" + wither_skeleton.getKey() + ": " + wither_skeleton.getValue(), 6, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> enderman : group.settings.endermans.entrySet()){
			if(enderman.getValue() != 0){
				aScoreBoardManager.updateScore(this, ChatColor.BLACK + "Enderman LV" + enderman.getKey() + ": " + enderman.getValue(), 3, sideBar, ChatColor.AQUA + "", null);
			}
		}
		for(Entry<Integer, Integer> wither : group.settings.withers.entrySet()){
			if(wither.getValue() != 0){
				aScoreBoardManager.updateScore(this, ChatColor.BLACK + "" + ChatColor.BOLD + "Wither LV" + wither.getKey() + ": " + wither.getValue(), 0, sideBar, ChatColor.AQUA + "", null);
			}
		}
		return;
	}
	
	public void addMana(double mana){
		if(this.mana >= 20) return;
		this.mana = this.mana + mana;
 	    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9&l+" + mana + " MANA"));
		SoundManager.playSoundToPlayer(Sound.WOOD_CLICK, player);
	}
	
	public boolean removeMana(double mana){
		if(this.mana - mana < 0){
        MessageManager.sendMessage(this.player, "&cYou don't have enough mana");
        return false;
		}
		this.mana = this.mana - mana;
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l-" + mana + " MANA"));
	    SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
		return true;
	}
	
	public void showMana(){
		int manatoint = (int) this.mana;
		String brachetleft = "&8[";
		String brachetright = "&8]";
		String mana = "";
		for(int a = 0; a < manatoint;a++){
			mana = mana + "&9|";
		}
		for(int a = 0; a < 20 - manatoint; a++){
			mana = mana + "&8|";
		}
		mana = brachetleft + mana + brachetright;
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', mana));
	}
	
	public String getManaStatus(){
		int manatoint = (int) this.mana;
		String brachetleft = "&8[";
		String brachetright = "&8]";
		String mana = "";
		for(int a = 0; a < manatoint;a++){
			mana = mana + "&9|";
		}
		for(int a = 0; a < 20 - manatoint; a++){
			mana = mana + "&8|";
		}
		mana = ChatColor.translateAlternateColorCodes('&', brachetleft + mana + brachetright);
		return mana;
	}
	
	public void updateManaStatus(){	
		for(Player player: Bukkit.getOnlinePlayers()){
			player.setDisplayName( "lol");

		}
	}
}

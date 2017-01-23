package me.Haeseke1.Alliances.Arena;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Haeseke1.Alliances.APlayer.APlayerManager;
import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;
import net.md_5.bungee.api.ChatColor;

public class Countdown extends BukkitRunnable{
    
	private int time;
	private Arena arena;
	private FileConfiguration arenaConfig = Main.arenaConfig;
	
	public Countdown(String arenaname){
	 	this.arena = ArenaManager.getArenaByName(arenaname.toLowerCase());
	    this.time = arena.getCountdown();
	}
	
	@Override
	public void run() {
	 try {
		if(TeamManager.checkTeams(arena.getName())){
		    if(this.time == arena.getCountdown()){
		    	try {
					ArenaManager.setStatus(arena.getName(), "COUNTING", null);
				} catch (EmptyLocationException e) {
					e.printStackTrace();
				}
			  try {
				  if(ArenaManager.checkSign(arena.getName())){
					  ArenaManager.updateSign(ArenaManager.getSign(arena.getName()), arena);
					  }
			} catch (EmptyLocationException e) {
				e.printStackTrace();
			} catch (EmptyStringException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
		    if(time == 0){
		    	String arenaname = this.arena.getName();
		    	try {
					ArenaManager.setStatus(arenaname, "PLAYING", null);
				} catch (EmptyLocationException e) {
					e.printStackTrace();
				}
			  try {
				  if(ArenaManager.checkSign(arenaname)){
					  ArenaManager.updateSign(ArenaManager.getSign(arenaname), arena);
					  }
			} catch (EmptyLocationException | EmptyStringException e) {
				e.printStackTrace();
			}
		    }
		    for(UUID playerUUID: arena.getPlayersInArena().keySet()){
		    	Player player = Bukkit.getPlayer(playerUUID);
		    	switch(time){
		    	case 60:
		    		SoundManager.playSoundToPlayer(Sound.NOTE_PLING, player);
		    		MessageManager.sendMessage(player, ChatColor.AQUA + "The fight will start in " + ChatColor.GOLD + "1" + ChatColor.AQUA + " minute");
		    		break; 
		    	case 30:
		    		SoundManager.playSoundToPlayer(Sound.NOTE_PLING, player);
		    		MessageManager.sendMessage(player, ChatColor.AQUA + "The fight will start in " + ChatColor.GOLD + "30" + ChatColor.AQUA + " seconds");
		    		break; 
		    	case 15:
		    		SoundManager.playSoundToPlayer(Sound.NOTE_PLING, player);
		    		MessageManager.sendMessage(player, ChatColor.AQUA + "The fight will start in " + ChatColor.GOLD + "15" + ChatColor.AQUA + " seconds");
		    		break;
		    	case 10:
		    		SoundManager.playSoundToPlayer(Sound.NOTE_PLING, player);
		    		MessageManager.sendMessage(player, ChatColor.AQUA + "The fight will start in " + ChatColor.GOLD + "10" + ChatColor.AQUA + " seconds");
		            break;
		    	}
		    	if(time <= 5 && time > 1){
		    		SoundManager.playSoundToPlayer(Sound.WOOD_CLICK, player);
		    		MessageManager.sendMessage(player, ChatColor.AQUA + "The fight will start in " + ChatColor.GOLD + time + ChatColor.AQUA + " seconds");
		    	}
		    	
		    	if(time == 1){
		    		SoundManager.playSoundToPlayer(Sound.WOOD_CLICK, player);
		    		MessageManager.sendMessage(player, ChatColor.AQUA + "The fight will start in " + ChatColor.GOLD + "1" + ChatColor.AQUA + " second");
		    	}
		    	
		    	if(time == 0){
		    		SoundManager.playSoundToPlayer(Sound.ANVIL_LAND, player);
		    		MessageManager.sendMessage(player, ChatColor.AQUA + "Teleporting players...");  	
		    	}
		    	if(time == -1){
		    	    try {
						ArenaManager.spawnPlayers(this.arena.getName());
					} catch (EmptyLocationException e) {
						Bukkit.broadcastMessage("error");
						this.cancel();
						e.printStackTrace();
					}
		    	  }
		    	}
		    time --;
		  }else if(!ArenaManager.checkStatus(arena.getName(), "COUNTING")){
			  for(UUID playerUUID: arena.getPlayersInArena().keySet()){
				  Player player = Bukkit.getPlayer(playerUUID);
				  APlayerManager.getAPlayer(player).addWin();
				  MessageManager.sendMessage(player, ChatColor.GREEN + "Your team won the game " + ChatColor.GOLD + "(+" + Main.settingsConfig.getInt("Arena_win_player_reward") + " coins)");
				  Coins.addPlayerCoins(player, Main.settingsConfig.getInt("Arena_win_player_reward"));
				  Alliance al = AllianceManager.getAlliance(player);
				  al.sendPlayersMessage(ChatColor.GREEN + "Your alliance won an arena fight! " + ChatColor.GOLD + "(+1 win)", player);
				  al.addWin();
				  try {
					  if(ArenaManager.checkSign(arena.getName())){
						  ArenaManager.updateSign(ArenaManager.getSign(arena.getName()), arena);
						  }
					} catch (EmptyLocationException | EmptyStringException e) {
						e.printStackTrace();
					}
			    }
		      WinnerCountdown wc = new WinnerCountdown(arena);
		      wc.runTaskTimer(Main.plugin, 0L, 10L);
			  arenaConfig.set("Arenas." + arena.getName().toLowerCase() + ".spawns.team1.alliance", "");
			  arenaConfig.set("Arenas." + arena.getName().toLowerCase() + ".spawns.team2.alliance", "");
			  Bukkit.getScheduler().cancelTask(this.getTaskId());
		  }else{
			  ArenaManager.setStatus(arena.getName(), "PLAYABLE", null);
			  this.cancel();
		  }
	} catch (IndexOutOfBoundsException | IllegalStateException | EmptyStringException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (EmptyLocationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}

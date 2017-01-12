package me.Haeseke1.Alliances.Arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Arena_Sign {
	
	public static List<Arena_Sign> arena_signs = new ArrayList<Arena_Sign>();
	public static List<Sign> signs = new ArrayList<Sign>();
	
	public Sign sign;
	public Fight_Type type;
	public Arena arena;
	public int Coins;
	
	public boolean playing = false;
	
	public List<Player> players = new ArrayList<Player>();
	
	public Arena_Sign(Sign sign, Fight_Type type, int Coins) {
		this.sign = sign;
		this.type = type;
		this.Coins = Coins;
		sign.setLine(0, ChatColor.AQUA + "Arena");
		sign.setLine(1, ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "1V1");
		sign.setLine(2, ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "Pay: " + Coins + " coins");
		sign.setLine(3, ChatColor.DARK_PURPLE + "0 in queue");
		sign.update();
		arena_signs.add(this);
		signs.add(sign);
	}
	
	public boolean addPlayer(Player player){
		switch(type){
		case ONE_V_ONE:
			if(players.size() == 2  && playing){
				return false;
			}
			players.add(player);
			if(players.size() == 2){
				if(ArenaManager.isArena()){
					arena = ArenaManager.getArena();
					ArenaManager.One_V_One(players.get(0), players.get(1), Coins * players.size(), arena);
					sign.setLine(3, ChatColor.RED + "Playing...");
					sign.update();
					playing = true;
					return true;
				}else{
					sendMessage(ChatColor.DARK_RED + "Waiting for a arena!");
				}
			}
			break;
		default:
			break;
		}
		sign.setLine(3, ChatColor.DARK_PURPLE + "" + players.size() + " in queue");
		sign.update();
		return true;
	}
	
	public boolean removePlayer(Player player){
		if(players.contains(player) && !playing){
			players.remove(player);
			sign.setLine(3, ChatColor.DARK_PURPLE + "" + players.size() + " in queue");
			sign.update();
			return true;
		}
		return false;
	}
	
	public void sendMessage(String message){
		for(Player player : players){
			player.sendMessage(message);
		}
	}
	
	public boolean isInQueue(Player player){
		return players.contains(player);
	}
	
	public void update(){
		if(playing && !arena.inGame){
			playing = false;
			players.clear();
			arena = null;
			sign.setLine(3, ChatColor.DARK_PURPLE + "0 in queue");
			sign.update();
		}
		
		switch(type){
		case ONE_V_ONE:
			if(players.size() == 2){
				if(ArenaManager.isArena() && !playing){
					arena = ArenaManager.getArena();
					ArenaManager.One_V_One(players.get(0), players.get(1), Coins * players.size(), arena);
					sign.setLine(3, ChatColor.RED + "Playing...");
					playing = true;
					sign.update();
					return;
				}
			}
			break;
		default:
			break;
		}
	}
	
}

package me.Haeseke1.Alliances.Arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Economy.Coins;
import net.md_5.bungee.api.ChatColor;

public class Arena {
	
	
	public static List<Arena> arenas = new ArrayList<Arena>();
	
	public String name;
	
	public World world;
	public int xmin;
	public int xmax;
	public int zmin;
	public int zmax;
	
	public Location spawn1;
	public Location spawn2;
	public Location waitRoom;
	
	public boolean inGame = false;
	
	public boolean isStarting = false;
	public int time = 10;
	
	public int coins;
	
	public String nameG1;
	public String nameG2;
	
	public List<Player> group1 = new ArrayList<Player>();
	public List<Player> group2 = new ArrayList<Player>();
	
	public List<Player> aliveGroup1 = new ArrayList<Player>();
	public List<Player> aliveGroup2 = new ArrayList<Player>();
	

	public boolean isFighting = false;
	
	
	public ItemStack createItem(){
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		item.setItemMeta(im);
		return item;
	}
	
	
	
	public Arena(String name, Location loc1, Location loc2, Location waitRoom, Location spawn1, Location spawn2){
		this.name = name;
		this.waitRoom = waitRoom;
		this.spawn1 = spawn1;
		this.spawn2 = spawn2;
		this.world = loc1.getWorld();
		if(loc1.getBlockX() > loc2.getBlockX()){
			xmin = loc2.getBlockX();
			xmax = loc1.getBlockX();
		}else{
			xmin = loc1.getBlockX();
			xmax = loc2.getBlockX();
		}
		if(loc1.getBlockZ() > loc2.getBlockZ()){
			zmin = loc2.getBlockZ();
			zmax = loc1.getBlockZ();
		}else{
			zmin = loc1.getBlockZ();
			zmax = loc2.getBlockZ();
		}
		arenas.add(this);
	}
	
	public Arena(String name, int xmin, int xmax, int zmin, int zmax, World world, Location waitRoom, Location spawn1, Location spawn2){
		this.name = name;
		this.world = world;
		this.xmin = xmin;
		this.xmax = xmax;
		this.zmin = zmin;
		this.zmax = zmax;
		this.waitRoom = waitRoom;
		this.spawn1 = spawn1;
		this.spawn2 = spawn2;
		arenas.add(this);
	}
	
	public boolean isInGame(){
		return inGame;
	}
	
	
	public void sendAllMessage(String message){
		for(Player player : group1){
			player.sendMessage(message);
		}
		for(Player player : group2){
			player.sendMessage(message);
		}
	}
	
	public void sendGroup1Message(String message){
		for(Player player : group1){
			player.sendMessage(message);
		}
	}
	
	public void sendGroup2Message(String message){
		for(Player player : group2){
			player.sendMessage(message);
		}
	}
	
	public void startGame(){
		aliveGroup1 = group1;
		aliveGroup2 = group2;
		isFighting = true;
		for(Player player : aliveGroup1){
			player.teleport(spawn1);
		}
		for(Player player : aliveGroup2){
			player.teleport(spawn2);
		}
		sendAllMessage(ChatColor.GREEN + "The match has started!");
	}
	
	public void endGame(){
		isFighting = false;
		if(aliveGroup1.isEmpty()){
			for(Player player : group2){
				Coins.addPlayerCoins(player, coins / group2.size());
			}
			sendAllMessage(ChatColor.BOLD + "" + ChatColor.GOLD + nameG2 + " has won the fight and is/are rewarded with " + coins + " coins!");
		}else{
			for(Player player : group1){
				Coins.addPlayerCoins(player, coins / group1.size());
			}
			sendAllMessage(ChatColor.BOLD + "" + ChatColor.GOLD + nameG1 + " has won the fight and is/are rewarded with " + coins + " coins!");
		}
		for(Player player : group2){
			player.teleport(player.getLocation().getWorld().getSpawnLocation());
		}
		for(Player player : group1){
			player.teleport(player.getLocation().getWorld().getSpawnLocation());
		}
		group1.clear();
		group2.clear();
		aliveGroup1.clear();
		aliveGroup2.clear();
	}
	
	
	
	
	
}

package me.Haeseke1.Alliances.Outpost.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Outpost.Timer;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class God {
	
	public static HashMap<ItemStack, Integer> rewards = new HashMap<ItemStack,Integer>();
	
	public static List<God> gods = new ArrayList<God>();
	
	public List<Player> inOutpost = new ArrayList<Player>();
	
	public Alliance taking_over;
	public int time_taking_over = 0;
	public int time_Contested = 0;
	
	public String name;
	
	public int xmin;
	public int zmin;
	public int xmax;
	public int zmax;
	public World world;
	
	public Alliance owner;
	
	public God(String name, Location loc1, Location loc2, Alliance owner) {
		this.name = name;
		this.owner = owner;
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
		gods.add(this);
	}
	
	public static ItemStack createItem(){
		ItemStack i = new ItemStack(Material.GOLDEN_APPLE, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "God");
		i.setItemMeta(im);
		return i;
	}
	
	public God(String name, World world, int xmin, int xmax, int zmin, int zmax, Alliance alli) {
		this.name = name;
		this.world = world;
		this.xmin = xmin;
		this.xmax = xmax;
		this.zmin = zmin;
		this.zmax = zmax;
		this.owner = alli;
		gods.add(this);
	}
	
	public void take_over(){
		boolean contest = false;
		boolean isTakingOver = false;
		for(Player player : inOutpost){
			if(AllianceManager.playerIsInAlli(player)){
				if(taking_over != null){
					if(AllianceManager.getAlliance(player).getMembers().containsKey(player.getUniqueId()) && !AllianceManager.getAlliance(player).equals(taking_over)){
						contest = true;
					}
					if(AllianceManager.getAlliance(player).equals(taking_over)){
						isTakingOver = true;
					}
				}else{
					if(owner == null || !owner.getMembers().containsKey(player.getUniqueId())){
						taking_over = AllianceManager.getAlliance(player);
						time_taking_over = 0;
						isTakingOver = true;
					}
				}
			}else{
				contest = true;
			}
		}
		if(contest){
			if(time_Contested == 10){
				if(taking_over != null){
					taking_over.sendPlayersAlertMessage("The outpost " + name + " is contested!");
				}
				time_Contested = -1;
			}
			time_Contested++;
			return;
		}
		if(!isTakingOver && taking_over != null){
			taking_over.sendPlayersAlertMessage("You are not taking over " + name + " anymore!");
			taking_over = null;
			if(owner != null){
				owner.sendPlayersRemarkMessage("Outpost " + name + " is safe!");
			}
			return;
		}
		if(taking_over != null){
			int n1 = Timer.take_overTime/4;
			int n2 = Timer.take_overTime/4*2;
			int n3 = Timer.take_overTime/4*3;
			int n4 = Timer.take_overTime;

			if(time_taking_over == 0){
				MessageManager.sendInfoBroadcast(taking_over.getName() + " is taking over a outpost called " + name + " (0%)");
				if(owner != null){
					owner.sendPlayersAlertMessage("You are losing your outpost called " + name + "!"); 
				}
			}
			if(time_taking_over == n1){
				MessageManager.sendInfoBroadcast(taking_over.getName() + " is taking over a outpost called " + name + " (25%)");
				if(owner != null){
					owner.sendPlayersAlertMessage("You are losing your outpost called " + name + "!"); 
				}
			}
			if(time_taking_over == n2){
				MessageManager.sendInfoBroadcast(taking_over.getName() + " is taking over a outpost called " + name + " (50%)");
				if(owner != null){
					owner.sendPlayersAlertMessage("You are losing your outpost called " + name + "!"); 
				}
			}
			if(time_taking_over == n3){
				MessageManager.sendInfoBroadcast(taking_over.getName() + " is taking over a outpost called " + name + " (75%)");
				if(owner != null){
					owner.sendPlayersAlertMessage("You are losing your outpost called " + name + "!"); 
				}
			}
			if(time_taking_over == n4){
				MessageManager.sendInfoBroadcast(taking_over.getName() + " has taken over a outpost called " + name + " (100%)");
				if(owner != null){
					owner.sendPlayersAlertMessage("You lost outpost " + name + "!"); 
				}
				owner = taking_over;
				taking_over = null;
			}
			if(isTakingOver){
				time_taking_over++;
			}
		}	
	}
}

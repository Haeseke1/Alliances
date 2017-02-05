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

public class Mob_Farm {
	
	
	public static HashMap<ItemStack, Integer> rewards = new HashMap<ItemStack,Integer>();
	
	public static List<Mob_Farm> mob_farms = new ArrayList<Mob_Farm>();
	
	public List<Player> inOutpost = new ArrayList<Player>();
	
	public Alliance taking_over;
	public int time_taking_over = 0;
	public int time_Contested = 0;
	
	public String name;
	public String nameWithColorCodes;
	
	public int xmin;
	public int zmin;
	public int xmax;
	public int zmax;
	public World world;
	
	public Alliance owner;
	
	public Mob_Farm(String name, Location loc1, Location loc2, Alliance owner) {
		this.nameWithColorCodes = name;
		this.name = ChatColor.translateAlternateColorCodes('&', name);
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
		mob_farms.add(this);
	}
	
	public Mob_Farm(String name, World world, int xmin, int xmax, int zmin, int zmax, Alliance alli) {
		this.nameWithColorCodes = name;
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.world = world;
		this.xmin = xmin;
		this.xmax = xmax;
		this.zmin = zmin;
		this.zmax = zmax;
		this.owner = alli;
		mob_farms.add(this);
	}
	
	public static ItemStack createItem(){
		ItemStack i = new ItemStack(Material.BONE, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Mob Farm");
		i.setItemMeta(im);
		return i;
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
			if(time_Contested == 5){
				if(taking_over != null){
					String message = "&bThis outpost is now contested!";
					message = message.replace("%outpost_name%", name);
					taking_over.sendPlayersMessage(message);
					if(owner != null){
						owner.sendPlayersMessage(message);
					}
				}
				time_Contested = -1;
			}
			time_Contested++;
			return;
		}
		if(!isTakingOver && taking_over != null){
			String message = "&lThis outpost is now neutral";
			message = message.replace("%outpost_name%", name);
			taking_over.sendPlayersMessage(message);
			taking_over = null;
			if(owner != null){
				message = "&lThis outpost is now safe";
				message = message.replace("%outpost_name%", name);
				owner.sendPlayersMessage(message);
			}
			return;
		}
		if(taking_over != null){
			int n1 = Timer.take_overTime/4;
			int n2 = Timer.take_overTime/4*2;
			int n3 = Timer.take_overTime/4*3;
			int n4 = Timer.take_overTime;

			if(time_taking_over == 0){
				String message = "&6" + name + "&c is under attack by an alliance! (&60%&c)";
				MessageManager.sendBroadcast(message);
				if(owner != null){
					message = "&cYou're losing an outpost!";
					owner.sendPlayersMessage(message); 
				}
			}
			if(time_taking_over == n1){
				String message = "&6" + name + "&c is under attack by an alliance! (&625%&c)";
				MessageManager.sendBroadcast(message);
				if(owner != null){
					message = "&cYou're losing an outpost!";
					owner.sendPlayersMessage(message);  
				}
			}
			if(time_taking_over == n2){
				String message = "&6" + name + "&c is under attack by an alliance! (&650%&c)";
				MessageManager.sendBroadcast(message);
				if(owner != null){
					message = "&cYou're losing an outpost!";
					owner.sendPlayersMessage(message); 
				}
			}
			if(time_taking_over == n3){
				String message = "&6" + name + "&c is under attack by an alliance! (&675%&c)";
				MessageManager.sendBroadcast(message);
				if(owner != null){
					message = "&cYou're losing an outpost!";
					owner.sendPlayersMessage(message); 
				}
			}
			if(time_taking_over == n4){
				String message = "&6" + name + "&c is under attack by an alliance! (&6100%&c)";
				MessageManager.sendBroadcast(message);
				if(owner != null){
					message = "&cYou're losing an outpost!";
					owner.sendPlayersMessage(message);  
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

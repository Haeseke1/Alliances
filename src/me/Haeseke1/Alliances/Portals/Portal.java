package me.Haeseke1.Alliances.Portals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;

public class Portal {
	
	public static List<Portal> portals = new ArrayList<>();
	
	public Location corner1;
	public Location corner2;
	public Location warp;
	
	public String name;
	
	public Portal(String name,Location corner1,Location corner2,Location warp){
		this.corner1 = corner1;
		this.corner2 = corner2;
		this.name = name;
		this.warp = warp;
		portals.add(this);
	}

	public Portal(String name, Player player){
		this.name = name;
		if(!regionSelect.hasRegion(player)){
			MessageManager.sendMessage(player, "&cYou need to select a region first");
			SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
			return;
		}
		if(portalAlreadyExists(this.name)){
			MessageManager.sendMessage(player, "&cThis portal does already exists");
			SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
			return;
		}
		this.corner1 = regionSelect.getRegion(player, "left");
		this.corner2 = regionSelect.getRegion(player, "right");
		portals.add(this);
		MessageManager.sendMessage(player, "&2Successfully created a portal with the name &6" + name);
	}
	
	public void setLocation(Player player){
		this.warp = player.getLocation();
		MessageManager.sendMessage(player, "&2You've set the warp location of &6" + this.name + " &2to (&6" + (int) player.getLocation().getX() + "&2,&6" + (int) player.getLocation().getY() + "&2,&6" + (int) player.getLocation().getZ() + "&2)");
	}
	
	public void teleportPlayer(Player player){
		if(warp == null){
			SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
			MessageManager.sendMessage(player, "&cThis portal isn't connected to a warp");
			return;
		}
		MessageManager.sendMessage(player, "&bTeleporting...");
		SoundManager.playSoundToPlayer(Sound.PORTAL_TRIGGER, player);
		player.teleport(this.warp, TeleportCause.ENDER_PEARL);
	}
	
	public boolean playerIsInPortal(Player player){
		
		Location playerloc = player.getLocation();
		int x = (int) playerloc.getX();
		int y = (int) playerloc.getY();
		int z = (int) playerloc.getZ();
		
		double x1 = corner1.getX();
		double x2 = corner2.getX();
	
		double z1 = corner1.getZ();
		double z2 = corner1.getZ();
		
		double y1 = corner1.getY();
		double y2 = corner2.getY();
		
		if(y1 != y && y2 != y){
		if(y1 < y2){
			if(!(y1 < y && y < y2)){
				return false;
			}
		}else{
			if(!(y1 > y && y > y2)){
				return false;
			}
		}
		}
		if(x1 != z && x2 != z){
		if(x1 < x2){
			if(!(x1 < x && x < x2)){
				return false;
			}
		}else{
			if(!(x1 > x && x > x2)){
				return false;
			}
		}
		}
		if(z1 != z && z2 != z){
		if(z1 < z2){
			if(!(z1 < z && z < z2)){
				return false;
			}
		}else{
			if(!((z1 >= z && z >= z2))){
				return false;
			}
		 }
	    }
		return true;
	}
	
	public static boolean portalAlreadyExists(String portalName){
		for(Portal p: Portal.portals){
			Bukkit.broadcastMessage(portalName + "," + p.name);
			if(p.name.equalsIgnoreCase(portalName)){
				return true;
			}
		}
		return false;
	}
	
	public static void savePortals(){
		if(Main.PortalsConfig.getConfigurationSection("").getKeys(false) != null){
		for(String portalname: Main.PortalsConfig.getConfigurationSection("").getKeys(false)){
			Main.PortalsConfig.set(portalname, null);
		}
		}
		for(Portal portal: Portal.portals){
			ConfigManager.setLocationFromConfig(Main.PortalsConfig, portal.name + ".corner1", portal.corner1);
			ConfigManager.setLocationFromConfig(Main.PortalsConfig, portal.name + ".corner2", portal.corner2);
			if(portal.warp != null){
			ConfigManager.setLocationFromConfig(Main.PortalsConfig, portal.name + ".warp", portal.warp);	
			}
		}
		ConfigManager.saveCustomConfig(new File(Main.plugin.getDataFolder(),"portals.yml"), Main.PortalsConfig);
		MessageManager.sendRemarkMessage("Portals are saved");
	}
	
	public static void loadPortals() throws EmptyLocationException{
		for(String name: Main.PortalsConfig.getKeys(false)){
			Location warp;
			Location corner1 = ConfigManager.getLocationFromConfig(Main.PortalsConfig, name + ".corner1");
			Location corner2 = ConfigManager.getLocationFromConfig(Main.PortalsConfig, name + ".corner2");
			if(Main.PortalsConfig.get(name + ".warp") != null){
			warp = ConfigManager.getLocationFromConfig(Main.PortalsConfig, name + ".warp");	
			}else{
			warp = null;
			}
			new Portal(name,corner1,corner2,warp);
		}
		MessageManager.sendRemarkMessage("Portals are loaded");
	}
	
	public static void removePortal(Player player,String name){
		if(!Portal.portalAlreadyExists(name)){
			MessageManager.sendMessage(player, "&cThis portal doesn't exists");
			SoundManager.playSoundToPlayer(Sound.NOTE_BASS, player);
			return;
		}
		Portal portal = Portal.getPortalByName(name);
        Portal.portals.remove(portal);
		MessageManager.sendMessage(player, "&2You've successfully deleted a portal");
		SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
	}
	
	public static Portal getPortalByName(String name){
		for(Portal portal: portals){
			if(portal.name.equalsIgnoreCase(name)){
				return portal;
			}
		}
		return null;
	}
}

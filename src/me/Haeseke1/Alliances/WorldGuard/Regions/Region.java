package me.Haeseke1.Alliances.WorldGuard.Regions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;
import net.md_5.bungee.api.ChatColor;

public class Region {
	
	public static List<Region> regions = new ArrayList<>();

	public String displayname;
	
	public Location corner1;
	public Location corner2;
	
	public HashMap<String,Boolean> settings = new HashMap<>();
	
	public Region(Player player,String name,Location corner1, Location corner2){
		this.displayname = ChatColor.translateAlternateColorCodes('&', name);
		if(player == null){
		this.corner1 = corner1;
		this.corner2 = corner2;
		}else{
		if(regionSelect.getRegion(player, "left") == null || regionSelect.getRegion(player, "right") == null){
			MessageManager.sendMessage(player, "&cYou need to select a region first");
		}
		this.corner1 = regionSelect.getRegion(player, "left");
		this.corner2 = regionSelect.getRegion(player, "right");
		}
		for(Setting setting: Setting.values()){
			this.settings.put(Setting.getName(setting), Settings.getDefaultSetting(setting));
		}
		if(regions.contains(this)) return;
		regions.add(this);
		if(player != null){
			MessageManager.sendMessage(player, "&2You've succesfully created a region " + this.displayname);
		}
	}
	
	public Region(Player player,int region_number){
		if(!Main.WorldGuardConfig.getConfigurationSection("Regions").contains("" + region_number)){
			if(player == null){
				MessageManager.sendAlertMessage("This region doesn't exists: &6" + region_number);
				return;
			}
			MessageManager.sendMessage(player,"&4This region doesn't exists: &6" + region_number);
			return;
		}
		try {
			this.displayname = ChatColor.translateAlternateColorCodes('&',ConfigManager.getStringFromConfig(Main.WorldGuardConfig,"Regions." + region_number + ".name"));
			this.corner1 = ConfigManager.getLocationFromConfig(Main.WorldGuardConfig,"Regions." + region_number + ".corner1");
			this.corner2 = ConfigManager.getLocationFromConfig(Main.WorldGuardConfig,"Regions." + region_number + ".corner2");
		} catch (EmptyLocationException e) {
			e.printStackTrace();
		} catch (EmptyStringException e) {
			e.printStackTrace();
		}
		for(Setting settingname: Setting.values()){
            if(Main.WorldGuardConfig.get("Regions." + region_number + ".settings." + settingname.name().toLowerCase().replace("_", "-")) == null){
            	this.resetSettings();
    			MessageManager.sendRemarkMessage("The " + this.displayname + " &2region has been resetted due a problem");
            	return;
            }
            this.settings.put(settingname.name().toLowerCase().replace("_", "-"), (Boolean) Main.WorldGuardConfig.get("Regions." + region_number + ".settings." + settingname.name().toLowerCase().replace("_", "-")));
		}
		if(player != null){
			MessageManager.sendRemarkMessage("The " + this.displayname + " &2region has been loaded");
		}
		if(regions.contains(this)) return;
		regions.add(this);
	}
	
	public static void saveRegions(){
	    FileConfiguration config;
	    config = Main.WorldGuardConfig;
	    config.set("Regions", null);
	    int count = 0;
	    for(Region region: regions){
	    	ConfigManager.setLocationFromConfig(config, "Regions." + count + ".corner1", region.corner1);
	    	ConfigManager.setLocationFromConfig(config, "Regions." + count + ".corner2", region.corner2);
	    	config.set("Regions." + count + ".name", region.displayname);
            for(String setting: region.settings.keySet()){
            	config.set("Regions." + count + ".settings." + setting, region.settings.get(setting));
            }
	    	count ++;
	    }
	    ConfigManager.saveCustomConfig(new File(Main.plugin.getDataFolder(),"worldguard.yml"), config);
	    MessageManager.sendRemarkMessage("The regions has been saved");
	}
	
	public static void loadRegions(){
	    FileConfiguration config;
	    config = Main.WorldGuardConfig;
	    if(config.get("Regions") != null){
	    for(String region_number: config.getConfigurationSection("Regions").getKeys(false)){
	    	new Region(null,Integer.valueOf(region_number));
	    }
	    }
	    MessageManager.sendRemarkMessage("The regions has been loaded");
	}
	
	public void setSetting(Player player,String settingname, boolean setting){
		for(Setting names: Setting.values()){
		if(names.name().equalsIgnoreCase(settingname.replace("-", "_").toUpperCase())){
		if(player == null){
		if(settingname.replace("-", "_") == null){
			MessageManager.sendAlertMessage("This setting doesn't exists");
			return;
		}
		if(Setting.valueOf(settingname.replace("-", "_").toUpperCase()) == null){
			MessageManager.sendAlertMessage("This setting doesn't exists");
			return;
		}
		this.settings.replace(settingname, setting);
		MessageManager.sendRemarkMessage("Set &6" + settingname + " &2to &6" + setting);
		return;
		}
		if(settingname.replace("-", "_") == null){
			MessageManager.sendMessage(player,"&cThis setting doesn't exists");
			return;
		}
		if(Setting.valueOf(settingname.replace("-", "_").toUpperCase()) == null){
			MessageManager.sendMessage(player,"&cThis setting doesn't exists");
			return;
		}
		this.settings.replace(settingname, setting);
		MessageManager.sendMessage(player,"&2Set &6" + settingname + " &2to &6" + setting);
		return;
		}
	}
		if(player != null){
		MessageManager.sendMessage(player,"&cThis setting doesn't exists");
		return;
		}
		MessageManager.sendAlertMessage("This setting doesn't exists");
	}
	
	public boolean getSetting(String settingname){
		if(Setting.valueOf(settingname.toUpperCase().replace("-", "_")) == null){
			MessageManager.sendAlertMessage("This setting doesn't exists");
			return false;
		}

		return this.settings.get(settingname);
	}
	
	public boolean isInRegion(Player player,Location loc){
		if(loc == null){
		if(corner1.getX() < corner2.getX()){
			if(!(corner1.getX() <= player.getLocation().getX() && player.getLocation().getX() <= corner2.getX())){
				return false;
			}
		}
		if(corner1.getX() > corner2.getX()){
			if(!(corner1.getX() >= player.getLocation().getX() && player.getLocation().getX() >= corner2.getX())){
				return false;
			}
		}
		if(corner1.getZ() < corner2.getZ()){
			if(!(corner1.getZ() <= player.getLocation().getZ() && player.getLocation().getZ() <= corner2.getZ())){
				return false;
			}
		}
		if(corner1.getZ() > corner2.getZ()){
			if(!(corner1.getZ() >= player.getLocation().getZ() && player.getLocation().getZ() >= corner2.getZ())){
				return false;
			}
		}
		if(corner1.getY() < corner2.getY()){
			if(!(corner1.getY() <= player.getLocation().getY() && player.getLocation().getY() <= corner2.getY())){
				return false;
			}
		}
		if(corner1.getY() > corner2.getY()){
			if(!(corner1.getY() >= player.getLocation().getY() && player.getLocation().getY() >= corner2.getY())){
				return false;
			}
		}
		return true;
		}
		if(corner1.getX() < corner2.getX()){
			if(!(corner1.getX() <= loc.getX() && loc.getX() <= corner2.getX())){
				return false;
			}
		}
		if(corner1.getX() > corner2.getX()){
			if(!(corner1.getX() >= loc.getX() && loc.getX() >= corner2.getX())){
				return false;
			}
		}
		if(corner1.getZ() < corner2.getZ()){
			if(!(corner1.getZ() <= loc.getZ() && loc.getZ() <= corner2.getZ())){
				return false;
			}
		}
		if(corner1.getZ() > corner2.getZ()){
			if(!(corner1.getZ() >= loc.getZ() && loc.getZ() >= corner2.getZ())){
				return false;
			}
		}
		if(corner1.getY() < corner2.getY()){
			if(!(corner1.getY() <= loc.getY() && loc.getY() <= corner2.getY())){
				return false;
			}
		}
		if(corner1.getY() > corner2.getY()){
			if(!(corner1.getY() >= loc.getY() && loc.getY() >= corner2.getY())){
				return false;
			}
		}
		return true;
	}
	
	public void resetSettings(){
		settings.clear();
		for(Setting setting: Setting.values()){
			this.settings.put(Setting.getName(setting), Settings.getDefaultSetting(setting));
		}
	}
	
	public static Region getRegionOfPlayer(Player player){
		for(Region region: Region.regions){
			if(region.isInRegion(player,null)){
				return region;
			}
		}
		return null;
	}
	
	public static Region getRegionOfLocation(Location loc){
		for(Region region: Region.regions){
			if(region.isInRegion(null,loc)){
				return region;
			}
		}
		return null;
	}
	
	public static Region getRegionByName(String name){
		for(Region region: Region.regions){
			if(ChatColor.stripColor(region.displayname).toLowerCase().equalsIgnoreCase(name)){
				return region;
			}
		}
		return null;
	}
	
}

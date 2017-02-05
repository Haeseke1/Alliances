package me.Haeseke1.Alliances.PVE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.Haeseke1.Alliances.APlayer.APlayerManager;
import me.Haeseke1.Alliances.APlayer.aPlayer;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Group {
	
	public static List<Group> groups = new ArrayList<Group>();
	
	public List<Player> members = new ArrayList<Player>();
	public Player owner;
	public Settings settings;
	
	public HashMap<Player, Location> memberLocations = new HashMap<>();
	
	public Group(List<Player> members, Player owner) {
		this.members = members;
		this.owner = owner;
		this.settings = new Settings();
		for(Player player : members){
			aPlayer aplayer = APlayerManager.getAPlayer(player);
			aplayer.is_in_pve_lobby = true;
			aplayer.firstRun = true;
			memberLocations.put(player, player.getLocation());
			player.teleport(PVE.main.lobby,TeleportCause.ENDER_PEARL);
		}
		groups.add(this);
	}
	
	
	public void disband(){
		groups.remove(this);
		PVE.main.removeQueue(this);
		
		for(Player player : members){
			aPlayer aplayer = APlayerManager.getAPlayer(player);
			aplayer.is_in_pve_lobby = false;
			aplayer.is_in_pve_arena = false;
			aplayer.firstRun = true;
			player.teleport(memberLocations.get(player),TeleportCause.ENDER_PEARL);
		}
	}
	
	public void sendPlayersMessage(String message, Player exception) {
		if (exception != null) {
			for (Player player : members) {
				if (player != exception) {
					MessageManager.sendMessage(player, message);
				}
			}
			return;
		}
	}

	public void sendPlayersMessage(String message) {
		for (Player player : members) {
			MessageManager.sendMessage(player, message);
		}
	}

}

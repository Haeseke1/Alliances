package me.Haeseke1.Alliances.Teleport;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class TeleportManager {

	public static HashMap<Player,Player> tpas = new HashMap<>();
	
	public static void teleportPlayerTo(Player player, Player target){
		if(!target.isOnline()){
			MessageManager.sendMessage(player, ChatColor.RED + "The tp requested was canceled cause the other player left the game");
			return;
		}
		tpas.remove(player);
		player.teleport(target);
		MessageManager.sendMessage(player, ChatColor.GREEN + "Teleporting...");
	}
	
}

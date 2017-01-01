package me.Haeseke1.Alliances.Utils;

import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Main.Main;

public class MessageManager {

	private String logo = Main.cmdlogo;
	
	public void sendMessage(Player player,String message){
		player.sendMessage(this.logo + message);
	}
	
	
}

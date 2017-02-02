package me.Haeseke1.Alliances.Teleport;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class Request {

	public static HashMap<Player,Request> requests = new HashMap<>();
	
	public Player player;
	public Player target;
	public int TIME_TILL_DISBAN;
	public int TELEPORT_TIME;
	public boolean accepted = false;
	
	public Request(Player player, Player target,int disban_time,int teleport_time){
		this.player = player;
		this.target = target;
		this.TIME_TILL_DISBAN = disban_time;
	    this.TELEPORT_TIME = teleport_time;
	    if(player.equals(target)){
	       MessageManager.sendMessage(player, "&cYou can send a tp request to yourself");
	       return;
	    }
	    if(requests.containsKey(player)){
		if(requests.get(player).target.getName().equalsIgnoreCase(this.target.getName())){
		   MessageManager.sendMessage(player, ChatColor.RED + "You've already sent a tp request to this player");
		   return;
	     }
	    }
		requests.put(player, this);
		MessageManager.sendMessage(player, "&2You've successfully sent a tp request to &6" + target.getName());
		MessageManager.sendMessage(target, "&6" + player.getName() + "&a sent you a tp request");
		MessageManager.sendMessage(target, "&7Type &a[YES] &7or &c[NO]&7 to respond");
		MessageManager.sendMessage(target, "&7You've &c" + this.TIME_TILL_DISBAN + "&7 seconds to answer this request!");
	}
	
	public void cooldown(){
		this.TIME_TILL_DISBAN = this.TIME_TILL_DISBAN - 1;
		if(this.TIME_TILL_DISBAN == 0){
			MessageManager.sendMessage(this.player, "&cYour request to &6" + this.target.getName() + "&c has been disbaned");
			requests.remove(player);
		}
	}
}

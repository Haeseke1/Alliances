package me.Haeseke1.Alliances.Auctions.Schedulers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Haeseke1.Alliances.Utils.MessageManager;

public class DelayedMessage extends BukkitRunnable{

	public Player player;
	
	public int time;
	
	public String message;
	
	public DelayedMessage(int time,String message,Player player){
		this.time = time;
		this.player = player;
		this.message = message;
	}

	@Override
	public void run() {
		if(this.time == 0){
			MessageManager.sendMessage(player, message);
			this.cancel();
		}
		time --;
	}
	
}

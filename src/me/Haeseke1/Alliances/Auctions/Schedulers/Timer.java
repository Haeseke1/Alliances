package me.Haeseke1.Alliances.Auctions.Schedulers;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.Haeseke1.Alliances.Auctions.Auction;
import me.Haeseke1.Alliances.Utils.TimeManager;

public class Timer extends BukkitRunnable{
	
	public Auction auction;

	public Timer(Auction auction){
		this.auction = auction;
	}
	
	@Override
	public void run() {
		auction.countdown();
	}
	
}

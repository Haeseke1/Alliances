package me.Haeseke1.Alliances.Main;

import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Economy.Coins;

public class aPlayer{

	int coins;
	Player player;

	public aPlayer(Player player) {
		coins = Coins.getPlayerCoins(player);
		this.player = player;
	}

}

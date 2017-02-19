package me.Haeseke1.Alliances.Item.Weapons.Wands.Scheduler;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Item.Totems.HealingTotem;

public class Healing implements Runnable {

	public Player player;

	@Override
	public void run() {
		for (HealingTotem totem : HealingTotem.htotems.values()) {
			player = totem.owner;
			if (!AllianceManager.playerIsInAlli(player)) {
				totem.heal(player);
				continue;
			}
			Alliance alliance = AllianceManager.getAlliance(player);
			for (UUID uuid : alliance.getMembers().keySet()) {
				player = Bukkit.getPlayer(uuid);
				totem.heal(player);
			}
		}
	}
}

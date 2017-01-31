package me.Haeseke1.Alliances.Weapons.Swords.Type;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Weapons.Swords.Sword;

public class Night_Blade extends Sword{

	public static HashMap<Player,Integer> cooldown = new HashMap<>();
	
	public Night_Blade(String name, Player player, int cooldown, String effect) {
		super(name, player, cooldown, effect);
	}

}

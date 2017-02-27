package me.Haeseke1.Alliances.Utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkManager {

	public static void launchFirework(Location loc, Type type, Color color, int power, boolean flicker){
		Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fwmeta = firework.getFireworkMeta();
		fwmeta.addEffect(FireworkEffect.builder().flicker(flicker).withColor(color).with(type).build());
		firework.setFireworkMeta(fwmeta);
	}
	
}

package me.Haeseke1.Alliances.Item.Weapons.Wands.Type;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.util.Vector;

import me.Haeseke1.Alliances.Item.Weapons.Wands.Wand;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Wither_Wand extends Wand{

	public Vector dir;
	
	public Wither_Wand(String name,Player user, Material wand_type, double mana) {
		super(name,user, wand_type, mana);
		dir = user.getLocation().getDirection();
	}

	public void launchProjectile(Sound sound){
		if(this.hasName()){
			if(APlayer.removeMana(mana)){
				WitherSkull skull = user.launchProjectile(WitherSkull.class,dir);
				skull.setCharged(false);
				skull.setIsIncendiary(false);
				if(sound == null){ return;}
				SoundManager.playSoundToPlayer(sound, user);
			}
		}
	}
	
	
	
}

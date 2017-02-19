package me.Haeseke1.Alliances.Item.Weapons.Wands.Type;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.Haeseke1.Alliances.APlayer.APlayerManager;
import me.Haeseke1.Alliances.APlayer.aPlayer;
import me.Haeseke1.Alliances.Item.Weapons.Wands.Wand;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Fire_Wand extends Wand{

	public Vector dir;
	
	public Fire_Wand(String name,Player user, Material wand_type, double mana) {
		super(name,user, wand_type,mana);
		dir = user.getLocation().getDirection();
	}
	
	public void launchProjectile(Sound sound){
		if(hasName()){
		aPlayer APlayer = APlayerManager.getAPlayer(user);
		if(APlayer.removeMana(mana)){
		Fireball fireball = user.launchProjectile(Fireball.class,dir);
		fireball.setIsIncendiary(false);
		if(sound == null){ return;}
		SoundManager.playSoundToPlayer(sound, user);
		}
		}
	}

	
}

package me.Haeseke1.Alliances.Item.Weapons.Swords.Type;

import org.bukkit.potion.PotionEffectType;

public enum SwordEffects {

	Night_Blade;
	
	public static PotionEffectType getEffect(SwordEffects type){
		switch(type){
		case Night_Blade:
		return PotionEffectType.INVISIBILITY;
		default:
		return null;
		}
	}
	
}
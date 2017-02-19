package me.Haeseke1.Alliances.Item.Weapons.Wands.Type;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Item.Totems.HealingTotem;
import me.Haeseke1.Alliances.Item.Weapons.Wands.Wand;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Healing_Wand extends Wand{
	
	public Healing_Wand(String name,Player user, Material wand_type, double mana) {
		super(name,user, wand_type,mana);
	}
	
	public void spawnTotem(Block block){
		if(this.hasName()){
		if(APlayer.removeMana(mana)){
		HealingTotem totem = new HealingTotem(user);
		totem.spawnTotem();
        SoundManager.playSoundToPlayer(Sound.LEVEL_UP, user);
		}
		}
	}
}

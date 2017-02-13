package me.Haeseke1.Alliances.Item.Weapons.Swords.Type;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Sword;
import me.Haeseke1.Alliances.Item.Weapons.Swords.SwordManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Night_Blade extends Sword implements Listener{

	
	public Night_Blade(Player player, int cooldown) {
		super("Night Blade", player, cooldown);
	}
	
	
	
	public Night_Blade() {
		
	}



	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
	    Player player = event.getPlayer();
	    if(player.getItemInHand() == null){
	    	return;
	    }
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK){
			return;
		}
		if(!player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasDisplayName() && !player.getItemInHand().getItemMeta().hasLore()){
			return;
		}
		String displayname = player.getItemInHand().getItemMeta().getDisplayName();
		if(!displayname.equals(ChatColor.LIGHT_PURPLE + "Night Blade")){
			return;
		}
		if(!SwordManager.hasSword(player.getUniqueId(), "Night Blade")){
			Night_Blade night_blade = new Night_Blade(player, 120);
			night_blade.giveEffect(Sound.ENDERDRAGON_WINGS);
		}else{
			MessageManager.sendMessage(player, "&cThis ability is cooling down (&6" + SwordManager.getSword(player.getUniqueId(), name).cooldown + "&cs left)"); 
		}
	}
	
	
	public void giveEffect(Sound sound){
		playerRemoveEffect(PotionEffectType.INVISIBILITY);
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,160,1,false));
		if(sound != null){ 
			SoundManager.playSoundToPlayer(sound, player);
		}
		for(Player player: Bukkit.getOnlinePlayers()){
			Alliance al = AllianceManager.getAlliance(this.player);
			if(al.equals(AllianceManager.getAlliance(player))){
				continue;
			}
			player.hidePlayer(this.player);
		}
	}
}

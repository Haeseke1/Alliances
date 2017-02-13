package me.Haeseke1.Alliances.Item.Weapons.Swords;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Sword{
	
	public static HashMap<UUID,List<Sword>> cooldowns = new HashMap<>();
	
	public String name;
	public Player player;
	public int cooldown;
	public PotionEffectType type;
	public UUID playerUUID;
	
	public Sword(String name, Player player, int cooldown) {
		this.player = player;
		this.cooldown = cooldown;
		SwordManager.putSword(playerUUID, this);
	}
	
	public Sword(){
		
	}

	public boolean itemHasEffect(ItemStack item, String effect){
		if(!(item.hasItemMeta())){ return false; }
		if(!(item.getItemMeta().hasLore())){ return false;}
		List<String> lore = item.getItemMeta().getLore();
		for(String loreItem: lore){
			if(ChatColor.stripColor(loreItem).equalsIgnoreCase(effect)){
				return true;
			}
		}
		return false;
	}
   
	public boolean hasSwordName(ItemStack item){
		if(!(item.hasItemMeta())){ return false; }
		if(!(item.getItemMeta().hasDisplayName())){ return false;}
		if(ChatColor.stripColor(item.getItemMeta().getDisplayName().replace("_", " ")).equalsIgnoreCase(name.replace("_", " "))){return true;}
		return false;
	}
	
	public void playerRemoveEffect(PotionEffectType currentEffect){
		for(PotionEffect effect: player.getActivePotionEffects()){
			if(effect.getType().equals(currentEffect)){
				player.removePotionEffect(effect.getType());
				return;
			}
		}
		return;
	}
	
	
	
	public void cooldown(UUID uuid){
		cooldown = cooldown - 1;
		if(cooldown <= 0){
			SwordManager.removeSword(uuid, this);
		}
	}
	
	
	
	
}

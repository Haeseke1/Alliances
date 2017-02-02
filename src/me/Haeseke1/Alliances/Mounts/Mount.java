package me.Haeseke1.Alliances.Mounts;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Haeseke1.Alliances.Arena.ArenaManager;
import me.Haeseke1.Alliances.CustomEntity.Horse.Elite;
import me.Haeseke1.Alliances.CustomEntity.Horse.Prince;
import me.Haeseke1.Alliances.Mounts.Commands.MountCommand;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Mount {

	public Player owner;
	public EntityType type;
	public String name;
	public String mountType;
    public Horse horse;
	
	public Mount(Player owner, String mountType){
		this.owner = owner;
		this.type = EntityType.HORSE;
		this.mountType = mountType;
	}
	
	public void spawnMount(){
	  if(ArenaManager.isInArena(owner)){ MessageManager.sendMessage(owner, "&cYou can't mount here"); return;}
		World world = owner.getWorld();
		Entity entity = world.spawnEntity(owner.getLocation(), type);
    	horse.setAdult();
    	horse.setTamed(true);
    	horse.setPassenger(owner);
	    switch(this.mountType){
	    case "elite":
	    	this.name = ChatColor.AQUA + this.owner.getName() + "'s mount";
	    	Horse elite = Elite.spawn(owner.getLocation());
	    	elite.getInventory().setArmor(new ItemStack(Material.DIAMOND_BARDING));
	    	elite.setVariant(Variant.HORSE);
	    	break;
	    case "prince":
	    	Horse prince = Prince.spawn(owner.getLocation());
	    	this.name = ChatColor.GOLD + this.owner.getName() + "'s mount";
	    	prince.getInventory().setArmor(new ItemStack(Material.GOLD_BARDING));
	    	prince.setVariant(Variant.HORSE);
	    	break;
	    case "donkey":
	    	Horse horse = Elite.spawn(owner.getLocation());
	    	this.name = ChatColor.GRAY + this.owner.getName() + "'s mount";
	    	horse.getInventory().setArmor(new ItemStack(Material.CHEST));
	    	horse.setVariant(Variant.DONKEY);
	    	horse.setCarryingChest(true);
	    	break;
	    default:
	    	this.name = null;
	    	break;
	    }
    	horse.setOwner(owner);
	    horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
	    horse.setCustomName(this.name);
	    horse.setCustomNameVisible(true);
	    this.horse = horse;
	}
	
	public void removeMount(){
		this.horse.setHealth(0);
		this.horse = null;
		MountCommand.mounts.remove(owner);
	}
	
	public boolean mobIsSpawned(){
		if(this.horse == null) return false;
		return true;
	}

}

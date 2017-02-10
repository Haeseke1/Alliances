package me.Haeseke1.Alliances.Mounts;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Arena.ArenaManager;
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
	
	@SuppressWarnings("unused")
	public void spawnMount(){
	  if(ArenaManager.isInArena(owner)){ MessageManager.sendMessage(owner, "&cYou can't mount here"); return;}
		World world = owner.getWorld();
	    switch(this.mountType){
	    case "elite":
	    	this.name = ChatColor.AQUA + this.owner.getName() + "'s mount";
	    	Horse elite = (Horse) this.owner.getWorld().spawnEntity(this.owner.getLocation(), EntityType.HORSE);
	    	elite.setAdult();
	    	elite.setTamed(true);
	    	elite.setPassenger(owner);
	    	elite.getInventory().setArmor(new ItemStack(Material.DIAMOND_BARDING));
	    	elite.setVariant(Variant.HORSE);
	    	elite.setOwner(owner);
		    elite.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		    elite.setCustomName(this.name);
		    elite.setCustomNameVisible(true);
		    this.horse = elite;
	    	break;
	    case "prince":
	    	Horse prince = (Horse) this.owner.getWorld().spawnEntity(this.owner.getLocation(), EntityType.HORSE);
	    	prince.setAdult();
	    	prince.setTamed(true);
	    	prince.setPassenger(owner);
	    	this.name = ChatColor.GOLD + this.owner.getName() + "'s mount";
	    	prince.getInventory().setArmor(new ItemStack(Material.GOLD_BARDING));
	    	prince.setVariant(Variant.HORSE);
	    	prince.setOwner(owner);
		    prince.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		    prince.setCustomName(this.name);
		    prince.setCustomNameVisible(true);
		    this.horse = prince;
	    	break;
	    case "donkey":
	    	Horse horse = (Horse) this.owner.getWorld().spawnEntity(this.owner.getLocation(), EntityType.HORSE);
	    	this.name = ChatColor.GRAY + this.owner.getName() + "'s mount";
	    	horse.getInventory().setArmor(new ItemStack(Material.CHEST));
	    	horse.setVariant(Variant.DONKEY);
	    	horse.setCarryingChest(true);
	    	horse.setPassenger(owner);
	    	horse.setOwner(owner);
		    horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		    horse.setCustomName(this.name);
		    horse.setCustomNameVisible(true);
		    this.horse = horse;
	    	break;
	    default:
	    	this.name = null;
	    	break;
	    }
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

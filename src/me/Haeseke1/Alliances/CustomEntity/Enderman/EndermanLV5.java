package me.Haeseke1.Alliances.CustomEntity.Enderman;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.entity.Enderman;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.EntityEnderman;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.World;

public class EndermanLV5 extends EntityEnderman{

	
	public EndermanLV5(World world){
		super(world);
	}

	@Override
	public void g(double x, double y, double z) {
	      return;
	}
	
	protected void initAttributes(){
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(20D);
		this.getAttributeInstance(GenericAttributes.d).setValue(0D);
	}
	
	
	protected Item getLoot(){
		return null;
	}
	
	public static Enderman spawn(Location location){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final EndermanLV5 customEntity = new EndermanLV5(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		return (Enderman) customEntity.getBukkitEntity();
	}

}

package me.Haeseke1.Alliances.CustomEntity.Blaze;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.entity.Blaze;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.EntityBlaze;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.World;

public class BlazeLV1 extends EntityBlaze{

	
	public BlazeLV1(World world){
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
	
	public static Blaze spawn(Location location){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final BlazeLV1 customEntity = new BlazeLV1(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		return (Blaze) customEntity.getBukkitEntity();
	}

}

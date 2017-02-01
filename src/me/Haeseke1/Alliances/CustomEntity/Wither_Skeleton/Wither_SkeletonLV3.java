package me.Haeseke1.Alliances.CustomEntity.Wither_Skeleton;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.EntityWitch;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.World;

public class Wither_SkeletonLV3 extends EntityWitch{

	
	public Wither_SkeletonLV3(World world){
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
	
	public static Skeleton spawn(Location location){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final Wither_SkeletonLV3 customEntity = new Wither_SkeletonLV3(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		Skeleton sk = (Skeleton) customEntity.getBukkitEntity();
		sk.setSkeletonType(SkeletonType.WITHER);
		return sk;
	}

}

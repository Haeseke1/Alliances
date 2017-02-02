package me.Haeseke1.Alliances.CustomEntity.Enderman;

import java.lang.reflect.Field;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.Enderman;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.DamageSource;
import net.minecraft.server.v1_8_R2.EntityEnderman;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R2.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;

public class EndermanLV2 extends EntityEnderman{

	
	public EndermanLV2(World world){
		super(world);
		try {
			Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
			bField.setAccessible(true);
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			cField.setAccessible(true);

			bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
		} catch (Exception e) {
			e.printStackTrace();
		}
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
	}
	
	protected void initAttributes(){
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(20D);
		this.getAttributeInstance(GenericAttributes.d).setValue(0D);
	}
	
	
	public boolean damageEntity(DamageSource damagesource, float f) {
		if(damagesource.getEntity() instanceof EntityLiving && new Random().nextInt(4) == 0){
			EntityLiving le = (EntityLiving) damagesource.getEntity();
			Location loc = getNewLocation(le.world.getWorld(), le.locX, le.locY, le.locZ, le.yaw);
			this.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		}
		return super.damageEntity(damagesource, f);
	}
	
	protected boolean k(double d0, double d1, double d2) {
		return false;
		
	}
	
	
	public static Location getNewLocation(CraftWorld world, double x,double y, double z, float yaw){
	    int degree = (int) yaw;
	    Location newLoc = new Location(world, x, y, z, yaw, 0);
	    if(degree >= 0 && degree <= 45){ newLoc.setZ(z - 1); return newLoc;}
	    if(degree > 45 && degree <= 90){ newLoc.setZ(z - 1); newLoc.setX(x + 1); return newLoc;}
	    if(degree > 90 && degree <= 135){ newLoc.setZ(z + 1); newLoc.setX(x - 1); return newLoc;}
	    if(degree > 135 && degree <= 180){ newLoc.setZ(z + 1); return newLoc;}
	    if(degree < 0 && degree >= -45){ newLoc.setZ(z + 1); newLoc.setX(x - 1); return newLoc;}
	    if(degree < -45 && degree >= -90){ newLoc.setX(x - 1); return newLoc;}
	    if(degree < -90 && degree >= -135){ newLoc.setX(x + 1); newLoc.setZ(z - 1); return newLoc;}
	    if(degree < -135 && degree >= -180){ newLoc.setZ(z + 1); return newLoc;}
	    return newLoc;
	}
	
	@Override
	protected void dropDeathLoot(boolean flag, int i) {
		return;
	}
	
	@Override
	protected Item getLoot(){
		return null;
	}
	
	public static Enderman spawn(Location location, String name){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final EndermanLV2 customEntity = new EndermanLV2(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		Enderman ender = (Enderman) customEntity.getBukkitEntity();
		ender.setCustomNameVisible(true);
		ender.setCustomName(name);
		return ender;
	}

}

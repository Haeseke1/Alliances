package me.Haeseke1.Alliances.CustomEntity.Zombie_Pigman;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.EntityPigZombie;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;

public class Zombie_PigmanLV1 extends EntityPigZombie{

	
	public Zombie_PigmanLV1(World world){
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
	}

	
	protected void initAttributes(){
		super.initAttributes();
	}
	
	@Override
	protected void dropDeathLoot(boolean flag, int i) {
		return;
	}
	
	@Override
	protected Item getLoot(){
		return null;
	}
	
	public static PigZombie spawn(Location location, String name){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final Zombie_PigmanLV1 customEntity = new Zombie_PigmanLV1(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		PigZombie zombie = (PigZombie) customEntity.getBukkitEntity();
		zombie.setBaby(false);
		zombie.setCustomNameVisible(true);
		zombie.setCustomName(name);
		return zombie;
	}

}

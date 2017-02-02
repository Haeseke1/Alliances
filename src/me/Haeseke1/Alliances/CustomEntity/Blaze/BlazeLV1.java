package me.Haeseke1.Alliances.CustomEntity.Blaze;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.entity.Blaze;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.EntityBlaze;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.World;

public class BlazeLV1 extends EntityBlaze{

	
	public BlazeLV1(World world){
		super(world);
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
	
	
	public static Blaze spawn(Location location, String name){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final BlazeLV1 customEntity = new BlazeLV1(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		Blaze blaze = (Blaze) customEntity.getBukkitEntity();
		blaze.setCustomName(name);
		blaze.setCustomNameVisible(true);
		return blaze;
	}
	
}

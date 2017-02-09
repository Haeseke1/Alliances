package me.Haeseke1.Alliances.CustomEntity.Horse;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.entity.Horse;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.EntityHorse;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.World;

public class Elite extends EntityHorse{

	public Elite(World world) {
		super(world);
	}

	protected void initAttributes(){
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(100D);
		this.getAttributeInstance(GenericAttributes.d).setValue(1D);
	}
	
	public static Horse spawn(Location location){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final Elite customEntity = new Elite(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		return (Horse) customEntity.getBukkitEntity();
	}
}

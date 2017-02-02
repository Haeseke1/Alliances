package me.Haeseke1.Alliances.CustomEntity.Zombie_Pigman;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.EntityPigZombie;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.ItemStack;
import net.minecraft.server.v1_8_R2.Items;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;

public class Zombie_PigmanLV2 extends EntityPigZombie{

	
	public Zombie_PigmanLV2(World world){
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
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(40D);
		this.getAttributeInstance(GenericAttributes.b).setValue(100000);
		this.getAttributeInstance(GenericAttributes.c).setValue(0);
		this.getAttributeInstance(GenericAttributes.d).setValue(0.55);
		this.getAttributeInstance(GenericAttributes.e).setValue(8D);
	}
	
	
	@Override
	protected void dropDeathLoot(boolean flag, int i) {
		return;
	}
	
	@Override
	protected Item getLoot(){
		return null;
	}
	
	@Override
	protected float bC() {
		this.setEquipment(0, new ItemStack(Items.GOLDEN_SWORD));
		this.setEquipment(1, new ItemStack(Items.LEATHER_BOOTS));
		this.setEquipment(2, new ItemStack(Items.LEATHER_LEGGINGS));
		this.setEquipment(3, new ItemStack(Items.LEATHER_CHESTPLATE));
		this.setEquipment(4, new ItemStack(Items.LEATHER_HELMET));
		return super.bC();
	}
	
	public static PigZombie spawn(Location location, String name){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final Zombie_PigmanLV2 customEntity = new Zombie_PigmanLV2(mcWorld);
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

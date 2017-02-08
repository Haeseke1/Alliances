package me.Haeseke1.Alliances.CustomEntity.Wither_Skeleton;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.Entity;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntitySkeleton;
import net.minecraft.server.v1_8_R2.EntityVillager;
import net.minecraft.server.v1_8_R2.EntityWitch;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.ItemStack;
import net.minecraft.server.v1_8_R2.Items;
import net.minecraft.server.v1_8_R2.MobEffect;
import net.minecraft.server.v1_8_R2.MobEffectList;
import net.minecraft.server.v1_8_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R2.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;

public class Wither_SkeletonLV3 extends EntitySkeleton{

	
	public Wither_SkeletonLV3(World world){
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
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.2D, false));
	}
	
	
	protected void initAttributes(){
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(60D);
		this.getAttributeInstance(GenericAttributes.b).setValue(100000);
		this.getAttributeInstance(GenericAttributes.c).setValue(100);
		this.getAttributeInstance(GenericAttributes.d).setValue(0.5);
		this.getAttributeInstance(GenericAttributes.e).setValue(9D);
	}
	
	@Override
	public void n(Entity entity) {
		if (this.getSkeletonType() == 1 && entity instanceof EntityLiving) {
			((EntityLiving) entity).addEffect(new MobEffect(MobEffectList.WITHER.id, 200,2));
		}
	}
	
	@Override
	protected float bC() {
		this.setEquipment(0, new ItemStack(Items.IRON_SWORD));
		this.setEquipment(1, new ItemStack(Items.IRON_BOOTS));
		this.setEquipment(2, new ItemStack(Items.IRON_LEGGINGS));
		this.setEquipment(3, new ItemStack(Items.IRON_CHESTPLATE));
		this.setEquipment(4, new ItemStack(Items.IRON_HELMET));
		return super.bC();
	}
	
	@Override
	protected void dropDeathLoot(boolean flag, int i) {
		return;
	}
	
	@Override
	protected Item getLoot(){
		return null;
	}
	
	public static Skeleton spawn(Location location, String name){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final Wither_SkeletonLV3 customEntity = new Wither_SkeletonLV3(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		Skeleton sk = (Skeleton) customEntity.getBukkitEntity();
		sk.setSkeletonType(SkeletonType.WITHER);
		sk.setCustomNameVisible(true);
		sk.setCustomName(name);
		return sk;
	}

}

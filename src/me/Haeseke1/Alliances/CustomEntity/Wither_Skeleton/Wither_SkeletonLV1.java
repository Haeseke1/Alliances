package me.Haeseke1.Alliances.CustomEntity.Wither_Skeleton;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.EntityEquipment;

import net.minecraft.server.v1_8_R2.Entity;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntitySkeleton;
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

public class Wither_SkeletonLV1 extends EntitySkeleton{

	
	public Wither_SkeletonLV1(World world){
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
		this.getAttributeInstance(GenericAttributes.b).setValue(100000);
	}
	
	@Override
	public void n(Entity entity) {
		if (this.getSkeletonType() == 1 && entity instanceof EntityLiving) {
			((EntityLiving) entity).addEffect(new MobEffect(MobEffectList.WITHER.id, 200));
		}
	}
	
	@Override
	protected float bC() {
		this.setEquipment(0, new ItemStack(Items.WOODEN_SWORD));
		this.setEquipment(1, new ItemStack(Items.LEATHER_BOOTS));
		this.setEquipment(2, new ItemStack(Items.LEATHER_LEGGINGS));
		this.setEquipment(3, new ItemStack(Items.LEATHER_CHESTPLATE));
		this.setEquipment(4, new ItemStack(Items.LEATHER_HELMET));
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
		final Wither_SkeletonLV1 customEntity = new Wither_SkeletonLV1(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		Skeleton sk = (Skeleton) customEntity.getBukkitEntity();
		sk.setSkeletonType(SkeletonType.WITHER);
		EntityEquipment ee = sk.getEquipment();
		ee.setBoots(new org.bukkit.inventory.ItemStack(Material.DIAMOND_BOOTS));
		ee.setLeggings(new org.bukkit.inventory.ItemStack(Material.DIAMOND_LEGGINGS));
		ee.setHelmet(new org.bukkit.inventory.ItemStack(Material.DIAMOND_HELMET));
		ee.setChestplate(new org.bukkit.inventory.ItemStack(Material.DIAMOND_CHESTPLATE));
		ee.setItemInHand(new org.bukkit.inventory.ItemStack(Material.DIAMOND_SWORD));
		sk.setCustomNameVisible(true);
		sk.setCustomName(name);
		return sk;
	}

}

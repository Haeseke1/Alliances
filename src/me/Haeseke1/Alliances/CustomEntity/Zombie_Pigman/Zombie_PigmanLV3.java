package me.Haeseke1.Alliances.CustomEntity.Zombie_Pigman;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import me.Haeseke1.Alliances.CustomEntity.Zombie.ZombieLV2;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R2.DamageSource;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityPigZombie;
import net.minecraft.server.v1_8_R2.EntityVillager;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.ItemStack;
import net.minecraft.server.v1_8_R2.Items;
import net.minecraft.server.v1_8_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R2.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R2.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_8_R2.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R2.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;

public class Zombie_PigmanLV3 extends EntityPigZombie{

	
	public List<Zombie> minions = new ArrayList<Zombie>();
	
	
	public Zombie_PigmanLV3(World world){
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

	public boolean damageEntity(DamageSource damagesource, float f) {
		if(!minions.isEmpty()){
			for(Zombie zombie : minions){
				if(!zombie.isDead()){
					return false;
				}
			}
		}
		if(damagesource.getEntity() == null && !(damagesource.getEntity() instanceof EntityHuman)){
			return true;
		}
		if(damagesource.getEntity() instanceof LivingEntity){
			Random r = new Random();
			if(r.nextInt(20) == 0){	
				for(int i = 0; i < 5; i++){
					Zombie zombie = ZombieLV2.spawn(new Location((org.bukkit.World) this.world.getWorld(),this.locX,this.locY,this.locZ), ChatColor.RED + "Minion");
					zombie.setTarget((LivingEntity) damagesource.getEntity().getBukkitEntity());
					minions.add(zombie);
				}
			}
		}
		return true;
	}
	
	@Override
	protected float bC() {
		this.setEquipment(0, new ItemStack(Items.GOLDEN_SWORD));
		this.setEquipment(1, new ItemStack(Items.IRON_BOOTS));
		this.setEquipment(2, new ItemStack(Items.IRON_LEGGINGS));
		this.setEquipment(3, new ItemStack(Items.IRON_CHESTPLATE));
		this.setEquipment(4, new ItemStack(Items.IRON_HELMET));
		return super.bC();
	}
	
	protected void initAttributes(){
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(60D);
		this.getAttributeInstance(GenericAttributes.b).setValue(100000);
		this.getAttributeInstance(GenericAttributes.c).setValue(0);
		this.getAttributeInstance(GenericAttributes.d).setValue(0.55);
		this.getAttributeInstance(GenericAttributes.e).setValue(14D);
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
		final Zombie_PigmanLV3 customEntity = new Zombie_PigmanLV3(mcWorld);
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

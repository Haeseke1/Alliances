package me.Haeseke1.Alliances.CustomEntity.Wither;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.Wither;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntityWither;
import net.minecraft.server.v1_8_R2.EntityWitherSkull;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.MathHelper;
import net.minecraft.server.v1_8_R2.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_8_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;

public class WitherLV2 extends EntityWither{

	
	public WitherLV2(World world){
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
		this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.0D, 40, 20.0F));
		this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
	}

	
	protected void initAttributes(){
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(600D);
		this.getAttributeInstance(GenericAttributes.b).setValue(100000);
		this.getAttributeInstance(GenericAttributes.c).setValue(100);
		this.getAttributeInstance(GenericAttributes.e).setValue(6D);
	}
	
	@Override
	public boolean cb() {
		return false;
	}
	
    private void a(int i, EntityLiving entityliving) {
        this.a(i, entityliving.locX, entityliving.locY + (double) entityliving.getHeadHeight() * 0.5D, entityliving.locZ, i == 0 && this.random.nextFloat() < 0.001F);
    }
	
    private double t(int i) {
        if (i <= 0) {
            return this.locX;
        } else {
            float f = (this.aI + (float) (180 * (i - 1))) / 180.0F * 3.1415927F;
            float f1 = MathHelper.cos(f);

            return this.locX + (double) f1 * 1.3D;
        }
    }

    private double u(int i) {
        return i <= 0 ? this.locY + 3.0D : this.locY + 2.2D;
    }

    private double v(int i) {
        if (i <= 0) {
            return this.locZ;
        } else {
            float f = (this.aI + (float) (180 * (i - 1))) / 180.0F * 3.1415927F;
            float f1 = MathHelper.sin(f);

            return this.locZ + (double) f1 * 1.3D;
        }
    }
    
	private void a(int i, double d0, double d1, double d2, boolean flag) {
		for(int count = 0; count < 2; count++){
	        this.world.a((EntityHuman) null, 1014, new BlockPosition(this), 0);
	        double d3 = this.t(i);
	        double d4 = this.u(i);
	        double d5 = this.v(i);
	        double d6 = d0 - d3;
	        double d7 = d1 - d4;
	        double d8 = d2 - d5;
	        EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.world, this, d6, d7, d8);

	        if (flag) {
	            entitywitherskull.setCharged(true);
	        }

	        entitywitherskull.locY = d4;
	        entitywitherskull.locX = d3;
	        entitywitherskull.locZ = d5;
	        this.world.addEntity(entitywitherskull);
		}
    }
	
	@Override
	public void a(EntityLiving entityliving, float f) {
		this.a(0,entityliving);
	}
	
	
	@Override
	protected void dropDeathLoot(boolean flag, int i) {
		return;
	}
	
	@Override
	protected Item getLoot(){
		return null;
	}
	
	public static Wither spawn(Location location, String name){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final WitherLV2 customEntity = new WitherLV2(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		Wither wither = (Wither) customEntity.getBukkitEntity();
		wither.setCustomNameVisible(true);
		wither.setCustomName(name);
		return wither;
	}

}

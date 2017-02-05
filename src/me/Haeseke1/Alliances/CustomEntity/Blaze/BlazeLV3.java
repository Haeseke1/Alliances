package me.Haeseke1.Alliances.CustomEntity.Blaze;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.Blaze;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.EntityBlaze;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntitySmallFireball;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.MathHelper;
import net.minecraft.server.v1_8_R2.PathfinderGoal;
import net.minecraft.server.v1_8_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;

public class BlazeLV3 extends EntityBlaze{

	
	public BlazeLV3(World world){
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
        this.goalSelector.a(4, new BlazeLV3.PathfinderGoalCustomBlazeFireball(this));
	}

	
	protected void initAttributes(){
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(125D);
		this.getAttributeInstance(GenericAttributes.b).setValue(100000);
		this.getAttributeInstance(GenericAttributes.c).setValue(100);
		this.getAttributeInstance(GenericAttributes.d).setValue(0.50);
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
	
	
	public static Blaze spawn(Location location, String name){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final BlazeLV3 customEntity = new BlazeLV3(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		Blaze blaze = (Blaze) customEntity.getBukkitEntity();
		blaze.setCustomName(name);
		blaze.setCustomNameVisible(true);
		return blaze;
	}
    static class PathfinderGoalCustomBlazeFireball extends PathfinderGoal {

        private EntityBlaze a;
        private int b;
        private int c;

        public PathfinderGoalCustomBlazeFireball(EntityBlaze entityblaze) {
            this.a = entityblaze;
            this.a(3);
        }

        public boolean a() {
            EntityLiving entityliving = this.a.getGoalTarget();

            return entityliving != null && entityliving.isAlive();
        }
        

        public void e() {
        	b++;
        	if(b == 30){
        		b = 0;
            	EntityLiving entityliving = this.a.getGoalTarget();
            	double d0 = this.a.h(entityliving);
                double d1 = entityliving.locX - this.a.locX;
                double d2 = entityliving.getBoundingBox().b + (double) (entityliving.length / 2.0F) - (this.a.locY + (double) (this.a.length / 2.0F));
                double d3 = entityliving.locZ - this.a.locZ;
                float f = MathHelper.c(MathHelper.sqrt(d0)) * 0.5F;

                this.a.world.a((EntityHuman) null, 1009, new BlockPosition((int) this.a.locX, (int) this.a.locY, (int) this.a.locZ), 0);

                for (int i = 0; i < 3; ++i) {
                    EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.a.world, this.a, d1 + this.a.bc().nextGaussian() * (double) f, d2, d3 + this.a.bc().nextGaussian() * (double) f);

                    entitysmallfireball.locY = this.a.locY + (double) (this.a.length / 2.0F) + 0.5D;
                    this.a.world.addEntity(entitysmallfireball);
                }
                super.e();
        	}
        }
    }
}

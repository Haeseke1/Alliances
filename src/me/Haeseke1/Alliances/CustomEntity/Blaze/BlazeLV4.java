package me.Haeseke1.Alliances.CustomEntity.Blaze;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
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
import net.minecraft.server.v1_8_R2.World;

public class BlazeLV4 extends EntityBlaze{

	
	public BlazeLV4(World world){
		super(world);
	}

	
	protected void initAttributes(){
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(200D);
		this.getAttributeInstance(GenericAttributes.b).setValue(100000);
		this.getAttributeInstance(GenericAttributes.c).setValue(100);
		this.getAttributeInstance(GenericAttributes.d).setValue(0.75);
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
		final BlazeLV4 customEntity = new BlazeLV4(mcWorld);
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

        public void c() {
            this.b = 0;
        }

        public void d() {
            this.a.a(false);
        }

        public void e() {
            --this.c;
            EntityLiving entityliving = this.a.getGoalTarget();
            double d0 = this.a.h(entityliving);

            if (d0 < 4.0D) {
                if (this.c <= 0) {
                    this.c = 20;
                    this.a.r(entityliving);
                }

                this.a.getControllerMove().a(entityliving.locX, entityliving.locY, entityliving.locZ, 1.0D);
            } else if (d0 < 256.0D) {
                double d1 = entityliving.locX - this.a.locX;
                double d2 = entityliving.getBoundingBox().b + (double) (entityliving.length / 2.0F) - (this.a.locY + (double) (this.a.length / 2.0F));
                double d3 = entityliving.locZ - this.a.locZ;

                if (this.c <= 0) {
                    ++this.b;
                    if (this.b == 1) {
                        this.c = 60;
                        this.a.a(true);
                    } else if (this.b <= 4) {
                        this.c = 6;
                    } else {
                        this.c = 100;
                        this.b = 0;
                        this.a.a(false);
                    }

                    if (this.b > 1) {
                        float f = MathHelper.c(MathHelper.sqrt(d0)) * 0.5F;

                        this.a.world.a((EntityHuman) null, 1009, new BlockPosition((int) this.a.locX, (int) this.a.locY, (int) this.a.locZ), 0);

                        for (int i = 0; i < 8; ++i) {
                            EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.a.world, this.a, d1 + this.a.bc().nextGaussian() * (double) f, d2, d3 + this.a.bc().nextGaussian() * (double) f);

                            entitysmallfireball.locY = this.a.locY + (double) (this.a.length / 2.0F) + 0.5D;
                            this.a.world.addEntity(entitysmallfireball);
                        }
                    }
                }

                this.a.getControllerLook().a(entityliving, 10.0F, 10.0F);
            } else {
                this.a.getNavigation().n();
                this.a.getControllerMove().a(entityliving.locX, entityliving.locY, entityliving.locZ, 1.0D);
            }

            super.e();
        }
    }
}

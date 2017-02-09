package me.Haeseke1.Alliances.CustomEntity.Spider;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.Spider;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.Entity;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntitySpider;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.MobEffect;
import net.minecraft.server.v1_8_R2.MobEffectList;
import net.minecraft.server.v1_8_R2.PathfinderGoalLeapAtTarget;
import net.minecraft.server.v1_8_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R2.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;

public class SpiderLV5 extends EntitySpider{

	
	public SpiderLV5(World world){
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
		this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));
        this.goalSelector.a(4, new SpiderLV5.PathfinderGoalSpiderMeleeAttack(this, EntityHuman.class));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        
	}

	
	protected void initAttributes(){
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(80D);
		this.getAttributeInstance(GenericAttributes.b).setValue(100000);
		this.getAttributeInstance(GenericAttributes.c).setValue(100);
		this.getAttributeInstance(GenericAttributes.d).setValue(0.5);
		this.getAttributeInstance(GenericAttributes.e).setValue(20D);
	}
	
	@Override
	public void n(Entity entity) {
		if (entity instanceof EntityLiving) {
			((EntityLiving) entity).addEffect(new MobEffect(MobEffectList.SLOWER_MOVEMENT.id, 200,3));
		}
	}
	
	@Override
	protected void dropDeathLoot(boolean flag, int i) {
		return;
	}
	
	@Override
	protected Item getLoot(){
		return null;
	}
	
	public static Spider spawn(Location location, String name){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final SpiderLV5 customEntity = new SpiderLV5(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		Spider spider = (Spider) customEntity.getBukkitEntity();
		spider.setCustomNameVisible(true);
		spider.setCustomName(name);
		return spider;
	}
	
	static class PathfinderGoalSpiderMeleeAttack extends PathfinderGoalMeleeAttack {

        public PathfinderGoalSpiderMeleeAttack(EntitySpider entityspider, Class<? extends Entity> oclass) {
            super(entityspider, oclass, 1.0D, true);
        }

        public boolean b() {
            float f = this.b.c(1.0F);

            if (f >= 0.5F && this.b.bc().nextInt(100) == 0) {
                this.b.setGoalTarget((EntityLiving) null);
                return false;
            } else {
                return super.b();
            }
        }

        protected double a(EntityLiving entityliving) {
            return (double) (4.0F + entityliving.width);
        }
    }

}

package me.Haeseke1.Alliances.CustomEntity.Spider;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.Spider;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.Entity;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntitySpider;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.MobEffect;
import net.minecraft.server.v1_8_R2.MobEffectList;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;

public class SpiderLV4 extends EntitySpider{

	
	public SpiderLV4(World world){
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
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(64D);
		this.getAttributeInstance(GenericAttributes.b).setValue(100000);
		this.getAttributeInstance(GenericAttributes.c).setValue(100);
		this.getAttributeInstance(GenericAttributes.d).setValue(2);
		this.getAttributeInstance(GenericAttributes.e).setValue(16D);
	}
	
	@Override
	public void n(Entity entity) {
		if (entity instanceof EntityLiving) {
			((EntityLiving) entity).addEffect(new MobEffect(MobEffectList.SLOWER_MOVEMENT.id, 200,2));
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
		final SpiderLV4 customEntity = new SpiderLV4(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		Spider spider = (Spider) customEntity.getBukkitEntity();
		spider.setCustomNameVisible(true);
		spider.setCustomName(name);
		return spider;
	}

}

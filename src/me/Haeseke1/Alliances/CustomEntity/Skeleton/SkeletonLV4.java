package me.Haeseke1.Alliances.CustomEntity.Skeleton;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.Enchantment;
import net.minecraft.server.v1_8_R2.EnchantmentManager;
import net.minecraft.server.v1_8_R2.EntityArrow;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntitySkeleton;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;

public class SkeletonLV4 extends EntitySkeleton{

	
	public SkeletonLV4(World world){
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

	
    public void a(EntityLiving entityliving, float f) {
    	for(int a = 0; a < 4; a++){
        	EntityArrow entityarrow = new EntityArrow(this.world, this, entityliving, 1.6F, (float) (14 - this.world.getDifficulty().a() * 4));
            int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, this.bA());
            int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, this.bA());

            entityarrow.b((double) (f * 2.0F) + this.random.nextGaussian() * 0.25D + (double) ((float) this.world.getDifficulty().a() * 0.11F));
            if (i > 0) {
                entityarrow.b(entityarrow.j() + (double) i * 0.5D + 0.5D);
            }

            if (j > 0) {
                entityarrow.setKnockbackStrength(j);
            }
    	}
    }
	
	protected void initAttributes(){
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(65D);
		this.getAttributeInstance(GenericAttributes.b).setValue(100000);
		this.getAttributeInstance(GenericAttributes.c).setValue(100);
		this.getAttributeInstance(GenericAttributes.d).setValue(0.50);
		this.getAttributeInstance(GenericAttributes.e).setValue(11D);
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
		final SkeletonLV4 customEntity = new SkeletonLV4(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		Skeleton sk = (Skeleton) customEntity.getBukkitEntity();
		sk.setCustomNameVisible(true);
		sk.setCustomName(name);
		return sk;
	}

}

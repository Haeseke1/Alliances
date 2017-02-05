package me.Haeseke1.Alliances.CustomEntity.Skeleton;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.Enchantment;
import net.minecraft.server.v1_8_R2.EnchantmentManager;
import net.minecraft.server.v1_8_R2.EntityArrow;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntitySkeleton;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.ItemStack;
import net.minecraft.server.v1_8_R2.Items;
import net.minecraft.server.v1_8_R2.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_8_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;

public class SkeletonLV3 extends EntitySkeleton{

	
	public SkeletonLV3(World world){
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
        this.goalSelector.a(4, new PathfinderGoalArrowAttack(this, 1.0D, 20, 60, 15.0F));
	}
	
	@Override
	protected float bC() {
		this.setEquipment(0, new ItemStack(Items.BOW));
		this.setEquipment(1, new ItemStack(Items.IRON_BOOTS));
		this.setEquipment(2, new ItemStack(Items.IRON_LEGGINGS));
		this.setEquipment(3, new ItemStack(Items.IRON_CHESTPLATE));
		this.setEquipment(4, new ItemStack(Items.IRON_HELMET));
		return super.bC();
	}

	@Override
    public void a(EntityLiving entityliving, float f) {
    	for(int a = 0; a < 3; a++){
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

            if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, this.bA()) > 0 || this.getSkeletonType() == 1) {
                // CraftBukkit start - call EntityCombustEvent
                EntityCombustEvent event = new EntityCombustEvent(entityarrow.getBukkitEntity(), 100);
                this.world.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    entityarrow.setOnFire(event.getDuration());
                }
                // CraftBukkit end
            }

            // CraftBukkit start
            org.bukkit.event.entity.EntityShootBowEvent event = org.bukkit.craftbukkit.v1_8_R2.event.CraftEventFactory.callEntityShootBowEvent(this, this.bA(), entityarrow, 0.8F);
            if (event.isCancelled()) {
                event.getProjectile().remove();
                return;
            }

            if (event.getProjectile() == entityarrow.getBukkitEntity()) {
                world.addEntity(entityarrow);
            }
            // CraftBukkit end

            this.makeSound("random.bow", 1.0F, 1.0F / (this.bc().nextFloat() * 0.4F + 0.8F));
    	}
    }
	
	protected void initAttributes(){
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(50D);
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
	
	public static Skeleton spawn(Location location, String name){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final SkeletonLV3 customEntity = new SkeletonLV3(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		Skeleton sk = (Skeleton) customEntity.getBukkitEntity();
		sk.setCustomNameVisible(true);
		sk.setCustomName(name);
		return sk;
	}

}

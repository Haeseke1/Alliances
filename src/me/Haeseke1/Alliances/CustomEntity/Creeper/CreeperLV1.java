package me.Haeseke1.Alliances.CustomEntity.Creeper;


import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.Creeper;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.DamageSource;
import net.minecraft.server.v1_8_R2.EntityCreeper;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R2.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.PathfinderGoalSwell;
import net.minecraft.server.v1_8_R2.World;

public class CreeperLV1 extends EntityCreeper{

	private int fuseTicks = 0;
	private int maxFuseTicks = 30;
	
	public CreeperLV1(World world){
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
		this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, false));
        this.goalSelector.a(2, new PathfinderGoalSwell(this));
	}
	
	
	protected void initAttributes(){
		super.initAttributes();
	}
	
	
	@Override
	protected void dropDeathLoot(boolean flag, int i) {
		return;
	}
	
	@Override
	protected Item getLoot(){
		return null;
	}
	
	
    protected void b(float f) {
    	Bukkit.broadcastMessage("get called pls");
        this.fuseTicks = (int) ((float) this.fuseTicks + f * 1.5F);
        if (this.fuseTicks > this.maxFuseTicks - 5) {
            this.fuseTicks = this.maxFuseTicks - 5;
        }
    }
	
    @Override
    public void t_() {
    	Bukkit.broadcastMessage("You will get the t later");
        if (this.isAlive()) {
            if (this.cn()) {
                this.a(1);
            }

            int i = this.cm();

            if (i > 0 && this.fuseTicks == 0) {
                this.makeSound("creeper.primed", 1.0F, 0.5F);
            }

            this.fuseTicks += i;
            if (this.fuseTicks < 0) {
                this.fuseTicks = 0;
            }

            if (this.fuseTicks >= this.maxFuseTicks) {
                this.fuseTicks = this.maxFuseTicks;
                explode();
            }
        }
    }
    
    
	public boolean damageEntity(DamageSource damagesource, float f) {
		Bukkit.broadcastMessage("Hello damage");
		if(damagesource.isExplosion()){
			Bukkit.broadcastMessage("RIP damage");
			return false;
		}
		return super.damageEntity(damagesource, f);
	}
    
    private void explode(){
    	Bukkit.broadcastMessage("Own explosion");
        float f = 1.0F;
        this.world.createExplosion(this, this.locX, this.locY, this.locZ, f, false, false);
        fuseTicks = 0;
    }
	
	
	public static Creeper spawn(Location location, String name){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final CreeperLV1 customEntity = new CreeperLV1(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		Creeper cp = (Creeper) customEntity.getBukkitEntity();
		cp.setCustomName(name);
		cp.setCustomNameVisible(true);
		return cp;
	}

}

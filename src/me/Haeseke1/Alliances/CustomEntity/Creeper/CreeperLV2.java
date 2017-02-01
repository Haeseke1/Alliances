package me.Haeseke1.Alliances.CustomEntity.Creeper;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.entity.Creeper;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R2.DamageSource;
import net.minecraft.server.v1_8_R2.EntityCreeper;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.Item;
import net.minecraft.server.v1_8_R2.World;

public class CreeperLV2 extends EntityCreeper{

	private int fuseTicks = 0;
	private int maxFuseTicks = 30;
	
	public CreeperLV2(World world){
		super(world);
	}
	
    @Override
    public void t_() {
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

        super.t_();
    }
    
	public boolean damageEntity(DamageSource damagesource, float f) {
		if(damagesource.isExplosion()){
			return false;
		}
		return true;
	}
    
    private void explode(){
        boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
        float f = 3.0F;
        this.world.createExplosion(this, this.locX, this.locY, this.locZ, f, false, flag);
        fuseTicks = 0;
    }
	
	protected void initAttributes(){
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(50D);
		this.getAttributeInstance(GenericAttributes.b).setValue(100000);
		this.getAttributeInstance(GenericAttributes.c).setValue(0);
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
	
	
	public static Creeper spawn(Location location, String name){
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final CreeperLV2 customEntity = new CreeperLV2(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		Creeper cp = (Creeper) customEntity.getBukkitEntity();
		cp.setCustomName(name);
		cp.setCustomNameVisible(true);
		return cp;
	}

}

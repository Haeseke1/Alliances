package me.Haeseke1.Alliances.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.PacketPlayOutWorldParticles;

public class ParticleManager {
	
	public static void playParticle(Effect type, Location loc, int distance, int data){
		loc.getWorld().playEffect(loc, type, distance, data);
	}
	public static void playParticle(Effect type, Location loc, int distance){
		loc.getWorld().playEffect(loc, type, distance);
	}
	
	public static void playParticle(EnumParticle type, Location loc, float distance, int amount){
		PacketPlayOutWorldParticles ppowp = new PacketPlayOutWorldParticles(type, true,(float)loc.getX(),(float)(loc.getY()), (float)loc.getZ(),distance,distance,distance,0f,amount);
		for(Player player : Bukkit.getOnlinePlayers()){
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(ppowp);
		}
	}
	
	public static void playParticle(EnumParticle type, Location loc, float distance, int amount, Player player){
		PacketPlayOutWorldParticles ppowp = new PacketPlayOutWorldParticles(type, true,(float)loc.getX(),(float)(loc.getY()), (float)loc.getZ(),distance,distance,distance,0f,amount);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(ppowp);
	}
	
	public static void playParticle(EnumParticle type, Location loc, float distance, int amount, Player player, boolean lagg){
		PacketPlayOutWorldParticles ppowp = new PacketPlayOutWorldParticles(type, lagg,(float)loc.getX(),(float)(loc.getY()), (float)loc.getZ(),distance,distance,distance,0f,amount);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(ppowp);
	}
}

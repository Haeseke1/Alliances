package me.Haeseke1.Alliances.Challenge;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum challengeType {
	Mob_Killing_Time, Mob_Killing_Count, Mob_Killing_Zombie,
	Mob_Killing_Skeleton, Mob_Killing_Enderman, Mob_Killing_Wither,
	Block_Breaking, Block_Placing, Fishing, Enchanting, Player_Kill,
	Time_On, Lost_Hearts, Distance_Travel;
	
	private static final List<challengeType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();
	
	public static challengeType getChallengeType(String name){
		switch(name.toLowerCase()){
		case "mob_killing_time":
			return challengeType.Mob_Killing_Time;
		case "mob_killing_count":
			return challengeType.Mob_Killing_Count;
		case "mob_killing_zombie":
			return challengeType.Mob_Killing_Zombie;
		case "mob_killing_skeleton":
			return challengeType.Mob_Killing_Skeleton;
		case "mob_killing_enderman":
			return challengeType.Mob_Killing_Enderman;
		case "mob_killing_wither":
			return challengeType.Mob_Killing_Wither;
		case "block_breaking":
			return challengeType.Block_Breaking;
		case "block_placing":
			return challengeType.Block_Placing;
		case "fishing":
			return challengeType.Fishing;
		case "enchanting":
			return challengeType.Enchanting;
		case "player_kill":
			return challengeType.Player_Kill;
		case "time_on":
			return challengeType.Time_On;
		case "lost_hearths":
			return challengeType.Lost_Hearts;
		case "distance_travel":
			return challengeType.Distance_Travel;
		}
		return null;
	}
	
	
	public static challengeType randomtype(){
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}

package me.Haeseke1.Alliances.Challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class Challenge {
	
	public static List<Challenge> challenges = new ArrayList<Challenge>();
	
	public static HashMap<challengeType, Integer> challengeMax_Points = new HashMap<challengeType, Integer>();
	public static HashMap<challengeType, Integer> challengeTimer = new HashMap<challengeType, Integer>();
	public static HashMap<challengeType, Integer> challengeReward = new HashMap<challengeType, Integer>();
	public static HashMap<challengeType, String> challengeName = new HashMap<challengeType, String>();
	
	public String name;
	public int timer;
	public double max_Points;
	public int reward;
	
	public challengeType type;
	
	public HashMap<UUID,Double> points = new HashMap<UUID,Double>();
	public List<UUID> done = new ArrayList<UUID>();
	
	public Challenge(String name, int timer, int max_Points, int reward, challengeType type) {
		this.name = name;
		this.timer = timer;
		this.max_Points = max_Points;
		this.type = type;
		this.reward = reward;
	}
	
	
}

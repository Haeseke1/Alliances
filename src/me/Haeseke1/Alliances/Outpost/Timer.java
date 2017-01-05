package me.Haeseke1.Alliances.Outpost;

public class Timer implements Runnable{
	
	public static int rewardTime;
	public static int currentRewardTime;
	
	public static int take_overTime;

	@Override
	public void run() {
		if(currentRewardTime >= rewardTime){
			OutpostManager.giveReward();
			currentRewardTime = 0;
		}else{
			currentRewardTime++;
		}
		
		
	}
}

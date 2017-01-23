package me.Haeseke1.Alliances.ScoreBoard.Update;


import me.Haeseke1.Alliances.APlayer.aPlayer;

public class Counter implements Runnable{
	
	
	public static int time = 0;
	public static boolean player = false;
	public static boolean alli = true;
	
	@Override
	public void run() {
		if(time >= 5){
			if(player){
				alli = true;
				player = false;
				for(aPlayer aplayer: aPlayer.online_Players){
					aplayer.resetScore();
				}
			}else{
				alli = false;
				player = true;
				for(aPlayer aplayer: aPlayer.online_Players){
					aplayer.resetScore();
				}
			}
			time = 0;
		}
		for(aPlayer aplayer: aPlayer.online_Players){
			if(player){
				aplayer.setPlayerScoreBoard();
			}
			if(alli){
				aplayer.setAllianceScoreBoard();
			}
		}
		time++;
	}

}

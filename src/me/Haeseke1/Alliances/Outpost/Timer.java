package me.Haeseke1.Alliances.Outpost;

public class Timer implements Runnable{
	


	@Override
	public void run() {
		for(Outpost outpost : Outpost.outposts){
			outpost.update();
		}
	}
}

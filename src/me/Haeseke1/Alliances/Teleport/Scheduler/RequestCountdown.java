package me.Haeseke1.Alliances.Teleport.Scheduler;

import me.Haeseke1.Alliances.Teleport.Request;

public class RequestCountdown implements Runnable{

	@Override
	public void run() {
	  for(Request request: Request.requests.values()){
		  if(request.accepted){continue;}
		  request.cooldown();
	  }
	}

}

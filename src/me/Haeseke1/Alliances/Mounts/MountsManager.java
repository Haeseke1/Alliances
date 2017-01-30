package me.Haeseke1.Alliances.Mounts;

import me.Haeseke1.Alliances.Mounts.Commands.MountCommand;

public class MountsManager {

	public static void despawnMounts(){
		for(Mount mount: MountCommand.mounts.values()){
			mount.removeMount();
		}
	}
	
}


package org.planetfactions.envoy.app.announcer;

import org.bukkit.scheduler.BukkitTask;
import org.planetfactions.envoy.app.Envoy;

public class EnvoyAutoAnnouncer
{
	private static Envoy envoy = Envoy.getEnvoyEvent();
	public EnvoyAutoAnnouncer() 
	{
		
	}
	
	public static void announce()
	{
		double time = envoy.getAutoStartTime()/1200.0;
		long sec30 = envoy.getAutoStartTime() - 600L;
		long min1 =  envoy.getAutoStartTime() - 1200L;
		long min5 =  envoy.getAutoStartTime() - 6000L;
		if(time >= 10)
		{
			BukkitTask task1 = new Sec30().runTaskLater(envoy.getPlugin(), sec30);
			BukkitTask task2 = new Min1().runTaskLater(envoy.getPlugin(), min1);
			BukkitTask task3 = new Min5().runTaskLater(envoy.getPlugin(), min5);
		}
		else if(time >= 5 && time < 10)
		{
			BukkitTask task1 = new Sec30().runTaskLater(envoy.getPlugin(), sec30);
			BukkitTask task2 = new Min1().runTaskLater(envoy.getPlugin(), min1);
		}
		else if(time >= 1 && time < 5)
		{
			BukkitTask task1 = new Sec30().runTaskLater(envoy.getPlugin(), sec30);
			BukkitTask task2 = new Min1().runTaskLater(envoy.getPlugin(), min1);
		}
		else
		{
			BukkitTask task1 = new Sec30().runTaskLater(envoy.getPlugin(), sec30);
		}
	}
}

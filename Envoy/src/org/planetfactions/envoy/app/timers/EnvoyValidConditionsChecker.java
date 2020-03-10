package org.planetfactions.envoy.app.timers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.planetfactions.envoy.Main;
import org.planetfactions.envoy.app.Envoy;

public class EnvoyValidConditionsChecker extends BukkitRunnable
{
	private Envoy envoy = Envoy.getEnvoyEvent();
	private Main plugin;
	public EnvoyValidConditionsChecker(Main plugin)
	{
		this.plugin = plugin;
	}
	
	
	@Override
	public void run() 
	{
		
		if(envoy.getPlayersReached())
		{
			BukkitTask starter = new EnvoyStarter(plugin).runTaskTimer(plugin, 100L, 1000L);
		}
		else
		{
			List<BukkitTask> tasks = Bukkit.getScheduler().getPendingTasks();
			for(BukkitTask task : tasks)
			{
				if(task instanceof EnvoyStarter)
				{
					task.cancel();
					System.out.println(task.toString());
				}
			}
			Bukkit.broadcast(ChatColor.YELLOW + "The Server Attempted to start an Envoy but not Enough Players Were Online", "envoy.create");
		}
	}
}

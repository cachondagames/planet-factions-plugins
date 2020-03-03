package org.planetfactions.envoy.app.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.planetfactions.envoy.Main;
import org.planetfactions.envoy.app.Envoy;

public class Schedule extends BukkitRunnable
{
	private Envoy envoy = Envoy.getEnvoyEvent();
	private Main plugin;
	private int TaskId = 1000;
	public Schedule(Main plugin)
	{
		this.plugin = plugin;
	}
	
	
	@Override
	public void run() 
	{
		
		if(envoy.getPlayersReached())
		{
			BukkitTask starter = new Starter(plugin).runTaskTimer(plugin, 100L, 1000L);
			TaskId = starter.getTaskId();
		}
		else
		{
			try
			{
				Bukkit.getScheduler().cancelTask(TaskId);
			}
			catch(IllegalStateException e) {}
			
			Bukkit.broadcast(ChatColor.YELLOW + "The Server Attempted to start an Envoy but not Enough Players Were Online", "envoy.create");
		}
	}
}

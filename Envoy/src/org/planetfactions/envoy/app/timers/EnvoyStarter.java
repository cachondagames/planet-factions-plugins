package org.planetfactions.envoy.app.timers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.planetfactions.envoy.Main;
import org.planetfactions.envoy.app.Envoy;

public class EnvoyStarter extends BukkitRunnable
{
	private Envoy envoy = Envoy.getEnvoyEvent();
	public EnvoyStarter(Main plugin)
	{
		
	}
	
	@Override
	public void run()
	{
		
		if(envoy.getOutterBound() == 0)
			envoy.setOutterBound(30);
		if(envoy.getInnerBound() == 0)
			envoy.setInnerBound(5);
		if(envoy.getEnvoyActiveState())
			Bukkit.broadcast(ChatColor.DARK_RED + "The server has attempted an auto start event however an Envoy is already active!", "envoy.create");
		else
		{
			envoy.createEnvoy(envoy.getAutoStartCrates());
			Bukkit.broadcast(ChatColor.DARK_RED + "The server has autostarted an envoy event!", "envoy.create");
			Bukkit.getScheduler().cancelTask(envoy.getTaskID());
			BukkitTask autoender = new EnvoyAutoEnder().runTaskLater(Bukkit.getPluginManager().getPlugin("Envoy"), 1000L);
			this.cancel();
		}
	}
}

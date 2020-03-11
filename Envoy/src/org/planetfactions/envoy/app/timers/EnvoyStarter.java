package org.planetfactions.envoy.app.timers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.planetfactions.envoy.app.Envoy;

public class EnvoyStarter extends BukkitRunnable
{
	private Envoy envoy = Envoy.getEnvoyEvent();
	public EnvoyStarter()
	{
		
	}
	
	@Override
	public void run()
	{
		envoy.setConditionTaskID(this.getTaskId());
		if(envoy.getPlayersReached())
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
				BukkitTask autoender = new EnvoyAutoEnder().runTaskLater(Bukkit.getPluginManager().getPlugin("Envoy"), 36000L);
				this.cancel();
			}
		}
		else
		{
			Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] &7The server attempted to start an Envoy but not enough players were online"), "envoy.create");
		}
	}
}

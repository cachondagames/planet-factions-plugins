package org.planetfactions.envoy.app.timers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.planetfactions.envoy.Main;
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
		Main plugin = envoy.getPlugin();
		envoy.setConditionTaskID(this.getTaskId());
		if(envoy.getPlayersReached())
		{
			if(envoy.getOutterBound() == 0)
				envoy.setOutterBound(30);
			if(envoy.getInnerBound() == 0)
				envoy.setInnerBound(5);
			if(envoy.getEnvoyActiveState())
				Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', "&[Envoy] &4The server has attempted an auto start event however an Envoy is already active!") , "envoy.create");
			else
			{
				envoy.createEnvoy((int) plugin.getConfig().getDouble("autostartcratenum"));
				Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] &4The server has autostarted an envoy event!"), "envoy.create");
				this.cancel();
			}
		}
		else
		{
			Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] &7The server attempted to start an Envoy but not enough players were online"), "envoy.create");
		}
	}
}
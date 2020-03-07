package org.planetfactions.envoy.app.schedule;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.planetfactions.envoy.Main;
import org.planetfactions.envoy.app.Envoy;

public class Starter extends BukkitRunnable
{
	private Envoy envoy = Envoy.getEnvoyEvent();
	public Starter(Main plugin)
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
		envoy.createEnvoyServerStart(envoy.getAutoStartCrates());
		Bukkit.broadcast(ChatColor.DARK_RED + "The server has autostarted an envoy event!", "envoy.create");
		}
	}
}

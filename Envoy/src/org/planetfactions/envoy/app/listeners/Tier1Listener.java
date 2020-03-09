package org.planetfactions.envoy.app.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.planetfactions.envoy.Main;
import org.planetfactions.envoy.app.Envoy;
import org.planetfactions.envoy.app.events.EnvoyTier1OpenEvent;

public class Tier1Listener implements Listener
{
	
	Envoy envoy = Envoy.getEnvoyEvent();
	public Tier1Listener(Main plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void OpenEvent(EnvoyTier1OpenEvent e)
	{
		try
		{
		e.getLocation().getBlock().setType(Material.AIR);
		envoy.chooseTier(envoy.getChestLocations().get(envoy.getLocationOn()).getLocation());
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&' , "&aTest"));
		}
		catch(IndexOutOfBoundsException i)
		{
			envoy.endEnvoy();
		}
	}
}

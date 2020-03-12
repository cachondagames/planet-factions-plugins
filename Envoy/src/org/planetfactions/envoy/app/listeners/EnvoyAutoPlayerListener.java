package org.planetfactions.envoy.app.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.planetfactions.envoy.Main;
import org.planetfactions.envoy.app.Envoy;

public class EnvoyAutoPlayerListener implements Listener
{
	private Envoy envoy = Envoy.getEnvoyEvent();
	private Main plugin;
	public EnvoyAutoPlayerListener(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void playerJoinCheck(PlayerJoinEvent p)
	{
		if(Bukkit.getOnlinePlayers().size() >= envoy.getNumberPlayers())
		{
			envoy.setPlayersReached(true);
		}
		else
		{
			envoy.setPlayersReached(false);
		}
	}
	
	@EventHandler
	public void playerLeaveCheck(PlayerQuitEvent p)
	{
		if(Bukkit.getOnlinePlayers().size() < envoy.getNumberPlayers() - 1)
		{
			envoy.setPlayersReached(false);
		}
	}
	
	@EventHandler
	public void playerKickCheck(PlayerKickEvent p)
	{
		if(Bukkit.getOnlinePlayers().size() < envoy.getNumberPlayers() - 1)
		{
			envoy.setPlayersReached(false);
		}
	}
}

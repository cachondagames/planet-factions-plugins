package org.planetfactions.envoy.app.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.planetfactions.envoy.Main;
import org.planetfactions.envoy.app.Envoy;

public class EnvoyAutoStart implements Listener
{
	private Envoy envoy = Envoy.getEnvoyEvent();
	public EnvoyAutoStart(Main plugin)
	{
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
}

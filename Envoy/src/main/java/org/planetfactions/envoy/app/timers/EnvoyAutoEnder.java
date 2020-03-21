package org.planetfactions.envoy.app.timers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.planetfactions.envoy.app.Envoy;

public class EnvoyAutoEnder extends BukkitRunnable
{
	private Envoy envoy = Envoy.getEnvoyEvent();
	public EnvoyAutoEnder()
	{
		
	}
	
	@Override
	public void run() 
	{
		envoy.endEnvoy();
		List<Player> players = Bukkit.getWorlds().get(0).getPlayers();	
		for(Player p : players)
		{
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[Envoy] &bAn Envoy Event Has Ended!"));
		}
		this.cancel();
	}
}
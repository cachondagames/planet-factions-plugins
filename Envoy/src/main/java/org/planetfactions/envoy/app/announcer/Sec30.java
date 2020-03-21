package org.planetfactions.envoy.app.announcer;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Sec30 extends BukkitRunnable
{
	public Sec30()
	{
		
	}
	
	@Override
	public void run() 
	{
		List<Player> list = Bukkit.getWorlds().get(0).getPlayers();
		for(Player p : list)
		{
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] &bAn Envoy Event is going to begin in 30 Seconds!"));
		}
	}
}

package org.planetfactions.envoy.app.timers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EnvoyAutoAnnouncer extends BukkitRunnable
{
	private int time;
	public EnvoyAutoAnnouncer(int t) 
	{
		time = t;
	}
	
	@Override
	public void run() 
	{
		List<Player> players = Bukkit.getWorlds().get(0).getPlayers();
		for(Player p : players) 
		{
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] &aAn Envoy is going to start in: " + time +"!"));
		}
	}
	
	public void setTime(long l)
	{
		time = (int) l/20;
	}
}

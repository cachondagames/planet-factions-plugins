package org.planetfactions.envoy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.planetfactions.envoy.app.Envoy;
import org.planetfactions.envoy.app.announcer.EnvoyAutoAnnouncer;
import org.planetfactions.envoy.app.listeners.EnvoyAutoPlayerListener;
import org.planetfactions.envoy.app.listeners.EnvoyOpenListener;
import org.planetfactions.envoy.app.listeners.Tier1Listener;
import org.planetfactions.envoy.app.listeners.Tier2Listener;
import org.planetfactions.envoy.app.listeners.Tier3Listener;
import org.planetfactions.envoy.app.timers.EnvoyStarter;
import org.planetfactions.envoy.commands.EnvoyAutoCommands;
import org.planetfactions.envoy.commands.EnvoyCommand;


public class Main extends JavaPlugin
{
	Envoy envoy = Envoy.getEnvoyEvent();
	public void onEnable()
	{
		saveDefaultConfig();
		envoy.setplugin(this);
		envoy.loadConfig();
		new EnvoyCommand(this);
		new EnvoyOpenListener(this);
		new EnvoyAutoPlayerListener(this);
		new EnvoyAutoCommands(this);
		new Tier1Listener(this);
		new Tier2Listener(this);
		new Tier3Listener(this);
		if(envoy.getAutoStart())
		{
			if(envoy.getAnnounce())
			{
				EnvoyAutoAnnouncer.announce();
			}
			BukkitTask concheck = new EnvoyStarter().runTaskTimer(this, envoy.getAutoStartTime(), envoy.getAutoStartTime());
			envoy.setConditionTaskID(concheck.getTaskId());
		}
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

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
import org.planetfactions.envoy.app.commands.EnvoyAutoCommands;
import org.planetfactions.envoy.app.commands.EnvoyCommand;


public class Main extends JavaPlugin
{
	Envoy envoy = Envoy.getEnvoyEvent();
	public void onEnable()
	{
		getConfig().options().copyDefaults(true);
		saveConfig();
		envoy.setplugin(this);
		envoy.loadConfig();
		new EnvoyCommand(this);
		new EnvoyOpenListener(this);
		new EnvoyAutoPlayerListener(this);
		new EnvoyAutoCommands(this);
		new Tier1Listener(this);
		new Tier2Listener(this);
		new Tier3Listener(this);
		try
		{
			if(this.getServer().getPluginManager().getPlugin("Factions").isEnabled())
			{
				envoy.setWarZone(true);
				this.getServer().getConsoleSender().sendMessage("[Envoy] Only Spawning the Warzone option is now enabled!");
			}
		}
		catch(NullPointerException e)
		{
			envoy.setWarZone(false);
			this.getServer().getConsoleSender().sendMessage("[Envoy] Factions is not installed disabling spawn in only Warzone!");
		}
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

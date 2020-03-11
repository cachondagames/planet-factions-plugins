package org.planetfactions.envoy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.planetfactions.envoy.app.Envoy;
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
	FileConfiguration config = getConfig();
	Envoy envoy = Envoy.getEnvoyEvent();
	public void onEnable()
	{
		new EnvoyCommand(this);
		new EnvoyOpenListener(this);
		new EnvoyAutoPlayerListener(this);
		new EnvoyAutoCommands(this);
		new Tier1Listener(this);
		new Tier2Listener(this);
		new Tier3Listener(this);
		if(envoy.getAutoStart())
		{
			BukkitTask concheck = new EnvoyStarter().runTaskTimer(this, 36000L, 36000L);
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

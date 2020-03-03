package org.planetfactions.envoy;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.planetfactions.envoy.app.listeners.EnvoyAutoStart;
import org.planetfactions.envoy.app.listeners.EnvoyOpenEvent;
import org.planetfactions.envoy.app.listeners.Schedule;
import org.planetfactions.envoy.commands.RandEnvoyCommand;


public class Main extends JavaPlugin
{
	public void onEnable()
	{
		new RandEnvoyCommand(this);
		new EnvoyOpenEvent(this);
		new EnvoyAutoStart(this);
		@SuppressWarnings("unused")
		BukkitTask starter = new Schedule(this).runTaskTimer(this, 100L, 1000L);
	}
}

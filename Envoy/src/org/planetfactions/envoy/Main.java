package org.planetfactions.envoy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.planetfactions.envoy.app.listeners.EnvoyAutoPlayerListener;
import org.planetfactions.envoy.app.listeners.EnvoyOpenListener;
import org.planetfactions.envoy.app.schedule.Schedule;
import org.planetfactions.envoy.commands.EnvoyAutoCommands;
import org.planetfactions.envoy.commands.EnvoyCommand;


public class Main extends JavaPlugin
{
	FileConfiguration config = getConfig();
	public void onEnable()
	{
        config.addDefault("youAreAwesome", true);
        config.options().copyDefaults(true);
        saveConfig();
		new EnvoyCommand(this);
		new EnvoyOpenListener(this);
		new EnvoyAutoPlayerListener(this);
		new EnvoyAutoCommands(this);
		@SuppressWarnings("unused")
		BukkitTask starter = new Schedule(this).runTaskTimer(this, 100L, 1000L);
	}
}

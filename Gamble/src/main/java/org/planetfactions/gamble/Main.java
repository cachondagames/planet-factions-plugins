package org.planetfactions.gamble;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.planetfactions.gamble.listeners.Listeners;
import org.planetfactions.gamble.spawn.CommandSpawner;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin
{
	private static Economy eco = null;
	public void onEnable()
	{
		setupEco();
		new CommandSpawner(this);
		new Listeners(this);
	}
	
	private boolean setupEco() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        eco = rsp.getProvider();
        return eco != null;
    }
	
	public static Economy getEco()
	{
		return eco;
	}
	
	
}

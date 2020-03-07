package org.planetfactions.shoes;

import org.bukkit.plugin.java.JavaPlugin;
import org.planetfactions.shoes.command.Shoes;

public class Main extends JavaPlugin 
{
	public void onEnable()
	{
		new Shoes(this);
	}
}

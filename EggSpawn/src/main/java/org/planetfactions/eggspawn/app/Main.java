package org.planetfactions.eggspawn.app;

import org.bukkit.plugin.java.JavaPlugin;
import org.planetfactions.eggspawn.app.recipes.Recipes;


public class Main extends JavaPlugin
{
	public void onEnable()
	{
		new Recipes(this);
	}
}

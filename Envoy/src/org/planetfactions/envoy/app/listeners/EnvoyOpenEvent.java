package org.planetfactions.envoy.app.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.planetfactions.envoy.Main;
import org.planetfactions.envoy.app.Envoy;

public class EnvoyOpenEvent implements Listener
{
	private Envoy envoy = Envoy.getEnvoyEvent();
	public EnvoyOpenEvent(Main plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void EnvoyInteract(PlayerInteractEvent e) // Event Handler for player interactions
	{
		if(envoy.getEnvoyActiveState()) // Checks if the envoy is currently active
		{
			if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) // Checks for right click on blocks
			{
				ArrayList<Block> blocks = envoy.getChestLocations();
				for(Block b : blocks) // Checks if the block that was right clicked was one placed by envoy create
				{
					if(e.getClickedBlock().equals(b))
					{
						if(e.getClickedBlock().getType().equals(Material.CHEST)) // Checks for tiers as below
						{
							Bukkit.broadcastMessage("Tier1 Works");
						}
						else if(e.getClickedBlock().getType().equals(Material.ENDER_CHEST))
						{
							Bukkit.broadcastMessage("Tier2 Works");
						}
						else
						{
							Bukkit.broadcastMessage("Tier3 Works");
						}
					}
				}
			}
		}
	}	
}

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
import org.planetfactions.envoy.app.events.EnvoyTier1OpenEvent;
import org.planetfactions.envoy.app.events.EnvoyTier2OpenEvent;
import org.planetfactions.envoy.app.events.EnvoyTier3OpenEvent;

public class EnvoyOpenListener implements Listener
{
	EnvoyTier1OpenEvent Tier1Open;
	EnvoyTier2OpenEvent Tier2Open;
	EnvoyTier3OpenEvent Tier3Open;
	private Envoy envoy = Envoy.getEnvoyEvent();
	public EnvoyOpenListener(Main plugin)
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
							Tier1Open = new EnvoyTier1OpenEvent(e.getPlayer(), e.getClickedBlock().getLocation());
							Bukkit.getPluginManager().callEvent(Tier1Open);
							e.setCancelled(true);
						}
						else if(e.getClickedBlock().getType().equals(Material.ENDER_CHEST))
						{
							Tier2Open = new EnvoyTier2OpenEvent(e.getPlayer(), e.getClickedBlock().getLocation());
							Bukkit.getPluginManager().callEvent(Tier2Open);
							e.setCancelled(true);
						}
						else
						{
							Tier3Open = new EnvoyTier3OpenEvent(e.getPlayer(), e.getClickedBlock().getLocation());
							Bukkit.getPluginManager().callEvent(Tier3Open);
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}	
}

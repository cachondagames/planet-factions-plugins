package org.planetfactions.envoy.app.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EnvoyTier2OpenEvent extends Event
{
	
	private final Player player;
	private final Location location;
	
	public EnvoyTier2OpenEvent(Player player, Location location)
	{
		this.player = player;
		this.location = location;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	private static final HandlerList HANDLERS = new HandlerList();
	@Override
	public HandlerList getHandlers() 
	{
		return HANDLERS;
	}
	
    public static HandlerList getHandlerList() 
    {
        return HANDLERS;
    }
}

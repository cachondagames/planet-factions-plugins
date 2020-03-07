package org.planetfactions.envoy.app.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EnvoyTier3OpenEvent extends Event
{
	
	private final Player player;
	
	public EnvoyTier3OpenEvent(Player player)
	{
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return player;
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

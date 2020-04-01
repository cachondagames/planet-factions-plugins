package org.planetfactions.gamble.spawn;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.planetfactions.gamble.Main;
import org.planetfactions.gamble.data.Data;

public class CommandSpawner implements CommandExecutor
{
	
	public CommandSpawner(Main plugin) 
	{
		plugin.getCommand("spawn").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player play = (Player) sender;
		Entity ent = play.getWorld().spawnEntity(play.getLocation(), EntityType.VILLAGER);
		ent.setCustomName("MONEY DUDE!!");
		Data.getData().addEnt(ent);
		return true;
	}
}

package org.planetfactions.envoy.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.planetfactions.envoy.Main;

public class EnvoyAutoCommands  implements CommandExecutor
{
	public EnvoyAutoCommands(Main plugin)
	{
		plugin.getCommand("envoyauto").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender player, Command cmd, String lbl, String[] args) {
		Player p = (Player) player;
		if(args.length < 1)
		{
			if(p.hasPermission("envoy.help"))
			{
				player.sendMessage(ChatColor.GREEN + "/envoyauto create: Makes a new envoy event");
				player.sendMessage(ChatColor.GREEN + "/envoyauto end: Ends the current envoy event");
				player.sendMessage(ChatColor.GREEN + "/envoyauto inbound: Sets the inbounds (double)");
				player.sendMessage(ChatColor.GREEN + "/envoyauto outbound: Sets the outbounds (double)");
				player.sendMessage(ChatColor.GREEN + "/envoyauto debug: Turns on debug mode to the console");
				player.sendMessage(ChatColor.GREEN + "/envoyauto distance: Sets the minimum distance to which envoys can be next to each other");
				return true;
			}
		}
		return false;
	}
}

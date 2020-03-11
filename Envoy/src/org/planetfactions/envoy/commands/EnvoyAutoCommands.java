package org.planetfactions.envoy.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.planetfactions.envoy.Main;
import org.planetfactions.envoy.app.Envoy;

public class EnvoyAutoCommands  implements CommandExecutor
{
	private Envoy envoy = Envoy.getEnvoyEvent();
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
				player.sendMessage(ChatColor.GREEN + "/envoyauto enable: Toggles the ability to have Envoys auto start");
				player.sendMessage(ChatColor.GREEN + "/envoyauto players: Sets the amount of players needed to start an Envoy");
				player.sendMessage(ChatColor.GREEN + "/envoyauto crates: Sets the amount of crates that will spawn on an auto start");
				return true;
			}
			else
			{
				player.sendMessage(ChatColor.RED + "You do not have permissions for this command!");
				return false;
			}
		}
		else
		{
			switch(args[0].toLowerCase())
			{
			case "crates":
				if(player.hasPermission("envoy.create"))
				{
					try
					{
						envoy.setAutoStartCrates(Integer.parseInt(args[1]));
						player.sendMessage(ChatColor.GREEN + "You have set the number of crates to spawn on an auto start to: " + envoy.getAutoStartCrates());
						return true;
					}
					catch(NumberFormatException | ArrayIndexOutOfBoundsException e)
					{
						if(e instanceof ArrayIndexOutOfBoundsException)
						{
							player.sendMessage(ChatColor.GREEN + "The current amount of crates is: " + envoy.getAutoStartCrates());
							return true;
						}
						else
						{
							player.sendMessage("The amount of crates needs to be an integer!");
							return false;
						}
					}
				}
				else
				{
					player.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
					return false;
				}
			
			case "players":
				if(player.hasPermission("envoy.create"))
				{
					try
					{
						envoy.setNumberPlayers(Integer.parseInt(args[1]));
						String s = "&a[Envoy] &cYou have set the number of players to auto start an Envoy to; " + "&c" + envoy.getNumberPlayers();
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
						return true;
					}
					catch(NumberFormatException | ArrayIndexOutOfBoundsException e)
					{
						if(e instanceof ArrayIndexOutOfBoundsException)
						{
							String s = "&a[Envoy] &cThe current number of players needed to start an Envoy is: " + "&c" + envoy.getNumberPlayers();
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
							return true;
						}
						else
						{
							String s = "&a[Envoy] &4You need to set the number of players to an Integer!";
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
							return false;
						}
					}
				}
			case "enable":
				if(envoy.getEnvoyActiveState())
				{
					
				}
			default:
				String s = "&a[Envoy] &4Invalid argument use /envoyauto help to see usage!";
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
				return true;
			}
		}
	}
}

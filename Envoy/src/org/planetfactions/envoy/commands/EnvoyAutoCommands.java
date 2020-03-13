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
				player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoyauto autostart: Toggles the ability to have Envoys auto start"));
				player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoyauto players: Sets the amount of players needed to start an Envoy"));
				player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoyauto crates: Sets the amount of crates that will spawn on an auto start"));
				player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoyauto starttime: Sets how long the server will wait to auto start an Envoy in ticks"));
				player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoyauto endtime: Sets how long the server will wait to auto end an Envoy in ticks"));
				player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoyauto autoend: Sets how long the server will wait to auto end an Envoy in ticks"));
				player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoyauto announce: Sets if the server will announce Envoys!"));
				return true;
			}
			else
			{
				player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
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
						player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cYou have set the number of crates to spawn on an auto start to: " + envoy.getAutoStartCrates()));
						return true;
					}
					catch(NumberFormatException | ArrayIndexOutOfBoundsException e)
					{
						if(e instanceof ArrayIndexOutOfBoundsException)
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe current amount of crates is: " + envoy.getAutoStartCrates()));
							return true;
						}
						else
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4The amount of crates needs to be an integer!"));
							return false;
						}
					}
				}
				else
				{
					player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
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
				else
				{
					player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
					return false;
				}
			case "autostart":
				if(player.hasPermission("envoy.create"))
				{
					try 
					{
						if(args[1].equalsIgnoreCase("true"))
						{
							if(envoy.getPlugin().getConfig().getBoolean("autostartenabled"))
							{
								player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server is already autostarting Envoys!"));
								return true;
							}
							else
							{
								envoy.AutoStartSelector(true);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server will now auto start Envoys!"));
								return true;
							}
						}
						else if(args[1].equalsIgnoreCase("false"))
						{
							if(envoy.getPlugin().getConfig().getBoolean("autostartenabled"))
							{
								envoy.AutoStartSelector(false);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server will now stop auto starting Envoys!"));
								return true;
							}
							else
							{
								player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4The server is already not auto starting Envoys!"));
								return true;
							}
						}
						else
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4Invalid arguments! Use /envoyauto for help!"));
							return false;
						}
					}
					catch(ArrayIndexOutOfBoundsException e)
					{
						if(envoy.getPlugin().getConfig().getBoolean("autostartenabled"))
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server is currently auto starting Envoys!"));
							return true;
						}
						else
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server is not currently auto starting Envoys!"));
							return true;
						}
					}
				}
				else
				{
					player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
					return false;
				}
			case "endtime":
				if(player.hasPermission("envoy.create"))
				{
					try 
					{
						if(Long.parseLong(args[1]) > 0)
						{
							envoy.setAutoEndTime(Long.parseLong(args[1]));
							player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe auto end time for Envoys is now: " + Long.parseLong(args[1]) +"!"));
							return true;
						}
						else
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You cant have the end time be negative!"));
							return true;
						}
					}
					catch(NumberFormatException | ArrayIndexOutOfBoundsException e)
					{
						if(e instanceof ArrayIndexOutOfBoundsException)
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] &cThe current Envoy auto end time is: " + envoy.getAutoEndTime() + "!"));
							return true;
						}
						else
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] &4You need to set the Envoy auto end time as a long in TICKS!"));
							return true;
						}
					}
				}
				else
				{
					player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
					return false;
				}
			case "starttime":
				if(player.hasPermission("envoy.create"))
				{
					try 
					{
						if(Long.parseLong(args[1]) > 0)
						{
							envoy.setAutoStartTime(Long.parseLong(args[1]));
							player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe auto start time for Envoys is now: " + Long.parseLong(args[1]) +"!"));
							return true;
						}
						else
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You cant have the Envoy start time be negative!"));
							return true;
						}
					}
					catch(NumberFormatException | ArrayIndexOutOfBoundsException e)
					{
						if(e instanceof ArrayIndexOutOfBoundsException)
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] &cThe current Envoy auto start time is: " + envoy.getAutoStartTime() + "!"));
							return true;
						}
						else
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] &4You need to set the Envoy auto start time as a long in TICKS!"));
							return true;
						}
					}
				}
				else
				{
					player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
					return false;
				}
			case "announce":
				if(player.hasPermission("envoy.create"))
				{
					try 
					{
						if(args[1].equalsIgnoreCase("true"))
						{
							if(envoy.getPlugin().getConfig().getBoolean("announceenvoys"))
							{
								player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server is already announcing Envoys!"));
								return true;
							}
							else
							{
								envoy.toggleAnnounce(true);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server will now announce Envoys!"));
								return true;
							}
						}
						else if(args[1].equalsIgnoreCase("false"))
						{
							if(envoy.getPlugin().getConfig().getBoolean("announceenvoys"))
							{
								envoy.toggleAnnounce(false);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server will now stop announcing Envoys!"));
								return true;
							}
							else
							{
								player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4The server is already not announcing Envoys!"));
								return true;
							}
						}
						else
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4Invalid arguments! Use /envoyauto for help!"));
							return false;
						}
					}
					catch(ArrayIndexOutOfBoundsException e)
					{
						if(envoy.getPlugin().getConfig().getBoolean("announceenvoys"))
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server is currently announcing Envoys!"));
							return true;
						}
						else
						{
							player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server is not currently announcing Envoys!"));
							return true;
						}
					}
				}
				else
				{
					player.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
					return false;
				}
			default:
				String s = "&a[Envoy] &4Invalid argument use /envoyauto help to see usage!";
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
				return true;
			}
		}
	}
}

package org.planetfactions.envoy.commands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.planetfactions.envoy.Main;
import org.planetfactions.envoy.app.Envoy;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;


public class EnvoyCommand implements CommandExecutor
{
	private Envoy envoy = Envoy.getEnvoyEvent();
	public EnvoyCommand(Main plugin)
	{
		plugin.getCommand("envoy").setExecutor(this);
	}
	@Override
	public boolean onCommand(CommandSender send, Command cmd, String lbl, String[] args) 
	{
		if(send instanceof Player)
		{
			Player play = (Player) send;
			if (args.length <= 0) // Check to see if the command was "envoy"
			{
				if(play.hasPermission("envoy.help")) // Displays help for the command
				{
					play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoy create:" + "\n" + "&a[Envoy] &9Makes a new envoy event"));
					play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoy end:" + "\n" + "&a[Envoy] &9Ends the current envoy event"));
					play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoy inbound:" + "\n" + "&a[Envoy] &9Sets the inbounds (double)"));
					play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoy outbound:" + "\n" + "&a[Envoy] &9Sets the outbounds (double)"));
					play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoy debug:" + "\n" + "&a[Envoy] &9Turns on debug mode to the console"));
					play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoy distance:" + "\n" + "&a[Envoy] &9Sets the minimum distance to which envoys can be next to each other"));
					play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoy reload:" + "\n" + "&a[Envoy] &9reloads the config"));
					play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c/envoy warzone:" + "\n" + "&a[Envoy] &9Sets if Envoys can only spawn in the warzone"));
					return true;
				}
				else
				{
					play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
					return false;
				}
			}
			else
			{
				switch (args[0].toLowerCase()) // Checks for argument given after /envoy
				{
				case "end": // Ends "instance" of the envoy
					if(play.hasPermission("envoy.end"))
					{
						if(envoy.getEnvoyActiveState()) // Makes sure that the envoy is running
						{
							envoy.endEnvoy();
							play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cYou have ended the current Envoy!"));
							return true;
						}
						else
						{
							play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThere is not a envoy currently running!"));
							return true;
						}
					}
					else
					{
						play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
						return false;
					}

				case "debug": // Turns on debug mode for plugin *STILL IMPLEMENTING*
					if (play.hasPermission("envoy.debug")) // Check for perms
					{
						if(envoy.getDebugState())
						{	
							play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cDebug mode for envoy disabled!"));
							return true;
						}
						else
						{
							envoy.toggleDebugState();
							play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cDebug mode for envoy enabled! Check Console for Details."));
							return true;
						}
					}
					else
					{
						play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
						return false;
					}
				case "inbound":
					if (play.hasPermission("envoy.create")) // Check for perms
					{
						try
						{
							if(!(Integer.parseInt(args[1]) < 0)) // Catches if the player supplies a negative number
								envoy.setInnerBound(Integer.parseInt(args[1]));
							play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cYou have set the Inner Bound to " + envoy.getInnerBound()));
							return true;
						}
						catch(NumberFormatException | ArrayIndexOutOfBoundsException e) // Catches if the player supplies a non integer number or failed to supply bound
						{
							if(e instanceof ArrayIndexOutOfBoundsException) 
							{
								play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe current inner bound is: " + envoy.getInnerBound()));
								return false;
							}
							else
							{
								play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4The bound needs to be an Integer"));
								return false;
							}
						}
					}
					else
					{
						play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
						return false;
					}
				case "outbound":
					if (play.hasPermission("envoy.create")) // Check for perms
					{
						try
						{
							if(!(Integer.parseInt(args[1]) < 0)) // Catches if the player supplies a negative number
								envoy.setOutterBound(Integer.parseInt(args[1]));
							play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cYou have set Outer Bound to " + envoy.getOutterBound()));
							return true;
						}
						catch(NumberFormatException | ArrayIndexOutOfBoundsException  e) // Catches if the player supplies a non integer number or didnt supply the bound
						{
							if(e instanceof ArrayIndexOutOfBoundsException)
							{
								play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe current outer bound is: " + envoy.getOutterBound()));
								return false;
							}
							else
							{
								play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4The bound needs to be an Integer"));
								return false;
							}
						}
					}
					else
					{
						play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
						return false;
					}
				case "create":
					if (play.hasPermission("envoy.create")) // Checks for perms
					{
						if(envoy.getOutterBound() == 0) // Ensures sure the bound has been set
						{
							play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You cant start an envoy event without setting your outer bounds!"));
							return false;
						}
						if(envoy.getInnerBound() == 0) // See above
						{
							play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You cant start an envoy event without setting your inner bounds!"));
							return false;
						}				
						if(envoy.getOutterBound()<envoy.getInnerBound()) // Ensures that the bounds are valid
						{
							play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You cant have your Inner Bound be greater than your Outer!"));
							return false;
						}
						else
						{
							try
							{
								if(!envoy.getEnvoyActiveState()) // Ensures that an envoy event is not already active
								{
									int i = envoy.createEnvoy(Integer.parseInt(args[1]));
									if(i == 1)
									{
										play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cYou have started a new envoy!" + "\n" + "Spawning in : " +  Integer.parseInt(args[1]) + " Chests"));
										return true;
									}
									else
									{
										return false;
									}
								}
								else
								{
									play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4An envoy is already active!"));
									return false;
								}
							}
							catch(NumberFormatException | ArrayIndexOutOfBoundsException e) // Catches if the player supplied a number that is not an integer or failed to supply proper arguments
							{
								if(e instanceof ArrayIndexOutOfBoundsException)
								{
									play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4Invalid Arugments" + "\n" + "Usage: /envoy create number of crates"));
									return false;
								}
								else
								{
									play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4The number of crates needs to be an integer!"));
									return false;
								}
							}
						}
					}
					else
					{
						play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
						return false;
					}

				case "distance": // For setting distance
					if(play.hasPermission("envoy.create")) // Check for perms 
					{
						try
						{
							envoy.setDistance(Integer.parseInt(args[1]));
							play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cYou have set the min distance to " + Integer.parseInt(args[1])));
							return true;
						}
						catch(NumberFormatException | ArrayIndexOutOfBoundsException e) // Catches if the player supplied a number that is not an integer or failed to supply proper arguments
						{
							if(e instanceof ArrayIndexOutOfBoundsException)
							{
								String s = "The current distance is " + Integer.toString(envoy.getDistance()) + " blocks";
								play.sendMessage(ChatColor.RED + ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &c" + s));
								return true;
							}
							else
							{
								play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4The distance needs to be an integer!"));
								return false;
							}
						}
					}
					else
					{
						play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
						return false;
					}
				case "reload":
					if(play.hasPermission("envoy.create"))
					{
						if(!envoy.getEnvoyActiveState())
						{
							envoy.reloadConfig();
							play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cConfig Reloaded!"));
							return true;
						}
						else
						{
							play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4Please end the current Envoy before reloading!"));
							return true;
						}
					}
					else
					{
						play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
						return false;
					}
				case "warzone":
					if(play.hasPermission("envoy.create"))
					{
						try 
						{
							if(args[1].equalsIgnoreCase("true"))
							{
								if(envoy.getPlugin().getConfig().getBoolean("onlyspawninwarzone"))
								{
									play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server is already only spawing Envoys in the Warzone!"));
									return true;
								}
								else
								{
									envoy.setWarZone(true);
									play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server will now only spawn Envoys in the Warzone!"));
									return true;
								}
							}
							else if(args[1].equalsIgnoreCase("false"))
							{
								if(envoy.getPlugin().getConfig().getBoolean("onlyspawninwarzone"))
								{
									envoy.setWarZone(false);
									play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server will now stop only spawning Envoys in the Warzone!"));
									return true;
								}
								else
								{
									play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4The server is already not only spawning Envoys in the Warzone!"));
									return true;
								}
							}
							else
							{
								play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4Invalid arguments! Use /envoyauto for help!"));
								return false;
							}
						}
						catch(ArrayIndexOutOfBoundsException e)
						{
							if(envoy.getPlugin().getConfig().getBoolean("onlyspawninwarzone"))
							{
								play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server is currently only spawning Envoys in the Warzone!"));
								return true;
							}
							else
							{
								play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &cThe server is not currently only spawning Envoys in the Warzone!"));
								return true;
							}
						}
					}
					else
					{
						play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4You do not have permissions for this command!"));
						return false;
					}
				case "test": 
					FLocation location = new FLocation(play.getLocation());
					Faction faction = Board.getInstance().getFactionAt(location);
					play.sendMessage(faction.getId());
					return true;
				default: // Fallback send for anything that is not defined above
					play.sendMessage(ChatColor.translateAlternateColorCodes('&' , "&a[Envoy] &4Invalid argument use /envoy for help"));
					return false;
				}
			}
		}
		else
		{
			send.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] &4Console cannot execute this command!"));
			return false;
		}
	}
}

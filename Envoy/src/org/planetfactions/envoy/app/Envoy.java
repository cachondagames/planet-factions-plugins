package org.planetfactions.envoy.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitTask;
import org.planetfactions.envoy.Main;
import org.planetfactions.envoy.app.timers.EnvoyAutoEnder;
import org.planetfactions.envoy.app.timers.EnvoyStarter;

public class Envoy
{
	private HashMap<Player, Integer> envoyclicks = new HashMap<Player, Integer>();
	private ArrayList<Player> envoyiter = new ArrayList<Player>();
	private ArrayList<Block> chestlocations = new ArrayList<Block>();
	private Main plugin;
	private boolean DEBUGSTATE = false;
	private double OUTBOUND = 0;
	private double INBOUND = 0;
	private int DISTANCE = 10;
	private boolean ACTIVE = false;
	private static Envoy Envoy = new Envoy();
	private int NUMPLAYERSNEEDED = 1;
	private int AUTOSTARTCRATES = 5;
	private boolean PLAYERSREACHED = false;
	private int GETLOCATIONON = 0;
	private int CONTASKID = 0;
	private long AUTOENDTIME = 36000L;
	private int ENDERID = 0;
	private boolean AUTOSTART = true;

	public void createEnvoy(int numcrates) // Initial call to spawn all the crates
	{	
		Location spawn = Bukkit.getWorld("world").getSpawnLocation(); // Gets world spawn
		ACTIVE = true;
		while(numcrates > 0)
		{
			Bukkit.getScheduler().cancelTask(getConditionTaskID());
			BukkitTask autoender = new EnvoyAutoEnder().runTaskLater(Bukkit.getPluginManager().getPlugin("Envoy"), AUTOENDTIME);
			double xVal = generateXValue();
			double zVal = generateZValue();
			Location temp = new Location(Bukkit.getWorlds().get(0), xVal, 100, zVal); // Generation of our random location in the world
			Location chest = spawn.add(temp); // Sets the location near the spawn within the bounds as defined by INBOUND and OUTBOUND
			double yVal = generateYValue(chest); // Makes sure the chest is on the ground **Possible bug with chest spawning in ground if the spawn is at y=100 or more**
			chest.setY(yVal);
			if(isLocationGood(chest.getBlock())) // Makes sure that the chest is location is within our bounds as defined by line 76 "10"
			{
				chestlocations.add(chest.getBlock()); // Sets location to what ever tier as generated by chooseTier()
				numcrates--;
			}				
		}
		chooseTier(chestlocations.get(0).getLocation());
		List<Player> list = Bukkit.getWorlds().get(0).getPlayers();
		String s = "&a[Envoy] &bA Envoy Event Has Started!" + System.lineSeparator() + "&a[Envoy] &dThe First Chest is at: " + "&4" + chestlocations.get(0).getX() + " " + "&4" + chestlocations.get(0).getY() + " " + "&4" + chestlocations.get(0).getZ();
		for(Player p : list)
		{
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
		}
		Firework fw = (Firework) chestlocations.get(GETLOCATIONON).getWorld().spawnEntity(chestlocations.get(GETLOCATIONON).getLocation(), EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		Random r = new Random();   
		Type type = Type.BALL_LARGE;       
		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.AQUA).withFade(Color.PURPLE).with(type).trail(r.nextBoolean()).build();
		fwm.addEffect(effect);
		fwm.setPower(3);
		fw.setFireworkMeta(fwm);        
		if(DEBUGSTATE)
		{
			for(Block b : chestlocations)
			{
				Bukkit.broadcastMessage(ChatColor.AQUA + "Envoy Location: " + b.getType() + " at " + b.getX() + " " + b.getY() + " " + b.getZ());
			}
		}
	}

	public Block chooseTier(Location l) // Randomly selects tiers 60:30:10 distribution with 100 over hang
	{
		Random ran1 = new Random();
		int num = ran1.nextInt(100) + 1;
		if(num < 60)
		{
			l.getBlock().setType(Material.CHEST);
			GETLOCATIONON++;
			return l.getBlock();
		}
		else if(num >= 60 && num <= 91)
		{
			l.getBlock().setType(Material.ENDER_CHEST);
			GETLOCATIONON++;
			return l.getBlock();
		}
		else
		{
			l.getBlock().setType(Material.BEACON);
			GETLOCATIONON++;
			return l.getBlock();
		}
	}

	public void AutoStartSelector(boolean b)
	{
		if(b)
		{
			AUTOSTART = true;
			plugin.getConfig().set("autostartenabled", true);
			plugin.saveConfig();
		}
		else
		{
			AUTOSTART = false;
			plugin.getServer().getScheduler().cancelTasks(plugin);
			plugin.getConfig().set("autostartenabled", false);
			plugin.saveConfig();
		}
	}

	public boolean possibleLocations(int numcrates) // Calculates the possible amount of envoys that can be placed within 
	{
		int outboundarea = (int) Math.pow((OUTBOUND*2),2);
		int inboundarea = (int) Math.pow((INBOUND*2),2);
		int distancearea = (int) Math.pow((DISTANCE*2),2);
		int blkamount = outboundarea - inboundarea;
		if(blkamount/distancearea >= numcrates)
		{
			if(DEBUGSTATE) // DEBUG
			{
				System.out.println("You are only able to spawn: " + blkamount/distancearea + " Crates");		
			}
			return true;
		}
		else
			if(DEBUGSTATE) // DEBUG
			{				
				System.out.println("You are only able to spawn: " + blkamount/distancearea + " Crates");
			}
		return false;
	}

	public boolean isLocationGood(Block b) // Logic for ensuring the location that is picked is within the bounds * Maybe make the bound be able to be set?*
	{
		if(chestlocations.size() == 1) // If the array only has one location or none will force a true to returned
			return true;
		else if(chestlocations.size() == 0)
			return true;	
		else
		{
			for(Block block : chestlocations)
			{
				if (block.getLocation().distance(b.getLocation()) < DISTANCE) // Using distance method to ensure no chest is within 10 blocks of each other
					return false;
			}
			if(b.getLocation().distance(Bukkit.getWorld("world").getSpawnLocation()) < INBOUND) // Using distance method to ensure that negative numbers dont cause our location to be set inside of our INBOUND
				return false;

			return true;
		}
	}

	public double generateXValue() // Logic to generate random X value 
	{
		int TempX = (int) Math.round((Math.random()*OUTBOUND+1)); // First call to generate random number then rounds up and casts as integer
		double xVal = 0;
		while(TempX <= INBOUND)
			TempX = (int) Math.round((Math.random()*OUTBOUND+1)); // Generates random number then rounds up and casts as integer in a loop until it satisfy's expression
		while(TempX > INBOUND)
		{
			if(Math.round(Math.random()*100+1) <= 50) // Logic to pick either the negative or positive value of the number as both will satisfy the INBOUND
			{
				xVal = TempX;
				return xVal;
			}
			else
			{
				xVal = TempX*-1;
				return xVal;
			}
		}
		return xVal;
	}

	public double generateZValue() // See Above
	{
		int TempZ = (int) Math.round((Math.random()*OUTBOUND+1));
		double zVal = 0;
		while(TempZ <= INBOUND)
			TempZ = (int) Math.round((Math.random()*OUTBOUND+1));
		while(TempZ > INBOUND)
		{
			if(Math.round((Math.random()*100+1)) <= 50)
			{
				zVal = TempZ;
				return zVal;
			}
			else
			{
				zVal = TempZ*-1;
				return zVal;
			}
		}
		return zVal;
	}

	public double generateYValue(Location l) // Logic to bring chest to ground *Will mostly likely need improvement*
	{
		Material type = l.getBlock().getRelative(BlockFace.DOWN).getType();
		while(type.equals(Material.AIR)) // Checks if the block below is air runs until it is not
		{
			l.setY(l.getY()-1);
			type = l.getBlock().getRelative(BlockFace.DOWN).getType();
		}
		if(type.equals(Material.LONG_GRASS) || type.equals(Material.STATIONARY_WATER) || type.equals(Material.STATIONARY_LAVA))
		{
			l.setY(l.getY()-1);
		}
		return l.getY();
	}

	public void endEnvoy() //
	{
		for (int i = GETLOCATIONON - 1 ; i >=0; i--) // Removes chests that were spawned
		{
			chestlocations.get(i).setType(Material.AIR);
			if(DEBUGSTATE) // DEBUG 
				System.out.println("Removing Envoy at " + chestlocations.get(i).getX() + " " + chestlocations.get(i).getY() + " " + chestlocations.get(i).getZ());
		}
		chestlocations.clear(); // Reset chest list
		GETLOCATIONON = 0;
		ACTIVE = false;
		if(AUTOSTART)
		{
			BukkitTask concheck = new EnvoyStarter().runTaskTimer(plugin, 36000L, 36000L);
			setConditionTaskID(concheck.getTaskId());
		}
		if(plugin.getServer().getScheduler().isQueued(ENDERID))
			plugin.getServer().getScheduler().cancelTask(ENDERID);
		if(envoyiter.size() > 1)
		{
			int t = 0;
			Player p = null;
			for(int i = envoyiter.size() - 1; i > 0; i--)
			{
				if(envoyclicks.get(envoyiter.get(i)).compareTo(envoyclicks.get(envoyiter.get(i-1))) > 0)
				{
					t = i;
					p = envoyiter.get(i);
				}
				else
				{
					t = i-1;
					p = envoyiter.get(i-1);
				}
			}
			List<Player> players = Bukkit.getWorlds().get(0).getPlayers();
			for(Player player : players)
			{
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] &dThe Envoy has ended! Congraulations to: " + p.getDisplayName() + " for collecting " + t + " Envoys!"));
			}
		}
		else if(envoyiter.size() == 0)
		{
			List<Player> players = Bukkit.getWorlds().get(0).getPlayers();
			for(Player player : players)
			{
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] &dThe Envoy has ended! Nobody collected any Envoys!"));
			}
		}
		else
		{
			List<Player> players = Bukkit.getWorlds().get(0).getPlayers();
			Player p = envoyiter.get(0);
			int t = envoyclicks.get(p);
			for(Player player : players)
			{
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] &dThe Envoy has ended! Congraulations to: " + p.getDisplayName() + " for collecting " + t + " Envoys!"));
			}
		}
		envoyiter.clear();
		envoyclicks.clear();
	}

	public boolean getDebugState() // Method to get our debug state
	{
		return DEBUGSTATE;
	}

	public void toggleDebugState() // Method to toggle debug on and off
	{
		if(getDebugState())
			DEBUGSTATE = false;
		else
			DEBUGSTATE = true;
	}

	public double getInnerBound() // Method to get our Inner Bound
	{
		return INBOUND;
	}

	public void setInnerBound(double inbound)  // Method to set our inner bound
	{
		INBOUND = inbound;
		plugin.getConfig().set("inbound", inbound);
		plugin.saveConfig();
	}

	public double getOutterBound() // Method to get our outer bound
	{
		return OUTBOUND;
	}

	public void setOutterBound(double outbound) // Method to set our outer bound
	{
		OUTBOUND = outbound;
		plugin.getConfig().set("outbound", outbound);
		plugin.saveConfig();
	}

	public boolean getEnvoyActiveState() // Method to get if an envoy is active or not
	{
		return ACTIVE;
	}

	public ArrayList<Block> getChestLocations() // Method to get chest locations
	{
		return chestlocations;
	}

	public void setDistance(int d) // Method to set our minimum distance
	{
		DISTANCE = d;
		plugin.getConfig().set("distancebetweencrates", d);
		plugin.saveConfig();
	}

	public int getDistance() // Method to get our minimum distance
	{
		return DISTANCE;
	}

	public static Envoy getEnvoyEvent() // Method to get our current envoy state
	{
		return Envoy;
	}

	public int getNumberPlayers() {
		return NUMPLAYERSNEEDED;
	}

	public void setNumberPlayers(int num) {
		NUMPLAYERSNEEDED = num;
		plugin.getConfig().set("autostartplayers", num);
		plugin.saveConfig();
	}

	public int getAutoStartCrates()
	{
		return AUTOSTARTCRATES;
	}

	public void setAutoStartCrates(int i)
	{
		AUTOSTARTCRATES = i;
		plugin.getConfig().set("autostartcratenum", i);
		plugin.saveConfig();
	}

	public boolean getPlayersReached()
	{
		return PLAYERSREACHED;
	}

	public void setPlayersReached(boolean b)
	{
		PLAYERSREACHED = b;
	}

	public int getLocationOn() 
	{
		return GETLOCATIONON;
	}

	public int getConditionTaskID() 
	{
		return CONTASKID;
	}

	public void setConditionTaskID(int taskid)
	{
		CONTASKID = taskid;
	}

	public void setEnderID(int taskid)
	{
		ENDERID = taskid;
	}

	public boolean getAutoStart()
	{
		return AUTOSTART;
	}

	public void setplugin(Main main) 
	{
		plugin = main;	
	}

	public Main getPlugin()
	{
		return plugin;
	}

	public void loadConfig()
	{
		INBOUND = plugin.getConfig().getInt("inbound");
		OUTBOUND = plugin.getConfig().getInt("outbound");
		DISTANCE = plugin.getConfig().getInt("distancebetweencrates");
		AUTOSTARTCRATES = plugin.getConfig().getInt("autostartcratenum");
		NUMPLAYERSNEEDED = plugin.getConfig().getInt("autostartplayers");
		AUTOSTART = plugin.getConfig().getBoolean("autostartenabled");
	}

	public void setEnvoyClick(Player player)
	{
		if(envoyclicks.get(player) == null)
		{
			envoyclicks.put(player, 1);
			if(envoyiter.contains(player))
			{
			}
			else
			{
				envoyiter.add(player);
			}
		}
		else
		{
			int t = envoyclicks.get(player) + 1;
			envoyclicks.replace(player, t);
			if(envoyiter.contains(player))
			{
			}
			else
			{
				envoyiter.add(player);
			}

		}
	}
}

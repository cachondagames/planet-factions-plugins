package org.planetfactions.gamble.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Data 
{
	private ArrayList<Entity> spawns = new ArrayList<Entity>();
	private HashMap<Player, Double> effects = new HashMap<Player, Double>(); 
	private static Data data = new Data();

	public static Data getData()
	{
		return data;
	}

	public boolean addEnt(Entity e)
	{
		if(spawns.add(e))
		{
			return true;
		}
		else
			return false;
	}

	public ArrayList<Entity> getSpawns()
	{
		return spawns;
	}

	public void addPlayer(Player p, double i)
	{
		effects.put(p, i);
	}

	public void removePlayer(Player p)
	{
		effects.remove(p);
	}

	public double getEffect(Player p)
	{
		return effects.get(p);
	}

	public boolean isEffect(Player p)
	{
		return effects.containsKey(p);
	}

	public static ArrayList<String> generateLore(int i)
	{
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("This is a magic potion!");
		switch(i)
		{
		case 0:
			strings.add("This will double your next income by 2x");
			return strings;
		case 1:
			strings.add("This will double your next income by 2.5x");
			return strings;
		case 2:
			strings.add("This will double your next income by 3x");
			return strings;
		case 3:
			strings.add("This will double your next income by 3.5x");
			return strings;
		case 4:
			strings.add("This will double your next income by 4x");
			return strings;
		case 5:
			strings.add("This will double your next income by 4.5x");
			return strings;
		case 6:
			strings.add("This will double your next income by 5x");
			return strings;
		case 7:
			strings.add("This will double your next income by 5.5x");
			return strings;
		case 8:
			strings.add("This will double your next income by 6x");
			return strings;
		default:
			strings.add("This will double your next income by 100x");
			return strings;
		}
	}

	public static double checkPotion(ItemStack item)
	{
		try
		{
			List<String> lore = item.getItemMeta().getLore();
			String s = lore.get(1);
			ArrayList<String> strings = new ArrayList<String>();
			strings.add("This will double your next income by 2x");
			strings.add("This will double your next income by 2.5x");
			strings.add("This will double your next income by 3x");
			strings.add("This will double your next income by 3.5x");
			strings.add("This will double your next income by 4x");
			strings.add("This will double your next income by 4.5x");
			strings.add("This will double your next income by 5x");
			strings.add("This will double your next income by 5.5x");
			strings.add("This will double your next income by 6x");
			for(String income : strings)
			{
				if(s.equalsIgnoreCase(income))
				{
					if(strings.indexOf(income) == 0)
					{
						return 2;
					}
					if(strings.indexOf(income) % 2 == 0)
					{
						return Double.valueOf(income.substring(36, 38));
					}
					else
					{
						return Double.valueOf(income.substring(36,40));
					}
				}
			}
			return 100;
		}
		catch(NullPointerException e)
		{
			return 1000;
		}

	}
}

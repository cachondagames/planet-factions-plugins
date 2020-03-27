package org.planetfactions.envoy.app.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.planetfactions.envoy.Main;
import org.planetfactions.envoy.app.Envoy;

public class Message 
{
	public static String playerReplace(String s, Player p)
	{
		s = s.replaceAll("%player%", p.getName());
		System.out.println(p.getName());
		return s;
	}

	public static void executeCommand(String tier, Player p, String prize)
	{
		Main plugin = Envoy.getEnvoyEvent().getPlugin();
		FileConfiguration config = plugin.getConfig();
		List<String> list = config.getStringList("Prizes." + tier + "." + String.valueOf(prize) + ".Commands");
		if(!list.isEmpty())
		{
			for(String string : list)
			{
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), playerReplace(string, p));
			}
		}
		else
		{
			return;
		}
	}

	public static void giveItem(String tier, Player p, String prize)
	{
		FileConfiguration config = Envoy.getEnvoyEvent().getPlugin().getConfig();
		PlayerInventory inv = p.getInventory();
		ArrayList<ItemStack> items = makeItem(tier, prize);
		for(ItemStack item : items)
		{
			ItemMeta meta = enchantItem(tier, prize, Integer.toString(items.indexOf(item)+1), item);
			item.setItemMeta(meta);
			inv.addItem(item);
		}
	}

	public static ArrayList<ItemStack> makeItem(String tier, String prize)
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		FileConfiguration config = Envoy.getEnvoyEvent().getPlugin().getConfig();
		int i = 1;
		while(true)
		{
			try
			{
				String item = config.getString("Prizes." + tier + "." + prize + ".Items." + i + ".Item");
				int amount = config.getInt("Prizes." + tier + "." + prize + ".Items." + i + ".Amount");
				if(!item.isEmpty())
				{
					ItemStack newitem = new ItemStack(Material.getMaterial(item.toUpperCase()),amount);
					items.add(newitem);
					i++;
				}
				else
				{
					break;
				}
			}
			catch(NullPointerException e)
			{
				return items;
			}
		}
		return items;
	}

	public static ItemMeta enchantItem(String tier, String prize, String itemnum, ItemStack item)
	{
		FileConfiguration config = Envoy.getEnvoyEvent().getPlugin().getConfig();
		ArrayList<String> names = new ArrayList<String>();
		List<String> enchantments = config.getStringList("Prizes." + tier + "." + prize + ".Items." + itemnum + ".Enchants");
		ItemMeta meta = item.getItemMeta();
		for(String string : enchantments)
		{
			String[] strings = string.split(",");
			for(String s : strings)
			{
				names.add(s);
			}
		}
		for(int i = 0; i <= names.size()-2;i = i+2)
		{
			try
			{
				meta.addEnchant(Enchantment.getByName(names.get(i).toUpperCase()), Integer.parseInt(names.get(i+1)), true);
				meta.setLore(config.getStringList("Prizes." + tier + "." + prize + ".Items." + itemnum + ".Lore"));
				meta.setDisplayName(config.getString("Prizes." + tier + "." + prize + ".Items." + itemnum + ".ItemName"));
			}
			catch(IllegalArgumentException e)
			{
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[Envoy] The Enchatment: " + names.get(i).toUpperCase() + " does not exist!"));
			}
		}
		return meta;
	}

	public static void isGood(String tier, Player p)
	{
		FileConfiguration config = Envoy.getEnvoyEvent().getPlugin().getConfig();
		int i = 1;
		while(true)
		{
			int chance = config.getInt("Prizes." + tier + "." + i + ".Chance");
			if(chance > 0)
			{
				Random r = new Random();
				if(r.nextInt(100) + 1 <= chance)
				{
					executeCommand(tier,p, Integer.toString(i));
					giveItem(tier,p,Integer.toString(i));
					List<String> list = config.getStringList("Prizes." + tier + "." + i + ".Message");
					for(String s : list)
					{
						String temp = "&a[Envoy] &5" + s;
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', playerReplace(temp, p)));
					}
					i++;
				}
			}
			else
			{
				break;
			}
		}
	}
}

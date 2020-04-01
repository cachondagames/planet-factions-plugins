package org.planetfactions.gamble.listeners;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.planetfactions.gamble.Inv;
import org.planetfactions.gamble.Main;
import org.planetfactions.gamble.data.Data;

import net.ess3.api.events.UserBalanceUpdateEvent;

public class Listeners implements Listener
{
	public Listeners(Main plugin)
	{
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void water(PlayerInteractEntityEvent e)
	{
		ArrayList<Entity> spawns = Data.getData().getSpawns();
		for(Entity d : spawns)
		{
			if(d.equals(e.getRightClicked()))
			{
				e.setCancelled(true);
				e.getPlayer().openInventory(Inv.openInv());
			}
		}
	}
	
	@EventHandler
	public void drpepper(InventoryClickEvent e)
	{
		if(e.getClickedInventory().getTitle().equalsIgnoreCase("Money Double"))
		{
			if(e.getAction().equals(InventoryAction.PICKUP_ALL))
			{
				e.setCancelled(true);
				int i = e.getSlot();
				switch(i)
				{
				case 0:
					if(Main.getEco().has((Player) e.getWhoClicked(), 1000))
					{
						giveItem(e.getSlot(),(Player) e.getWhoClicked());
						return;
					}
					else
					{
						e.getWhoClicked().sendMessage("This item costs 1000 dollars!");
						return;
					}
				case 1:
					if(Main.getEco().has((Player) e.getWhoClicked(), 2000))
					{
						giveItem(e.getSlot(),(Player) e.getWhoClicked());
						return;
					}
					else
					{
						e.getWhoClicked().sendMessage("This item costs 2000 dollars!");
						return;
					}
				case 2:
					if(Main.getEco().has((Player) e.getWhoClicked(), 3000))
					{
						giveItem(e.getSlot(),(Player) e.getWhoClicked());
						return;
					}
					else
					{
						e.getWhoClicked().sendMessage("This item costs 3000 dollars!");
						return;
					}
				case 3:
					if(Main.getEco().has((Player) e.getWhoClicked(), 4000))
					{
						giveItem(e.getSlot(),(Player) e.getWhoClicked());
						return;
					}
					else
					{
						e.getWhoClicked().sendMessage("This item costs 4000 dollars!");
						return;
					}
				case 4:
					if(Main.getEco().has((Player) e.getWhoClicked(), 5000))
					{
						giveItem(e.getSlot(),(Player) e.getWhoClicked());
						return;
					}
					else
					{
						e.getWhoClicked().sendMessage("This item costs 5000 dollars!");
						return;
					}
				case 5:
					if(Main.getEco().has((Player) e.getWhoClicked(), 6000))
					{
						giveItem(e.getSlot(),(Player) e.getWhoClicked());
						return;
					}
					else
					{
						e.getWhoClicked().sendMessage("This item costs 6000 dollars!");
						return;
					}
				case 6:
					if(Main.getEco().has((Player) e.getWhoClicked(), 7000))
					{
						giveItem(e.getSlot(),(Player) e.getWhoClicked());
						return;
					}
					else
					{
						e.getWhoClicked().sendMessage("This item costs 7000 dollars!");
						return;
					}
				case 7:
					if(Main.getEco().has((Player) e.getWhoClicked(), 8000))
					{
						giveItem(e.getSlot(),(Player) e.getWhoClicked());
						return;
					}
					else
					{
						e.getWhoClicked().sendMessage("This item costs 8000 dollars!");
						return;
					}
				case 8:
					if(Main.getEco().has((Player) e.getWhoClicked(), 9000))
					{
						giveItem(e.getSlot(),(Player) e.getWhoClicked());
						return;
					}
					else
					{
						e.getWhoClicked().sendMessage("This item costs 9000 dollars!");
						return;
					}
				}
				
			}
			else
			{
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void candadry(PlayerItemConsumeEvent e)
	{
		if(e.getItem().getType().equals(Material.POTION))
		{
			double i = Data.checkPotion(e.getItem());
			if(i < 10)
			{
				Data.getData().addPlayer(e.getPlayer(), i);
				e.getPlayer().sendMessage("Your next gain in money will now be increased!");
			}
		}
	}
	
	@EventHandler
	public void squirt(UserBalanceUpdateEvent e)
	{
		if(Data.getData().isEffect(e.getPlayer()))
		{
			if(e.getNewBalance().compareTo(e.getOldBalance()) > 0)
			{
				BigDecimal temp = e.getNewBalance().subtract(e.getOldBalance());
				e.setNewBalance(e.getOldBalance().add(temp.multiply(BigDecimal.valueOf(Data.getData().getEffect(e.getPlayer())))));
				Data.getData().removePlayer(e.getPlayer());
			}
		}
	}
	
	public void giveItem(int i, Player p)
	{
		ItemStack item = new ItemStack(Material.POTION);
		ItemMeta meta = item.getItemMeta();
		switch(i)
		{
		case 0:
			meta.setDisplayName("2x");
			meta.setLore(Data.generateLore(i));
			item.setItemMeta(meta);
			p.getInventory().addItem(item);
			return;
		case 1:
			meta.setDisplayName("2.5x");
			meta.setLore(Data.generateLore(i));
			item.setItemMeta(meta);
			p.getInventory().addItem(item);
			return;
		case 2:
			meta.setDisplayName("3x");
			meta.setLore(Data.generateLore(i));
			item.setItemMeta(meta);
			p.getInventory().addItem(item);
			return;
		case 3:
			meta.setDisplayName("3.5x");
			meta.setLore(Data.generateLore(i));
			item.setItemMeta(meta);
			p.getInventory().addItem(item);
			return;
		case 4:
			meta.setDisplayName("4x");
			meta.setLore(Data.generateLore(i));
			item.setItemMeta(meta);
			p.getInventory().addItem(item);
			return;
		case 5:
			meta.setDisplayName("4.5x");
			meta.setLore(Data.generateLore(i));
			item.setItemMeta(meta);
			p.getInventory().addItem(item);
			return;
		case 6:
			meta.setDisplayName("5x");
			meta.setLore(Data.generateLore(i));
			item.setItemMeta(meta);
			p.getInventory().addItem(item);
			return;
		case 7:
			meta.setDisplayName("5.5x");
			meta.setLore(Data.generateLore(i));
			item.setItemMeta(meta);
			p.getInventory().addItem(item);
			return;
		case 8:
			meta.setDisplayName("6x");
			meta.setLore(Data.generateLore(i));
			item.setItemMeta(meta);
			p.getInventory().addItem(item);
			return;
		}
	}
}

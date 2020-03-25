package org.planetfactions.eggspawn.app.recipes;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.planetfactions.eggspawn.app.Main;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

public class Recipes implements Listener
{
	private Main plugin;
	public Recipes(Main plugin)
	{
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		if(loadrecipes()) 
		{
			plugin.getServer().getConsoleSender().sendMessage("[TEST] THIS RECIPE LOADED");
		}
		else
		{
			plugin.getServer().getConsoleSender().sendMessage("[TEST] FUCK");
		}
	}
	
	public boolean loadrecipes()
	{
		ItemStack dragone = new ItemStack(Material.DRAGON_EGG);
		ShapedRecipe egg = new ShapedRecipe(dragone);
		egg.shape("ccc","d","d");
		egg.setIngredient('c', Material.IRON_INGOT);
		return plugin.getServer().addRecipe(egg);
		
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void food(PrepareItemCraftEvent e)
	{
		ItemStack dragone = new ItemStack(Material.DRAGON_EGG);
		if(e.getRecipe().getResult().getType().equals(Material.IRON_PICKAXE))
		{
			ItemStack[] items = e.getInventory().getContents();
			for(ItemStack item : items)
			{
				if(item.getType().equals(Material.IRON_INGOT))
				{
					if(item.getItemMeta().hasLore())
					{
						e.getInventory().setResult(dragone);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void vodka(BlockPlaceEvent e) 
	{
		if(e.getBlock().getType().equals(Material.DRAGON_EGG))
		{
			FPlayer temp = FPlayers.getInstance().getByPlayer(e.getPlayer());
			if(temp.hasFaction())
			{
				if(temp.isInOwnTerritory())
				{
					Faction fac = temp.getFaction();
					List<Player> play = fac.getOnlinePlayers();
					for(Player players: play)
					{
						PotionEffect temp2 = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 3, false, false);
						players.addPotionEffect(temp2);
					}
				}
			}
			else
			{
				e.getPlayer().sendMessage("you need to be in a faction to use this!");
				e.setCancelled(true);
			}
		}
	}
	
}

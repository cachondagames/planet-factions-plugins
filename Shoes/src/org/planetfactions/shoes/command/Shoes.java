package org.planetfactions.shoes.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.planetfactions.shoes.Main;

public class Shoes implements CommandExecutor
{
	public Shoes(Main plugin)
	{
		plugin.getCommand("shoes").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender player, Command arg1, String arg2, String[] args) 
	{
			Player p = (Player) player;
			PlayerInventory inv = p.getInventory();
			ItemStack hand = p.getItemInHand();
			inv.setChestplate(hand);
			inv.setLeggings(hand);
			return true;
	}
	
}

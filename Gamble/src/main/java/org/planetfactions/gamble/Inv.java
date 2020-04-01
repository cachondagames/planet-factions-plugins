package org.planetfactions.gamble;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Inv 
{
	public static Inventory openInv()
	{
		Inventory inv = populateInv(Bukkit.createInventory(null, 9, "Money Double"));
		return inv;
	}
	
	private static Inventory populateInv(Inventory inv)
	{
		ItemStack items = new ItemStack(Material.POTION);
		ItemMeta meta = items.getItemMeta();
		String[] string = new String[9];
		string[0] = "2x";
		string[1] = "2.5x";
		string[2] = "3x";
		string[3] = "3.5x";
		string[4] = "4x";
		string[5] = "4.5x";
		string[6] = "5x";
		string[7] = "5.5x";
		string[8] = "6x";
		for(int i = 0; i < inv.getSize(); i++)
		{
			meta.setDisplayName(string[i]);
			items.setItemMeta(meta);
			inv.setItem(i, items);
		}
		return inv;
	}
	
	
}

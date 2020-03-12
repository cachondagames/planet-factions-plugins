package org.planetfactions.envoy.app.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.planetfactions.envoy.Main;
import org.planetfactions.envoy.app.Envoy;
import org.planetfactions.envoy.app.events.EnvoyTier1OpenEvent;

public class Tier1Listener implements Listener
{
	
	Envoy envoy = Envoy.getEnvoyEvent();
	public Tier1Listener(Main plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void OpenEvent(EnvoyTier1OpenEvent e)
	{
		try
		{
			ArrayList<Block> block = envoy.getChestLocations();
			int i = envoy.getLocationOn();
			e.getLocation().getBlock().setType(Material.AIR);
			envoy.chooseTier(block.get(i).getLocation());
			String s = "&a[Envoy] &dThe next chest is located at: " + "&4" + block.get(i).getX() + " " + "&4" +  block.get(i).getY() + " " + "&4" +  block.get(i).getZ();
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&' , s));
            Firework fw = (Firework) block.get(i).getWorld().spawnEntity(block.get(i).getLocation(), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            Random r = new Random();   
            Type type = Type.BALL_LARGE;       
            FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.AQUA).withFade(Color.PURPLE).with(type).trail(r.nextBoolean()).build();
            fwm.addEffect(effect);
            fwm.setPower(3);
            fw.setFireworkMeta(fwm);
            envoy.setEnvoyClick(e.getPlayer());
		}
		catch(IndexOutOfBoundsException i)
		{
			envoy.setEnvoyClick(e.getPlayer());
			envoy.endEnvoy();
		}
	}
}

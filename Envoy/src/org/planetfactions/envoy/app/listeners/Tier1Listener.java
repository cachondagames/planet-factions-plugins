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
			String s = "&a[Envoy] &cThe next chest is located at: " + block.get(i).getX() + " " + block.get(i).getY() + " " + block.get(i).getZ();
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&' , s));
            Firework fw = (Firework) block.get(i).getWorld().spawnEntity(block.get(i).getLocation(), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            Random r = new Random();   
            int rt = r.nextInt(5) + 1;
            Type type = Type.BALL;       
            if (rt == 1) type = Type.BALL;
            if (rt == 2) type = Type.BALL_LARGE;
            if (rt == 3) type = Type.BURST;
            if (rt == 4) type = Type.CREEPER;
            if (rt == 5) type = Type.STAR;
            FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.AQUA).withFade(Color.PURPLE).with(type).trail(r.nextBoolean()).build();
            fwm.addEffect(effect);
            int rp = r.nextInt(2) + 1;
            fwm.setPower(rp);
            fw.setFireworkMeta(fwm);           
		}
		catch(IndexOutOfBoundsException i)
		{
			envoy.endEnvoy();
		}
	}
}

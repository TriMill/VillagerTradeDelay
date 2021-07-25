package io.github.trimill.villagertradedelay;

import net.minecraft.world.entity.npc.EntityVillager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftVillager;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class VillagerTradeDelay extends JavaPlugin implements CommandExecutor {
	// delay in ticks for the villager to be a nitwit
	public long delay;
	public ArrayList<Villager> villagersWaiting;
	public ArrayList<BukkitRunnable> runnables;
	public boolean active;
	public String customName;
	public boolean customNameVisible;

	@Override
	public void onEnable() {
		this.active = true;
		this.initFromConfig();
		villagersWaiting = new ArrayList<>();
		runnables = new ArrayList<>();
		getServer().getPluginManager().registerEvents(new CareerChangeListener(), this);
		getCommand("reload").setExecutor(this);
		getCommand("resetqueue").setExecutor(this);
		getLogger().info("Loaded VillagerTradeDelay");
	}

	@Override
	public void onDisable() {
		getLogger().info("Disabling VillagerTradeDelay, resetting queued villagers");
		resetAllQueued();
		active = false;
	}

	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(sender.isOp()) {
			String name = command.getName();
			if(name.equals("reload")) {
				this.reloadConfig();
				this.initFromConfig();
				sender.sendMessage("Reloaded configuration");
			} else if(name.equals("resetqueue")) {
				resetAllQueued();
				sender.sendMessage("Finished all queued villager conversions");
			}
		} else {
			sender.sendMessage("This command must be run by an operator!");
		}
		return true;
	}

	/*
	 * Load/reload config file
	 */
	public void initFromConfig() {
		this.saveDefaultConfig();
		FileConfiguration cfg = this.getConfig();
		this.delay = cfg.getLong("delay");
		this.customNameVisible = cfg.getBoolean("custom_name.visible");
		if(this.customNameVisible) {
			this.customName = cfg.getString("custom_name.name");
		} else {
			this.customName = "";
		}
	}

	/*
	 * Reset all queued villagers
	 */
	public void resetAllQueued() {
		ArrayList<BukkitRunnable> runnables_copy = new ArrayList<>(runnables);
		for(BukkitRunnable r: runnables_copy) {
			r.run();
			r.cancel();
		}
	}

	//== Static methods ==//

	/*
	 * Change a villager from a nitwit to unemployed
	 */
	public static void resetVillager(Villager v) {
		v.setProfession(Villager.Profession.NONE);
		v.setMemory(MemoryKey.JOB_SITE, null);
		v.setMemory(MemoryKey.LAST_WORKED_AT_POI, null);

		// witchcraft
		EntityVillager e = ((CraftVillager)v).getHandle();
		e.c(e.getMinecraftServer().getWorldServer(e.getWorld().getDimensionKey()));
	}

	/*
	 * Return the current plugin instance
	 */
	public static @NotNull VillagerTradeDelay pl() {
		return VillagerTradeDelay.getPlugin(VillagerTradeDelay.class);
	}
}

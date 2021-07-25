package io.github.trimill.villagertradedelay;

import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

public class CareerChangeRunnable extends BukkitRunnable {
	private Villager v;
	private VillagerTradeDelay pl;
	private String formerName;
	private boolean formerNameVisible;

	public CareerChangeRunnable(Villager v, VillagerTradeDelay pl) {
		this.v = v;
		this.pl = pl;

		this.formerName = v.getCustomName();
		this.formerNameVisible = v.isCustomNameVisible();
		if(pl.customNameVisible) {
			v.setCustomName(pl.customName);
			v.setCustomNameVisible(true);
		}
	}

	@Override
	public void run() {
		v.setCustomName(this.formerName);
		v.setCustomNameVisible(this.formerNameVisible);
		VillagerTradeDelay.resetVillager(v);
		pl.villagersWaiting.remove(v);
		pl.runnables.remove(this);
	}
}

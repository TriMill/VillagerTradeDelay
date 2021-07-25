package io.github.trimill.villagertradedelay;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerCareerChangeEvent;

public class CareerChangeListener implements Listener {
	@EventHandler
	public void onCareerChange(VillagerCareerChangeEvent e) {
		VillagerTradeDelay pl = VillagerTradeDelay.pl();
		if(pl.active) {
			pl.getLogger().info("*");
			if(e.getProfession() == Villager.Profession.NONE) {
				Villager v = e.getEntity();
				if(!pl.villagersWaiting.contains(v)) {
					pl.getLogger().info("Beginning conversion");
					pl.villagersWaiting.add(v);
					CareerChangeRunnable r = new CareerChangeRunnable(v, pl);
					r.runTaskLater(pl, pl.delay);
					pl.runnables.add(r);
					e.setProfession(Villager.Profession.NITWIT);
				} else {
					e.setCancelled(true);
				}
			}
		}
	}
}

package io.github.thedavis.alarmclock.actions;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.thedavis.alarmclock.timers.Timer;

public class KickAction extends MessageBasedAction {
	
	public KickAction(JavaPlugin plugin, Timer timer, String message){
		super(plugin, timer, message);
	}

	@Override
	public void perform(Collection<UUID> playerIds) {
		if(timer.isExpired()){
			for(UUID id : playerIds){
				plugin.getServer().getPlayer(id).kickPlayer(message);
			}
			this.cancel();
		}
	}

}

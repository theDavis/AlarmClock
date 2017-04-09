package io.github.thedavis.alarmclock.actions;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.thedavis.alarmclock.timers.Timer;

public class Warning extends MessageBasedAction {
	
	public Warning(JavaPlugin plugin, Timer timer, String message) {
		super(plugin, timer, message);
	}

	@Override
	public void perform(Collection<UUID> playerIds) {
		if(timer.isExpired()){
			playerIds.forEach(new Consumer<UUID>(){
				@Override
				public void accept(UUID id) {
					plugin.getServer().getPlayer(id).sendMessage(message);
				}
			});
			this.cancel();
		}
	}

}

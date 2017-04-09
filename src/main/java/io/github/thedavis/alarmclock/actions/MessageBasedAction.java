package io.github.thedavis.alarmclock.actions;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.thedavis.alarmclock.timers.Timer;

public abstract class MessageBasedAction extends AbstractAction {
	
	protected final JavaPlugin plugin;
	protected final Timer timer;
	protected final String message;

	public MessageBasedAction(JavaPlugin plugin, Timer timer, String message){
		this.plugin = plugin;
		this.timer = timer;
		this.message = message;
	}
}

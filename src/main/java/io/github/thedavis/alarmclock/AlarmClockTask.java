package io.github.thedavis.alarmclock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AlarmClockTask extends BukkitRunnable {
	
	private final JavaPlugin plugin;
	private final long duration;
	private long startTime;
	private final boolean kickPlayers;
	private final String message;
	private List<Player> players;

	private boolean fifteenMinuteWarningEnabled = true;
	private boolean fiveMinuteWarningEnabled = true;
	
	public AlarmClockTask(JavaPlugin plugin, long duration, boolean kickPlayers, Collection<? extends Player> players){
		this.plugin = plugin;
		this.duration = duration;
		this.kickPlayers = kickPlayers;
		this.message = "Time is up, go outside!";
		this.startTime = System.nanoTime();
		this.players = new ArrayList<>(players);
	}

	@Override
	public void run() {
		final long minutesRemaining = getMinutesRemaining();
		if(minutesRemaining <= 0){
			for(Player player : players){
				Player onlinePlayer = plugin.getServer().getPlayer(player.getUniqueId());
				if(onlinePlayer.isOnline()){
					if(kickPlayers){
						onlinePlayer.kickPlayer(message);
					} else {
						onlinePlayer.sendMessage(message);
					}
				}
			}
			this.cancel();
		} else {
			sendWarnings(minutesRemaining);
		}
	}
	
	private long getMinutesRemaining(){
		return duration - getMinutesElapsed();
	}
	
	private long getMinutesElapsed(){
		long diff = System.nanoTime() - startTime;
		return convertNanoSecondsToMinutes(diff);
	}
	
	private long convertNanoSecondsToMinutes(long nanoTime){
		return TimeUnit.MINUTES.convert(nanoTime, TimeUnit.NANOSECONDS);
	}
	
	private void sendWarnings(long minutesRemaining){
		if(fifteenMinuteWarningEnabled && lessThanFifteenMinutesRemain(minutesRemaining)){
			sendMinutesRemainingWarning(minutesRemaining);
			fifteenMinuteWarningEnabled = false;
		}
		
		if(fiveMinuteWarningEnabled && lessThanFiveMinutesRemain(minutesRemaining)){
			sendMinutesRemainingWarning(minutesRemaining);
			fiveMinuteWarningEnabled = false;
		}
	}
	
	private boolean lessThanFifteenMinutesRemain(long minutesElapsed){
		return timeRemainingIsLessThan(minutesElapsed, 15);
	}
	
	private boolean lessThanFiveMinutesRemain(long minutesElapsed){
		return timeRemainingIsLessThan(minutesElapsed, 5);
	}
	
	private boolean timeRemainingIsLessThan(long remaining, long someDuration){
		return remaining < someDuration;
	}

	private void sendMinutesRemainingWarning(long minutesRemaining){
		for(Player player : players){
			Player onlinePlayer = plugin.getServer().getPlayer(player.getUniqueId());
			if(onlinePlayer.isOnline()){
				onlinePlayer.sendMessage("Only " + minutesRemaining + " minutes left, finish up!");
			}
		}
	}
}

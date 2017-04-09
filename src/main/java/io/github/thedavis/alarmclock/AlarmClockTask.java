package io.github.thedavis.alarmclock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.thedavis.alarmclock.actions.AlarmClockAction;
import io.github.thedavis.alarmclock.actions.KickAction;
import io.github.thedavis.alarmclock.actions.Warning;
import io.github.thedavis.alarmclock.timers.TimerFactory;

public class AlarmClockTask extends BukkitRunnable {
	
	private final JavaPlugin plugin;
	private List<UUID> playerIds;
	private Set<AlarmClockAction> actions;
	
	public AlarmClockTask(JavaPlugin plugin, long durationInMinutes, boolean kickPlayers, Collection<? extends Player> players){
		this.plugin = plugin;
		this.playerIds = getUUIDs(players);
		configureActions(durationInMinutes, kickPlayers, "Time to go to bed!");
	}
	
	private List<UUID> getUUIDs(Collection<? extends Player> players){
		List<UUID> uuids = new ArrayList<>();
		for(Player player : players){
			uuids.add(player.getUniqueId());
		}
		return uuids;
	}
	
	private void configureActions(long durationInMinutes, boolean kickPlayers, String message){
		actions = new HashSet<>();
		if(kickPlayers){
			actions.add(new KickAction(plugin, TimerFactory.getSystemTimer(durationInMinutes), message));
		} else {
			actions.add(new Warning(plugin, TimerFactory.getSystemTimer(durationInMinutes), message));
		}
		
		if(durationInMinutes > 5){
			actions.add(new Warning(plugin, TimerFactory.getSystemTimer(durationInMinutes - 5), "5 minute warning!"));
		}
		
		if(durationInMinutes > 10){
			actions.add(new Warning(plugin, TimerFactory.getSystemTimer(durationInMinutes - 10), "10 minute warning!"));
		}
	}

	@Override
	public void run() {
		updateActivePlayers();
		performActions();
		updateActiveActions();
		cancelIfAllActionsComplete();
	}
	
	private void updateActivePlayers(){
		playerIds.removeIf(new Predicate<UUID>(){
			@Override
			public boolean test(UUID id) {
				return !plugin.getServer().getPlayer(id).isOnline();
			}
		});
	}
	
	private void performActions(){
		actions.forEach(new Consumer<AlarmClockAction>(){
			@Override
			public void accept(AlarmClockAction action) {
				action.perform(playerIds);
			}
		});
	}
	
	private void updateActiveActions(){
		actions.removeIf(new Predicate<AlarmClockAction>(){
			@Override
			public boolean test(AlarmClockAction action) {
				return !action.isActive();
			}
		});
	}
	
	private void cancelIfAllActionsComplete(){
		if(actions.isEmpty()){
			this.cancel();
		}
	}
}

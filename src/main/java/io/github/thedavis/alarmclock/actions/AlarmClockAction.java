package io.github.thedavis.alarmclock.actions;

import java.util.Collection;
import java.util.UUID;

public interface AlarmClockAction {
	
	public boolean isActive();

	public void perform(Collection<UUID> playerIds);
	
	public void cancel();
}

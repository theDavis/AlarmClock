package io.github.thedavis.alarmclock.actions;

public abstract class AbstractAction implements AlarmClockAction {
	
	protected boolean active = true;

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void cancel() {
		this.active = false;
	}

}

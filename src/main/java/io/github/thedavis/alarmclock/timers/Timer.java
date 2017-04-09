package io.github.thedavis.alarmclock.timers;

public interface Timer {
	 
	/**
	 * @return true if this timer has expired
	 */
	public boolean isExpired();
	
	/**
	 * @return the time remaining in minutes
	 */
	public long getMinutesRemaining();
}

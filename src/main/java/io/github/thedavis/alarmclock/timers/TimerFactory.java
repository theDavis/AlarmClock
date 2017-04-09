package io.github.thedavis.alarmclock.timers;

import java.time.Clock;
import java.util.concurrent.TimeUnit;

public class TimerFactory {

	/**
	 * Returns a Timer that uses the system time
	 * @param durationInMinutes the duration in minutes
	 * @return a Timer that uses system time
	 */
	public static Timer getSystemTimer(long durationInMinutes){
		return new SystemTimer(Clock.systemDefaultZone(), minutesToMilliseconds(durationInMinutes));
	}
	
	private static long minutesToMilliseconds(long durationInMinutes){
		return TimeUnit.MILLISECONDS.convert(durationInMinutes, TimeUnit.MINUTES);
	}
}

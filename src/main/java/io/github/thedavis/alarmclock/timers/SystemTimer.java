package io.github.thedavis.alarmclock.timers;

import java.time.Clock;
import java.util.concurrent.TimeUnit;

public class SystemTimer implements Timer {
	
	private final long duration;
	private final Clock clock;
	private final long startTime;
	
	private boolean expired = false;
	
	public SystemTimer(Clock clock, long durationInMs){
		if(durationInMs < 0){
			throw new IllegalArgumentException("Timer cannot have a negative duration");
		}
		this.duration = durationInMs;
		this.clock = clock;
		this.startTime = clock.millis();
	}

	@Override
	public boolean isExpired() {
		if(!expired){
			expired = (clock.millis() - startTime) >= duration;
		}
		
		return expired;
	}
	
	@Override
	public long getMinutesRemaining(){
		return TimeUnit.MINUTES.convert(clock.millis() - startTime, TimeUnit.MILLISECONDS);
	}
}

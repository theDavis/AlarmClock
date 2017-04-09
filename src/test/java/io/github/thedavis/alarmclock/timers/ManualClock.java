package io.github.thedavis.alarmclock.timers;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.apache.commons.lang.NotImplementedException;

public class ManualClock extends Clock {

	private long millitime;
	
	public ManualClock(){
		this.millitime = 0;
	}
	
	@Override
	public long millis(){
		return millitime;
	}
	
	public void tick(long milliseconds){
		this.millitime += milliseconds;
	}

	@Override
	public ZoneId getZone() {
		throw new NotImplementedException("Method not implemented");
	}

	@Override
	public Instant instant() {
		throw new NotImplementedException("Method not implemented");
	}

	@Override
	public Clock withZone(ZoneId zone) {
		throw new NotImplementedException("Method not implemented");
	}

}

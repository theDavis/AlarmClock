package io.github.thedavis.alarmclock.timers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Clock;

import org.junit.Test;

public class TestSystemTimer {

	@Test
	public void testSystemTimerExpires_ZeroDuration() throws InterruptedException{
		ManualClock clock = new ManualClock();
		Timer timer = new SystemTimer(clock, 0);
		assertTrue("Timer should have expired", timer.isExpired());
	}
	
	@Test
	public void testSystemTimerExpires_NonZeroDuration() throws InterruptedException{
		ManualClock clock = new ManualClock();
		Timer timer = new SystemTimer(clock, 1);
		assertFalse(timer.isExpired());
		clock.tick(1);
		assertTrue(timer.isExpired());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSystemTimer_negativeDuration(){
		new SystemTimer(Clock.systemDefaultZone(), -1);
	}
	
}

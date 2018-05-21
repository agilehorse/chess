package cz.cvut.fel.pjv.GUI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ClockTest {

    private final Clock clock;

    public ClockTest() {

        clock = new Clock();
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }
//  process test
    @Test
    public void clockAllFunctionalities() {
        System.out.println("clockAllFunctionalities");
        final int whiteTime = clock.getWhiteTime();
        final int blackTime = clock.getBlackTime();
        Clock.start();
        assertTrue(Clock.getTurn().isWhite());
        sleep();
        Clock.updateClock();
        assertTrue(Clock.getTurn().isBlack());
        sleep();
        Clock.terminate();
        final int newWhiteTime = clock.getWhiteTime();
        final int newBlackTime = clock.getWhiteTime();
        assertNotEquals(newWhiteTime, whiteTime);
        assertNotEquals(newBlackTime, blackTime);
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

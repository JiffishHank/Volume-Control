package com.example.jonathan.volumecontrol;

import org.junit.Test;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class SocketManagerTest {

    private SocketManager manager;

    /*
        Verifies that the 2-arg constructor sets everything properly
     */
    @Test
    public void testConstructorTwoArgs() throws NoSuchFieldException, IllegalAccessException {
        SocketManager manager = new SocketManager("127.0.0.0", "mute");

        Field ipAddress = manager.getClass().getDeclaredField("ipAddress");
        Field action = manager.getClass().getDeclaredField("action");
        Field value = manager.getClass().getDeclaredField("value");

        ipAddress.setAccessible(true);
        action.setAccessible(true);
        value.setAccessible(true);

        assertEquals("127.0.0.0", ipAddress.get(manager));
        assertEquals("mute", action.get(manager));
        assertEquals(-1, value.get(manager));
    }

    /*
        Verifies that the 3-arg constructor sets everything properly
     */
    @Test
    public void testConstructorThreeArgs() throws NoSuchFieldException, IllegalAccessException {
        SocketManager manager = new SocketManager("127.0.0.0", "raise", 10);

        Field ipAddress = manager.getClass().getDeclaredField("ipAddress");
        Field action = manager.getClass().getDeclaredField("action");
        Field value = manager.getClass().getDeclaredField("value");

        ipAddress.setAccessible(true);
        action.setAccessible(true);
        value.setAccessible(true);

        assertEquals("127.0.0.0", ipAddress.get(manager));
        assertEquals("raise", action.get(manager));
        assertEquals(10, value.get(manager));
    }

    /*
        When raiseVolume() is called,
        Then it should send a packet to the server and return the result
     */
    @Test
    public void testRaiseVolumeSuccess() throws Exception {
        manager = new SocketManager("127.0.0.0", "raise", 10);
        boolean success = manager.raiseVolume();

        assertTrue("It should indicate whether the packet was sent successfully", success);
    }

    /*
        When lowerVolume() is called,
        Then it should send a packet to the server and return the result
     */
    @Test
    public void testLowerVolume() throws Exception {
        manager = new SocketManager("127.0.0.0", "lower", 10);
        boolean success = manager.lowerVolume();

        assertTrue("It should indicate whether the packet was sent successfully", success);
    }

    /*
        When mute() is called,
        Then it should send a packet to the server and return the result
     */
    @Test
    public void testMute() throws Exception {
        manager = new SocketManager("127.0.0.0", "mute");
        boolean success = manager.mute();

        assertTrue("It should indicate whether the packet was sent successfully", success);
    }

    /*
        When unmute() is called,
        Then it should send a packet to the server and return the result
    */
    @Test
    public void testUnmute() throws Exception {
        manager = new SocketManager("127.0.0.0", "unmute");
        boolean success = manager.unmute();

        assertTrue("It should indicate whether the packet was sent successfully", success);
    }

    /*
        Purposefully fails to generate certain command line output
     */
    @Test
    public void testFail(){
        fail("This test fails in order to generate a different command line output.");
    }
}
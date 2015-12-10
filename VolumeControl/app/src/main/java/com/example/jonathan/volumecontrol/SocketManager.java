package com.example.jonathan.volumecontrol;

import android.os.AsyncTask;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Jonathan on 10/31/2015.
 */
public class SocketManager extends AsyncTask {

    private byte[] buffer = new byte[64];
    private String ipAddress = null;
    private String action = null;
    private int value = -1;

    public SocketManager(String ipAddress, String action) {
        this.action = action;
        this.buffer = this.action.getBytes();
        this.ipAddress = ipAddress;
    }

    public SocketManager(String ipAddress, String action, int value) {
        this.action = action;
        this.value = value;
        this.buffer = (this.action + " " + this.value).getBytes();
        this.ipAddress = ipAddress;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if (this.action.equals("mute")) {
            this.mute();
        } else if (this.action.equals("unmute")) {
            this.unmute();
        } else if (this.action.equals("raise") && this.value >= 0) {
            this.raiseVolume();
        } else if (this.action.equals("lower") && this.value >= 0) {
            this.lowerVolume();
        }

        return null;
    }

    /*
        Sends a packet signaling the server to raise the volume by the specified amount
     */
    protected boolean raiseVolume() {
        boolean success = false;

        try {
            InetAddress host = InetAddress.getByName(this.ipAddress);
            DatagramSocket socket = new DatagramSocket(null);

            byte[] buffer = ("raise " + this.value).getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, host, 8080);
            socket.send(packet);
            socket.close();

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /*
        Sends a packet signaling the server to lower the volume by the specified amount
     */
    protected boolean lowerVolume() {
        boolean success = false;

        try {
            InetAddress host = InetAddress.getByName(this.ipAddress);
            DatagramSocket socket = new DatagramSocket(null);

            buffer = ("lower " + this.value).getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, host, 8080);
            socket.send(packet);
            socket.close();

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /*
        Sends a packet signaling the server to mute the volume
     */
    protected boolean mute() {
        boolean success = false;

        try {
            InetAddress host = InetAddress.getByName(this.ipAddress);
            DatagramSocket socket = new DatagramSocket(null);

            buffer = "mute".getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, host, 8080);
            socket.send(packet);
            socket.close();

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /*
        Sends a packet signaling the server to unmute the volume
     */
    protected boolean unmute() {
        boolean success = false;

        try {
            InetAddress host = InetAddress.getByName(this.ipAddress);
            DatagramSocket socket = new DatagramSocket(null);

            buffer = "unmute".getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, host, 8080);
            socket.send(packet);
            socket.close();

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}

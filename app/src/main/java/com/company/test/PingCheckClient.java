package com.company.test;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PingCheckClient {
    volatile boolean isDone = true;

    public int doPing(InetAddress address, int port, int timeout) {
        long timeOfPing = System.currentTimeMillis();
        byte[] requestValue = ("Ping at " + timeOfPing).getBytes(StandardCharsets.UTF_8);
        DatagramPacket request = new DatagramPacket(requestValue, requestValue.length);
        request.setAddress(address);
        request.setPort(port);
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            long start = System.currentTimeMillis();
            clientSocket.send(request);
            isDone = false;
            clientSocket.setSoTimeout(timeout);
            DatagramPacket inputDatagramPacket = new DatagramPacket(new byte[1024], 1024);
            clientSocket.receive(inputDatagramPacket);
            isDone = true;
            long end = System.currentTimeMillis();
            String result = new String(Arrays.copyOf(inputDatagramPacket.getData(),inputDatagramPacket.getLength()), StandardCharsets.UTF_8);
            if (result.equals("Ping at " + timeOfPing))
                return (int) (end - start);
            else
                System.out.println(result);
                return -1;
        } catch (IOException e) {
            isDone = true;
            return -1;
        }
    }
}

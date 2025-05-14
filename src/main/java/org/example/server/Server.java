package org.example.server;

import org.example.server.Computation.Sensor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {


        GUIHandler guiHandler = new GUIHandler();
        new MainGUI(guiHandler);

        Sensor s = new Sensor(guiHandler);









//        startUDPServer();
//        try {
//            ServerSocket tcpServer = new ServerSocket(12345); // known TCP port
//            System.out.println("TCP Server waiting for client connection...");
//
//
//            Socket clientSocket = tcpServer.accept(); // blocks
//            System.out.println("Client connected: " + clientSocket.getInetAddress());
//            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
//            while (true) {
//                System.out.println("Received: " + dis.readUTF());
//            }
//        } catch (Exception e) {}



    }

    private static void startUDPServer() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    DatagramSocket udpSocket = new DatagramSocket(8998); // known UDP port
                    byte[] buffer = new byte[1024];

                    System.out.println("Server waiting for discovery request...");
                    while (true) {
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        udpSocket.receive(packet); // blocking

                        String message = new String(packet.getData(), 0, packet.getLength());
                        if (message.equals("DISCOVER_SERVER")) {
                            String response = "SERVER_HERE:12345"; // 12345 is TCP port
                            byte[] respData = response.getBytes();
                            DatagramPacket respPacket = new DatagramPacket(
                                    respData, respData.length, packet.getAddress(), packet.getPort()
                            );
                            udpSocket.send(respPacket);
                        }
                    }
                } catch (Exception e) {}
            }
        }).start();

    }
}
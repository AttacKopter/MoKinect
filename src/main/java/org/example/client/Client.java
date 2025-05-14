package org.example.client;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import org.example.server.Computation.Sensor;
import org.example.server.Computation.SensorHandler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;

public class Client {

    public static void main(String[] args) {


        DataOutputStream os = null;
        BufferedReader is = null;

        try {
            Socket s = connectToServer();
            os = new DataOutputStream(s.getOutputStream());
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));

            SensorHandler sensorHandler = new SensorHandler();
            sensorHandler.start(J4KSDK.SKELETON | J4KSDK.PLAYER_INDEX | J4KSDK.DEPTH | J4KSDK.UV | J4KSDK.XYZ);

            int tpm = 30;
            while (true) {
                Thread.sleep(60000/tpm);
                os.writeUTF(sensorHandler.getSkeletonString(sensorHandler.skeleton));
            }

        } catch (Exception e) {}
    }

    private static Socket connectToServer() {

        Socket tcpSocket = null;

        while (tcpSocket == null) {
            try {
                DatagramSocket socket = new DatagramSocket();
                socket.setBroadcast(true);
                socket.setSoTimeout(5000);

                String discoveryMsg = "DISCOVER_SERVER";
                byte[] sendData = discoveryMsg.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(
                        sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8998
                );
                socket.send(sendPacket);


                byte[] buffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);

                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                if (response.startsWith("SERVER_HERE:")) {
                    String portStr = response.split(":")[1];
                    int tcpPort = Integer.parseInt(portStr);
                    InetAddress serverAddress = receivePacket.getAddress();

                    tcpSocket = new Socket(serverAddress, tcpPort);
                    System.out.println("Connected to server: " + serverAddress);
                    return tcpSocket;
                }

            } catch (Exception e) {
                System.err.println(e);

            }
        }
        return tcpSocket;
    }
}


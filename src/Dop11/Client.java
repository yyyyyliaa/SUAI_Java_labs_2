package Dop11;

import java.io.*;
import java.net.*;

public class Client {
    private static boolean connected = false;
    private static String companionName = "Server: ";

    public static void main(String args[]) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: java Client <server_address> <server_port>");
            return;
        }

        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);
        DatagramSocket clientSocket = new DatagramSocket();

        Thread sendThread = new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String username = null;
                InetAddress serverAddressInet = InetAddress.getByName(serverAddress);

                while (true) {

                    if (!connected) {
                        String startMessage = "<START>";
                        byte[] sendData = startMessage.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddressInet, serverPort);
                        clientSocket.send(sendPacket);
                        connected = true;
                    }

                    String message = reader.readLine();

                    if (message.equals("@quit")) {
                        clientSocket.close();
                        System.exit(0);
                    } else if (message.startsWith("@name ")) {
                        username = message.substring(6);
                        String tempMessage = "<NAME> " + username;
                        byte[] sendData = tempMessage.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddressInet, serverPort);
                        clientSocket.send(sendPacket);
                    } else {
                        byte[] sendData = message.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddressInet, serverPort);
                        clientSocket.send(sendPacket);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sendThread.start();

        Thread receiveThread = new Thread(() -> {
            byte[] receiveData = new byte[1024];
            while (true) {
                try {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);

                    String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                    if (receivedMessage.startsWith("<NAME> ")){
                        companionName = receivedMessage.substring(7);
                    }
                    else {
                       System.out.println(companionName + ": " + receivedMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        receiveThread.start();
    }
}

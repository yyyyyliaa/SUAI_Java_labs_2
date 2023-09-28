package Lab11;

import java.io.*;
import java.net.*;


public class Server {
    private static InetAddress  clientAddress;
    private static int clientPort;
    private static String chatMateName = "Client: ";



    public static void main(String args[]) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java Server <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        DatagramSocket serverSocket = new DatagramSocket(port);
        byte[] receiveData = new byte[1024];

        System.out.println("The server is ready to work on the port " + port);

        Thread receiveThread = new Thread(() -> {
            while (true) {
                try {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);

                    clientPort = receivePacket.getPort();
                    clientAddress = receivePacket.getAddress();
                    String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                    if (receivedMessage.startsWith("<NAME>")){
                        chatMateName = receivedMessage.substring(7);
                    }
                    else if(receivedMessage.equals("<START>")){
                        String tempMessage = "Hello! Here are the available commands:\n" +
                                "@name - change name\n" +
                                "@quit - end the chat";
                        byte[] sendData = tempMessage.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                        serverSocket.send(sendPacket);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        receiveThread.start();

        Thread sendThread = new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String username = null;

                while (true) {
                    String message = reader.readLine();

                    if (message.equals("@quit")) {
                        System.exit(0);
                    } else if(message.startsWith("@name ")){
                        username = message.substring(6);
                        String tempMessage = "<NAME> "+ username;
                        byte[] sendData = tempMessage.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                        serverSocket.send(sendPacket);
                    } else {
                        byte[] sendData = message.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                        serverSocket.send(sendPacket);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        sendThread.start();
    }
}

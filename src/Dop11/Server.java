package Dop11;

import java.io.*;
import java.net.*;


public class Server {
    private static InetAddress  clientAddress;
    private static int clientPort;

    private static int hiddenNumber;
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
                    }
                    else if(receivedMessage.equals("<START>")){
                        String tempMessage = "Hello! I came up with a number between 0 and 30, try to guess it!";
                        hiddenNumber = (int) (Math.random() * 30);
                        byte[] sendData = tempMessage.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                        serverSocket.send(sendPacket);
                    }
                    else {
                        int answer = Integer.parseInt(receivedMessage);
                        String tempMessage = "";
                        if(answer>hiddenNumber) tempMessage = "Wrong answer! The hidden number is less!";
                        else if(answer<hiddenNumber) tempMessage = "Wrong answer! The hidden number is greater!";
                        else tempMessage = "This is the correct answer!";

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
    }
}

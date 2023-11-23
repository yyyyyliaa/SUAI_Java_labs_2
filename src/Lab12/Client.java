package Lab12;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    public static ArrayList<ClientThread> blaackList = new ArrayList<>();
    private Socket socket;
    private String username;

    private BufferedReader reader;
    private BufferedWriter writer;


    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
        } catch (IOException e) {
            closeAll(socket, reader, writer);
        }
    }

    public void sendMessage() {
        try {
            writer.write(username);
            writer.newLine();
            writer.flush();

            Scanner sc = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = sc.nextLine();
                writer.write(username + ": " + messageToSend);
                writer.newLine();
                writer.flush();
            }
            sc.close();
        } catch (IOException e) {
            closeAll(socket, reader, writer);
        }
    }

    public void receiveMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromGroupChat;

                while (socket.isConnected()) {
                    try {
                        messageFromGroupChat = reader.readLine();
                        System.out.println(messageFromGroupChat);
                    } catch (IOException e) {
                        closeAll(socket, reader, writer);
                    }
                }
            }
        }).start();
    }


    public void closeAll(Socket socket, BufferedReader reader, BufferedWriter writer) {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        Socket socket = new Socket("localhost",8000);
        Client client = new Client(socket, username);
        client.receiveMessage();
        client.sendMessage();

        sc.close();
    }
}

package Lab12;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientThread implements Runnable{

    public static ArrayList<ClientThread> users = new ArrayList<>();
    public static HashMap<String, ArrayList<ClientThread>> blacklist = new HashMap<>();
    private Socket socket;
    private String username;

    private BufferedReader reader;
    private BufferedWriter writer;


    ClientThread(Socket socket) {
        try {
            this.socket = socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = reader.readLine();
            users.add(this);
            messageForAll("*" + username + " joined the chat*");
        } catch (IOException e) {
            closeAll(socket, reader, writer);
        }
    }

    public String getUsername(){
        return username;
    }

    @Override
    public void run() {
        String messageFromClient = "";

        while (socket.isConnected()) {
            try {
                messageFromClient = reader.readLine();
                String cleanMessage = messageFromClient.substring(messageFromClient.indexOf(' ') + 1);
                String senderName = messageFromClient.substring(0, messageFromClient.indexOf(' ')-1);
                if(cleanMessage.startsWith("@senduser ")){
                    String messageToSend = cleanMessage.substring(10);
                    int endIndexOfReceiverName = messageToSend.indexOf(' ');
                    String receiveName = messageToSend.substring(0,endIndexOfReceiverName);
                    messageToSend = senderName + ": " + messageToSend.substring(endIndexOfReceiverName + 1);
                    messageForOne(messageToSend, receiveName, senderName);
                }
                else if (cleanMessage.startsWith("@blacklist ")){
                    String messageToSend = cleanMessage.substring(10);
                    int endIndexOfReceiverName = messageToSend.indexOf(' ');
                    String receiveName = messageToSend.substring(0,endIndexOfReceiverName);

                }
                else messageForAll(messageFromClient);
            } catch (IOException e) {
                closeAll(socket, reader, writer);
                break;
            }
        }
    }

    public void messageForAll(String messageToSend) {
        for (ClientThread user : users) {
            try {
                if (!user.username.equals(username)) {
                    user.writer.write(messageToSend);
                    user.writer.newLine();
                    user.writer.flush();
                }
            } catch (IOException e) {
                closeAll(socket, reader, writer);
            }
        }
    }

    public void messageForOne(String messageToSend, String receiverName, String senderName){
        for (ClientThread user : users) {
            try {
                if (!user.username.equals(senderName) && user.username.equals(receiverName)) {
                    user.writer.write(messageToSend);
                    user.writer.newLine();
                    user.writer.flush();
                }
            } catch (IOException e) {
                closeAll(socket, reader, writer);
            }
        }
    }

    public void removeClientThread() {
        users.remove(this);
        messageForAll("*" + username + " has left the chat*");
    }

    public void closeAll(Socket socket, BufferedReader reader, BufferedWriter writer) {
        removeClientThread();
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
}

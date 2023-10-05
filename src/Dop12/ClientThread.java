package Dop12;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            blacklist.put(username, new ArrayList<>());
            messageForAll("*" + username + " joined the chat*");
        } catch (IOException e) {
            closeAll(socket, reader, writer);
        }
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
                    String messageWithoutCommand = cleanMessage.substring(10);
                    int endIndexOfReceiverName = messageWithoutCommand.indexOf(' ');
                    String receiveName = messageWithoutCommand.substring(0,endIndexOfReceiverName);
                    messageWithoutCommand = senderName + ": " + messageWithoutCommand.substring(endIndexOfReceiverName + 1);
                    messageForOne(messageWithoutCommand, receiveName, senderName);
                }
                else if (cleanMessage.startsWith("@blacklist ")){
                    String messageWithoutCommand = cleanMessage.substring(10);
                    int endIndexOfReceiverName = messageWithoutCommand.indexOf(' ');
                    String blockedUsername = messageWithoutCommand.substring(endIndexOfReceiverName + 1);
                    addToBlacklist(senderName, blockedUsername);
                }
                else messageForAll(senderName, messageFromClient);
            } catch (IOException e) {
                closeAll(socket, reader, writer);
                break;
            }
        }
    }

    private void addToBlacklist(String username, String blockedUsername){
        ClientThread blockedUser = null;
        for (ClientThread user : users)
            if (user.username.equals(blockedUsername)) blockedUser = user;
        if(blockedUser==null){
            System.out.println("Wrong username!");
            return;
        }


        List<String> keys = new ArrayList<String>(blacklist.keySet());
        for (String key : keys) {
            if (key.equals(username)) {
                blacklist.get(key).add(blockedUser);
            } else {
                ArrayList<ClientThread> blockedUsers = new ArrayList<>();
                blockedUsers.add(blockedUser);
                blacklist.put(username, blockedUsers);
            }
        }
    }


    public void messageForAll(String senderName, String messageToSend){
        ClientThread sender = null;
        String msg = "";
        for(ClientThread user : users)
            if(user.username.equals(senderName)) sender = user;

        for (ClientThread user : users) {
            try {
                ArrayList<ClientThread> blockedUsers = blacklist.get(user.username);
                if (!user.username.equals(username) ) {
                    if(blockedUsers.contains(sender)) msg = "<BL> " + messageToSend;
                    else msg = messageToSend;

                    user.writer.write(msg);
                    user.writer.newLine();
                    user.writer.flush();
                }
            } catch (IOException e) {
                closeAll(socket, reader, writer);
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

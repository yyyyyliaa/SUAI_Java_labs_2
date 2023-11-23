package Lab15.src.main.java.com.example;

import java.io.*;
import java.util.*;

public class Adboard {
    private static volatile Adboard instance;
    private final Map<String, String> users = new HashMap<>(); // Хранение пользователей и паролей
    private ArrayList<Ad> ads = null;
    private static final String path = "/Users/yyyyyliaa/Lab15/src/main/java/com/example/users.txt";

    private static final String path1 = "/Users/yyyyyliaa/Lab15/src/main/java/com/example/ads.txt";

    private Adboard(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                users.put(parts[0], parts[1]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadFromFile();
    }

    public static Adboard getInstance(){
        if (instance == null) {
            synchronized (Adboard.class) {
                if (instance == null) {
                    instance = new Adboard();
                    System.out.println("TOMCAT LOH");
                }
            }
        }
        return instance;
    }

    public void newAd(String title, String text, String creator, String date){
        ads.add(new Ad(title, text, creator, date));
        saveToFile();
    }
    public void newUser(String username, String password){
        users.put(username, password);
    }

    public boolean checkLogin(String username, String password) throws IOException {
        String str = users.get(username);
        if(str==null) return false;
        else return str.equals(password);
    }

    public ArrayList<Ad> getAds(){
        return ads;
    }

    private void loadFromFile(){
        ads = new ArrayList<>();
        try {
            BufferedReader r = new BufferedReader (new InputStreamReader(new FileInputStream(path1)));
            while(r.ready()) {
                ads.add(Ad.fromString(r.readLine()));
            }
        }
        catch(IOException e) { e.printStackTrace();}

    }

    private void saveToFile(){
        try(FileWriter writer = new FileWriter(path1, false)) {
            StringBuilder sb = new StringBuilder();
            for(Ad ad : ads){
                sb.append(ad.toString()).append("\n");
            }
            writer.write(sb.toString());
            writer.flush();
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addComment(String title, String comment, String autor){
        for(Ad ad1 : ads){
            if(ad1.getTitle().equals(title)) ad1.addComment(comment, autor);
        }
    }
}

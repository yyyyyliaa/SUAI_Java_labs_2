package Lab16.src.main.java.com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Farm {
    private static volatile Farm instance;
    private HashMap<String, ArrayList<String>> animals;
    private final static String path = "/Users/yyyyyliaa/Lab16/src/main/java/com/example/data.txt";

    private Farm() {
        loadFromFile();
    }

    private void loadFromFile() {
        animals = new HashMap<>();
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            ArrayList<String> data = new ArrayList<>();
            while (r.ready()) {
                String line = r.readLine();
                if (line.startsWith("* ")) {
                    line = line.substring(2);
                    data = new ArrayList<>();
                    animals.put(line, data);
                } else if (line.startsWith("    * ")) {
                    line = line.substring(6);
                    data.add(line);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Farm getInstance() {
        if (instance == null) {
            synchronized (Farm.class) {
                if (instance == null) {
                    instance = new Farm();
                    System.out.println("TOMCAT LOH");
                }
            }
        }
        return instance;


    }

    public String convertToJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");

        for (String key : animals.keySet()) {
            json.append("\"").append(key).append("\": [");

            ArrayList<String> values = animals.get(key);
            for (int i = 0; i < values.size(); i++) {
                json.append("\"").append(values.get(i)).append("\"");
                if (i < values.size() - 1) {
                    json.append(", ");
                }
            }

            json.append("], ");
        }

        if (!animals.isEmpty()) {
            json.delete(json.length() - 2, json.length());
        }

        json.append("}");
        return json.toString();
    }
}

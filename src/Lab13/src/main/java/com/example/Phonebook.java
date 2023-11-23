package com.example;

import java.io.*;
import java.util.*;

public class Phonebook {
    private final HashMap<String, List<String>> data = new HashMap<>();

    public Phonebook() {}

    public void loadPhonebookFromFile(String filePath){
        try {
            BufferedReader r = new BufferedReader (new InputStreamReader(new FileInputStream(filePath)));
            while(r.ready()) {
                String tmp = r.readLine();
                String[] tokens = tmp.split(":");
                String[] numbers = tokens[1].split(",");
                List<String> phones = new ArrayList<>();
                Collections.addAll(phones, numbers);
                data.put(tokens[0], phones);
            }
        }
        catch(IOException e) { e.printStackTrace();}
    }

    public void savePhonebookToFile(String filePath){
        try(FileWriter writer = new FileWriter(filePath, false)) {
            Iterator<Map.Entry<String, List<String>>> iterator = data.entrySet().iterator();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; ; ++i) {
                if(iterator.hasNext()) {
                    Map.Entry<String, List<String>> entry = iterator.next();
                    sb.append(entry.getKey()).append(":");
                    for(String str : entry.getValue()){
                        sb.append(str).append(",");
                    }
                    sb.append("\n");
                } else break;
            }
            writer.write(sb.toString());
            writer.flush();
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public synchronized void add(String name, String phone) {
        List<String> tmp = new ArrayList<>();
        if(data.containsKey(name)){
            tmp = data.get(name);
        }
        tmp.add(phone);
        data.put(name, tmp);
    }

    public synchronized void edit(String name, String new_name) {
        List<String> tmp = new ArrayList<>();
        if(data.containsKey(name))tmp = data.get(name);
        data.remove(name);
        this.add(new_name, tmp);
//        data.put(new_name, tmp);
    }

    public synchronized void del(String name, String phone){
        List<String> tmp = new ArrayList<>();
        if(data.containsKey(name)){
            tmp = data.get(name);
        }
        tmp.remove(phone);
        data.put(name, tmp);
    }

    public synchronized void add(String name, List<String> phone) {
        data.put(name, phone);
    }

    public synchronized HashMap<String, List<String>> getNamesStrings() {
        return data;
    }

    public synchronized boolean contain(String name){
        return data.containsKey(name);
    }

    public synchronized void reset() {
        data.clear();
    }
}
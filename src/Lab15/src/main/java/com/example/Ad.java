package Lab15.src.main.java.com.example;

import java.util.HashMap;
import java.util.Map;

public class Ad {
    private static final String token = "/TOKEN/";

    private Map<String, String> comments = new HashMap<>();
    private String title;
    private String text;

    private String creator;
    private String date;



    public Ad(String title, String text, String creator, String date) {
        this.title = title;
        this.text = text;
        this.creator = creator;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString(){
        return title + token + text + token + creator + token + date;
    }

    public static Ad fromString(String str){
        String[] tokens = str.split(token);
        String title = tokens[0];
        String text = tokens[1];
        String creator = tokens[2];
        String date = tokens[3];

        return new Ad(title, text, creator, date);
    }

    public void addComment(String comment, String autor){
        comments.put(comment, autor);
    }

    public Map<String, String> getComments(){
        return comments;
    }
}

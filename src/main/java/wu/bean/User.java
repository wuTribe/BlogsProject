package wu.bean;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private int uid;
    private String username;
    private String password;
    private ArrayList<Article> articles;
    private Date time;

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", articles=" + articles +
                ", time=" + time +
                '}';
    }

    public User() {
    }

    public User(int uid, String username, String password, ArrayList<Article> articles, Date time) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.articles = articles;
        this.time = time;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}

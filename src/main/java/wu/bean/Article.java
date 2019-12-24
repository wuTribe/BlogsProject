package wu.bean;

import java.util.Date;

public class Article {
    private int aid;
    private String path; // 文件所在路径
    private String fileName; // 文件的名称
    private Date time; // 文件创建的名称
    private int auid; // 文章所属的用户
    private String title; // 文章的标题
    private String content; // 文章的内容

    @Override
    public String toString() {
        return "Article{" +
                "aid=" + aid +
                ", path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                ", time=" + time +
                ", auid=" + auid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Article() {
    }

    public Article(int aid, String path, String fileName, Date time, int auid, String title, String content) {
        this.aid = aid;
        this.path = path;
        this.fileName = fileName;
        this.time = time;
        this.auid = auid;
        this.title = title;
        this.content = content;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getAuid() {
        return auid;
    }

    public void setAuid(int auid) {
        this.auid = auid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

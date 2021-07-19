package fpt.provipluxurylimited.challengefocus.models;

public class Quote {
    private int id;
    private String content;

    public Quote() {
    }

    public Quote(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

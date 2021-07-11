package fpt.provipluxurylimited.challengefocus.models;

public class Feedback {
    private int star;
    private String userId;
    private String content;

    public Feedback(int star, String userId, String content) {
        this.star = star;
        this.userId = userId;
        this.content = content;
    }

    public Feedback(int star, String userId) {
        this.star = star;
        this.userId = userId;
    }

    public int getStar() {
        return star;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

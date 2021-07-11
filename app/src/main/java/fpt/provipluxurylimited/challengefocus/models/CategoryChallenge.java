package fpt.provipluxurylimited.challengefocus.models;

public class CategoryChallenge {
    private int id;
    private String imageUrl;
    private String title;

    public CategoryChallenge() {
    }

    public CategoryChallenge(int id, String imageUrl, String title) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "CategoryChallenge{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

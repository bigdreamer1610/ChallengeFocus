package fpt.provipluxurylimited.challengefocus.models;

public class CategoryChallenge {
    private int id;
    private String imageUrl;
    private String title;
    private int categoryId;
    private String categoryName;

    public CategoryChallenge() {
    }

    public CategoryChallenge(int id, String imageUrl, String title, int categoryId, String categoryName) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
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

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
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

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

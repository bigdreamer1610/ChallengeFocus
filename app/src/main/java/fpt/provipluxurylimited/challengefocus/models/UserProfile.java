package fpt.provipluxurylimited.challengefocus.models;

public class UserProfile {
    private String name;
    private String imageUrl;
    private String caption;

    public UserProfile() {
    }

    public UserProfile(String name, String imageUrl, String caption) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.caption = caption;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public String toString() {
        return "name: " + this.name + ", caption: " + this.caption;
    }
}

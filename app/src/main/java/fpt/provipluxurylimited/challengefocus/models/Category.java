package fpt.provipluxurylimited.challengefocus.models;

import java.util.ArrayList;

public class Category {
    private int id;
    private String title;
    private ArrayList<CategoryChallenge> challenges;

    public Category() {
    }

    public Category(int id, String title, ArrayList<CategoryChallenge> challenges) {
        this.id = id;
        this.title = title;
        this.challenges = challenges;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChallenges(ArrayList<CategoryChallenge> challenges) {
        this.challenges = challenges;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<CategoryChallenge> getChallenges() {
        return challenges;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", challenges=" + challenges +
                '}';
    }
}

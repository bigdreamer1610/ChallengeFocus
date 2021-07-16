package fpt.provipluxurylimited.challengefocus.models;

import java.util.ArrayList;

public class Category {
    private String title;
    private ArrayList<CategoryChallenge> challenges;

    public Category() {
    }

    public Category(String title, ArrayList<CategoryChallenge> challenges) {
        this.title = title;
        this.challenges = challenges;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<CategoryChallenge> getChallenges() {
        return challenges;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChallenges(ArrayList<CategoryChallenge> challenges) {
        this.challenges = challenges;
    }
}

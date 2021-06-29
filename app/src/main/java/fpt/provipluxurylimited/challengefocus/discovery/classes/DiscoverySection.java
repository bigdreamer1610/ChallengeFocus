package fpt.provipluxurylimited.challengefocus.discovery.classes;

import java.util.ArrayList;

import fpt.provipluxurylimited.challengefocus.models.Challenge;

public class DiscoverySection {
    private String sectionName;
    private ArrayList<Challenge> items;

    public DiscoverySection(String sectionName, ArrayList<Challenge> items) {
        this.sectionName = sectionName;
        this.items = items;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public void setItems(ArrayList<Challenge> items) {
        this.items = items;
    }

    public String getSectionName() {
        return sectionName;
    }

    public ArrayList<Challenge> getItems() {
        return items;
    }
}

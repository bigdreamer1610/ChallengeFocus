package fpt.provipluxurylimited.challengefocus.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiscoveryResult {
    private List<String> categoryNames;
    private HashMap<String, ArrayList<CategoryChallenge>> list;

    public DiscoveryResult() {
    }

    public DiscoveryResult(List<String> categoryNames, HashMap<String, ArrayList<CategoryChallenge>> list) {
        this.categoryNames = categoryNames;
        this.list = list;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public HashMap<String, ArrayList<CategoryChallenge>> getList() {
        return list;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public void setList(HashMap<String, ArrayList<CategoryChallenge>> list) {
        this.list = list;
    }
}

package fpt.provipluxurylimited.challengefocus.models;

import java.util.ArrayList;

public class MyChallenges {
    private ArrayList<Challenge> doingList;
    private ArrayList<Challenge> doneList;
    private ArrayList<Challenge> failedList;

    public MyChallenges(ArrayList<Challenge> doingList, ArrayList<Challenge> doneList, ArrayList<Challenge> failedList) {
        this.doingList = doingList;
        this.doneList = doneList;
        this.failedList = failedList;
    }

    public void setDoingList(ArrayList<Challenge> doingList) {
        this.doingList = doingList;
    }

    public void setDoneList(ArrayList<Challenge> doneList) {
        this.doneList = doneList;
    }

    public void setFailedList(ArrayList<Challenge> failedList) {
        this.failedList = failedList;
    }

    public ArrayList<Challenge> getDoingList() {
        return doingList;
    }

    public ArrayList<Challenge> getDoneList() {
        return doneList;
    }

    public ArrayList<Challenge> getFailedList() {
        return failedList;
    }
}

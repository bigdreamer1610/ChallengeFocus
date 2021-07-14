package fpt.provipluxurylimited.challengefocus.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class ToDoItem {
    private String title;
    private String date;
    private Boolean isDone;
    private String imageUrl;
    private int id;
    private Boolean isExpanded;

    public ToDoItem() {
    }

    public ToDoItem(String title, String date, Boolean isDone, String imageUrl, int id) {
        this.title = title;
        this.date = date;
        this.isDone = isDone;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public ToDoItem(String title, String date, Boolean isDone, String imageUrl, int id, Boolean isExpanded) {
        this.title = title;
        this.date = date;
        this.isDone = isDone;
        this.imageUrl = imageUrl;
        this.id = id;
        this.isExpanded = isExpanded;
    }

    public ToDoItem(String title, Boolean isDone, int id) {
        this.title = title;
        this.isDone = isDone;
        this.id = id;
    }



    protected ToDoItem(Parcel in) {
        title = in.readString();
        date = in.readString();
        byte tmpIsDone = in.readByte();
        isDone = tmpIsDone == 0 ? null : tmpIsDone == 1;
        imageUrl = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Boolean getExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }
}

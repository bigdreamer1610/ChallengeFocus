package fpt.provipluxurylimited.challengefocus.models;

public class ToDoItem {
    private String name;
    private ItemStatus status;
    private String date;

    public ToDoItem(String name, ItemStatus status, String date) {
        this.name = name;
        this.status = status;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

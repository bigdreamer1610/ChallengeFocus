package fpt.provipluxurylimited.challengefocus.models;

public class Challenge {
    private String id;
    private int challengeId;
    private String dueDate;
    private String doneDate;
    private String imageUrl;
    private int percentage;
    private String title;
    private String status;

    public Challenge() {
    }

    public Challenge(String id, int challengeId, String dueDate, String doneDate, String imageUrl, int percentage, String title, String status) {
        this.id = id;
        this.challengeId = challengeId;
        this.dueDate = dueDate;
        this.doneDate = doneDate;
        this.imageUrl = imageUrl;
        this.percentage = percentage;
        this.title = title;
        this.status = status;
    }

    public Challenge(int challengeId, String imageUrl, int percentage, String title, String status) {
        this.challengeId = challengeId;
        this.imageUrl = imageUrl;
        this.percentage = percentage;
        this.title = title;
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDoneDate(String doneDate) {
        this.doneDate = doneDate;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDoneDate() {
        return doneDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPercentage() {
        return percentage;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "id='" + id + '\'' +
                ", challengeId=" + challengeId +
                ", dueDate='" + dueDate + '\'' +
                ", doneDate='" + doneDate + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", percentage=" + percentage +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


}


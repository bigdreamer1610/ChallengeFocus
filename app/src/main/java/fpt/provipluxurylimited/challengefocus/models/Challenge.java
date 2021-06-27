package fpt.provipluxurylimited.challengefocus.models;

public class Challenge {
    private String image;
    private String name;
    private ChallengeStatus status;
    private int percentage;
    private String from;
    private String to;

    public Challenge() {
    }

    public Challenge(String image, String name, ChallengeStatus status, String from, String to) {
        this.image = image;
        this.name = name;
        this.status = status;
        this.from = from;
        this.to = to;
    }

    public Challenge(String image, String name, ChallengeStatus status, int percentage, String from, String to) {
        this.image = image;
        this.name = name;
        this.status = status;
        this.percentage = percentage;
        this.from = from;
        this.to = to;
    }

    public Challenge(String image, String name, ChallengeStatus status, int percentage) {
        this.image = image;
        this.name = name;
        this.status = status;
        this.percentage = percentage;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(ChallengeStatus status) {
        this.status = status;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public ChallengeStatus getStatus() {
        return status;
    }

    public int getPercentage() {
        return percentage;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}


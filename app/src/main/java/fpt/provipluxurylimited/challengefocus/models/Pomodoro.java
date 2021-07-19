package fpt.provipluxurylimited.challengefocus.models;

public class Pomodoro {
  private int id;
  private int cycle;
  private String endDate;
  private String mode;
  private String status;

  public Pomodoro() {
  }

  public Pomodoro(int id, int cycle, String endDate, String mode, String status) {
    this.id = id;
    this.cycle = cycle;
    this.endDate = endDate;
    this.mode = mode;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCycle() {
    return cycle;
  }

  public void setCycle(int cycle) {
    this.cycle = cycle;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}


package fpt.provipluxurylimited.challengefocus.models;

public class SettingsItem {
    private SettingType type;

    public SettingsItem(SettingType type) {
        this.type = type;
    }

    public void setType(SettingType type) {
        this.type = type;
    }

    public SettingType getType() {
        return type;
    }
}

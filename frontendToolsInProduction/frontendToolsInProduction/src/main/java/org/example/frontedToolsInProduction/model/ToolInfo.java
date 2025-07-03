package org.example.frontedToolsInProduction.model;

public class ToolInfo {
    private String idTool;
    private String name;
    private String storageLocation;
    private String availability;
    private String dateTimeAdmission;

    // Геттеры и сеттеры
    public String getIdTool() { return idTool; }
    public void setIdTool(String idTool) { this.idTool = idTool; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStorageLocation() { return storageLocation; }
    public void setStorageLocation(String storageLocation) { this.storageLocation = storageLocation; }

    public String getAvailability() {
        if (availability == null) return "false";
        if (availability.equals("1") || availability.equals("1.0")) {
            return "true";
        }
        return "false";
    }

    public void setAvailability(String availabilityRaw) {
        this.availability = availabilityRaw;
    }

    private String cleanDateTime(String dateTime) {
        if (dateTime == null) return "";
        // Заменяем T на пробел (если нужно)
        dateTime = dateTime.replace("T", " ");
        // Убираем временную зону начиная с '+', если есть
        int plusIndex = dateTime.indexOf('+');
        if (plusIndex > 0) {
            dateTime = dateTime.substring(0, plusIndex);
        }
        return dateTime.trim();
    }

    public String getDateTimeAdmission() { return dateTimeAdmission; }
    public void setDateTimeAdmission(String dateTimeAdmission) {
        this.dateTimeAdmission = cleanDateTime(dateTimeAdmission);
    }
}


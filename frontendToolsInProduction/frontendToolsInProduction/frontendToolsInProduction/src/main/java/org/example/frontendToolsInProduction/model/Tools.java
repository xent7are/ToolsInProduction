package org.example.frontendToolsInProduction.model;

import com.google.gson.annotations.SerializedName;

public class Tools {

    @SerializedName("idTool")
    private String idTool;

    @SerializedName("typeOfTool")
    private TypeOfTool typeOfTool;

    @SerializedName("storageLocation")
    private StorageLocation storageLocation;

    @SerializedName("availability")
    private boolean availability;

    @SerializedName("dateAndTimeAdmission")
    private String dateTimeAdmission;


    public String getIdTool() {
        return idTool;
    }

    public void setIdTool(String idTool) {
        this.idTool = idTool;
    }

    public String getTypeOfTool() {
        return typeOfTool != null ? typeOfTool.getArticleToolType() : null;
    }

    public void setTypeOfTool(TypeOfTool typeOfTool) {
        this.typeOfTool = typeOfTool;
    }

    public String getStorageLocation() {
        return storageLocation != null ? storageLocation.getIdPlace() : null;
    }

    public void setStorageLocation(StorageLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getDateTimeAdmission() {
        if (dateTimeAdmission != null) {
            return dateTimeAdmission.replace("T", " ");
        }
        return null;
    }

    public void setDateTimeAdmission(String dateTimeAdmission) {
        this.dateTimeAdmission = dateTimeAdmission;
    }
}
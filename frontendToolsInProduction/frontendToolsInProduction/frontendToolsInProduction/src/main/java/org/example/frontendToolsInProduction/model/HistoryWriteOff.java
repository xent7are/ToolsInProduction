package org.example.frontendToolsInProduction.model;

import com.google.gson.annotations.SerializedName;

public class HistoryWriteOff {

    @SerializedName("idWriteOff")
    private String idWriteOff;

    @SerializedName("idTool")
    private String idTool;

    @SerializedName("name")
    private String name;

    @SerializedName("dateAndTimeWriteOff")
    private String dateTimeWriteOff;

    public String getIdWriteOff() {
        return idWriteOff;
    }

    public void setIdWriteOff(String idWriteOff) {
        this.idWriteOff = idWriteOff;
    }

    public String getIdTool() {
        return idTool;
    }

    public void setIdTool(String idTool) {
        this.idTool = idTool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTimeWriteOff() {
        if (dateTimeWriteOff != null) {
            return dateTimeWriteOff.replace("T", " ");
        }
        return null;
    }

    public void setDateTimeWriteOff(String dateTimeWriteOff) {
        this.dateTimeWriteOff = dateTimeWriteOff;
    }
}

package org.example.frontendToolsInProduction.model;

import com.google.gson.annotations.SerializedName;

public class AvailableTool {
    @SerializedName("idTool")
    private String idTool;
    @SerializedName("toolTypeName")
    private String toolTypeName;
    @SerializedName("deliveryDate")
    private String deliveryDate;

    public String getIdTool() {
        return idTool;
    }

    public void setIdTool(String idTool) {
        this.idTool = idTool;
    }

    public String getToolTypeName() {
        return toolTypeName;
    }

    public void setToolTypeName(String toolTypeName) {
        this.toolTypeName = toolTypeName;
    }

    public String getDeliveryDate() {
        if (deliveryDate != null) {
            return deliveryDate.replace("T", " ");
        }
        return null;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
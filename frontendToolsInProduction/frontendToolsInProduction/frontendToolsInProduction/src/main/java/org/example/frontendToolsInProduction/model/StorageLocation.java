package org.example.frontendToolsInProduction.model;

import com.google.gson.annotations.SerializedName;

public class StorageLocation {

    @SerializedName("idPlace")
    private String idPlace;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    public String getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(String idPlace) {
        this.idPlace = idPlace;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
}

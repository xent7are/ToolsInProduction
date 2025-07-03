package org.example.frontedToolsInProduction.model;

import com.google.gson.annotations.SerializedName;

public class TypeOfTool {

    @SerializedName("articleToolType")
    private String articleToolType;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    public String getArticleToolType() {
        return articleToolType;
    }

    public void setArticleToolType(String articleToolType) {
        this.articleToolType = articleToolType;
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

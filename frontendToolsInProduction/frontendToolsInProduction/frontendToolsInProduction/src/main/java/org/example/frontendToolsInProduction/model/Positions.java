package org.example.frontendToolsInProduction.model;

import com.google.gson.annotations.SerializedName;

public class Positions {

    @SerializedName("idPosition")
    private String idPosition;

    @SerializedName("titlePosition")
    private String titlePosition;

    @SerializedName("requirements")
    private String requirements;

    @SerializedName("duties")
    private String duties;

    @SerializedName("salary")
    private String salary;

    @SerializedName("role")
    private String role;

    public String getIdPosition() {
        return idPosition;
    }

    public void setIdPosition(String idPosition) {
        this.idPosition = idPosition;
    }

    public String getTitlePosition() {
        return titlePosition;
    }

    public void setTitlePosition(String titlePosition) {
        this.titlePosition = titlePosition;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getDuties() {
        return duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

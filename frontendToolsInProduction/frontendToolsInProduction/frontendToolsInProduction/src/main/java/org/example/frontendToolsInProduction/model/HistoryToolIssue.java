package org.example.frontendToolsInProduction.model;

import com.google.gson.annotations.SerializedName;

public class HistoryToolIssue {

    public static class Id {
        @SerializedName("employee")
        private String employeeId;

        @SerializedName("tool")
        private String toolId;

        @SerializedName("dateAndTimeIssue")
        private String dateTimeIssue;

        public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }

        public String getToolId() {
            return toolId;
        }

        public void setToolId(String toolId) {
            this.toolId = toolId;
        }

        public String getDateTimeIssue() {
            if (dateTimeIssue != null) {
                return dateTimeIssue.replace("T", " ");
            }
            return null;
        }

        public void setDateTimeIssue(String dateTimeIssue) {
            this.dateTimeIssue = dateTimeIssue;
        }
    }

    @SerializedName("id")
    private Id id;

    @SerializedName("action")
    private String action;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    // Для таблицы — делаем геттеры, которые PropertyValueFactory будет искать
    public String getIdEmployee() {
        return id != null ? id.getEmployeeId() : null;
    }

    public String getIdTool() {
        return id != null ? id.getToolId() : null;
    }

    public String getDateTimeIssue() {
        return id != null ? id.getDateTimeIssue() : null;
    }
}

package org.example.frontendToolsInProduction.model;

public class EmployeeToolInfo {
    private String id;
    private String positionName;
    private String surname;
    private String name;
    private String patronymic;
    private String jobTitle;
    private String phone;
    private String email;
    private String admissionDate;


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPositionName() { return positionName; }
    public void setPositionName(String positionName) { this.positionName = positionName; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getName() { return name; }
    public void setName(String middleName) { this.name = middleName; }

    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone != null ? phone.trim() : null; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    private String cleanDateTime(String dateTime) {
        if (dateTime == null) return "";
        dateTime = dateTime.replace("T", " ");
        int plusIndex = dateTime.indexOf('+');
        if (plusIndex > 0) {
            dateTime = dateTime.substring(0, plusIndex);
        }
        return dateTime.trim();
    }

    public String getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = cleanDateTime(admissionDate);
    }
}

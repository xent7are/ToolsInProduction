package org.example.frontendToolsInProduction.model;

import com.google.gson.annotations.SerializedName;

public class Employees {

    @SerializedName("idEmployee")
    private String idEmployee;

    @SerializedName("position")
    private Positions idPosition;

    @SerializedName("surname")
    private String surname;

    @SerializedName("name")
    private String name;

    @SerializedName("patronymic")
    private String patronymic;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("email")
    private String email;

    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getIdPosition() {
        return idPosition != null ? idPosition.getIdPosition() : null;
    }

    public void setIdPosition(Positions idPosition) {
        this.idPosition = idPosition;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

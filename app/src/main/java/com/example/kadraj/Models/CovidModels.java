package com.example.kadraj.Models;

public class CovidModels {
    private String todayTestNumber, todayCaseNumber, todayPatientNumber, todayDeathNumber, todayHealingNumber;
    private String allTestNumber, allCaseNumber, allHeavyPatientNumber, allDeathNumber, allHealingNumber;
    private String date;

    public CovidModels(String date, String todayTestNumber, String todayCaseNumber, String todayPatientNumber, String todayDeathNumber, String todayHealingNumber, String allTestNumber, String allCaseNumber, String allHeavyPatientNumber, String allDeathNumber, String allHealingNumber) {
        this.date = date;
        this.todayTestNumber = todayTestNumber;
        this.todayCaseNumber = todayCaseNumber;
        this.todayPatientNumber = todayPatientNumber;
        this.todayDeathNumber = todayDeathNumber;
        this.todayHealingNumber = todayHealingNumber;
        this.allTestNumber = allTestNumber;
        this.allCaseNumber = allCaseNumber;
        this.allHeavyPatientNumber = allHeavyPatientNumber;
        this.allDeathNumber = allDeathNumber;
        this.allHealingNumber = allHealingNumber;

    }

    public String getDate() {
        return date;
    }

    public String getTodayTestNumber() {
        return todayTestNumber;
    }

    public String getTodayCaseNumber() {
        return todayCaseNumber;
    }

    public String getTodayPatientNumber() {
        return todayPatientNumber;
    }

    public String getTodayDeathNumber() {
        return todayDeathNumber;
    }

    public String getTodayHealingNumber() {
        return todayHealingNumber;
    }

    public String getAllTestNumber() {
        return allTestNumber;
    }

    public String getAllCaseNumber() {
        return allCaseNumber;
    }

    public String getAllHeavyPatientNumber() {
        return allHeavyPatientNumber;
    }

    public String getAllDeathNumber() {
        return allDeathNumber;
    }

    public String getAllHealingNumber() {
        return allHealingNumber;
    }
}

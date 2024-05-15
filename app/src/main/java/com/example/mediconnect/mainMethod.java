package com.example.mediconnect;

public class mainMethod {

    String name,date,days,bill,diagnosis,medication;

    mainMethod(){}
    public mainMethod(String name, String date, String days, String bill, String diagnosis, String medication) {
        this.name = name;
        this.date = date;
        this.days = days;
        this.bill = bill;
        this.diagnosis = diagnosis;
        this.medication = medication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getbill() {
        return this.bill;
    }

    public void setbill(String bill) {
        this.bill = bill;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }
}

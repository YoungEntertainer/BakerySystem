package za.co.bakerysystem.model;

import java.time.LocalDate;

public class SalesReport {

    private int ID;
    private LocalDate date;
    private Integer hours;
    private String comment;

    public SalesReport(int ID, LocalDate date, Integer hours, String comment) {
        this.ID = ID;
        this.date = date;
        this.hours = hours;
        this.comment = comment;
    }

    public SalesReport(Integer hours, String comment) {
        this.hours = hours;
        this.comment = comment;
    }

    public SalesReport() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "SalesReport{" + "ID=" + ID + ", date=" + date + ", hours=" + hours + ", comment=" + comment + '}';
    }

}

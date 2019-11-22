package com.chumbok.pos.dto;

public class DateDTO {
    private int month;
    private int year;

    public DateDTO(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public DateDTO() {
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

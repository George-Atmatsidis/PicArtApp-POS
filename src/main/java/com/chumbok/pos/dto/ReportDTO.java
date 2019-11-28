package com.chumbok.pos.dto;

public class ReportDTO {
    private int month;
    private int year;
    private long totalSalesOrRentsThisMonth;
    private String userWhoSoldOrRentTheMost;
    private long howMuchThatMadafackerSoldOrRentThatMonth;
    private long howMuchMoneyWasMadeThatMonth;

    public ReportDTO(int month, int year, long totalSalesOrRentsThisMonth, String userWhoSoldOrRentTheMost) {
        this.month = month;
        this.year = year;
        this.totalSalesOrRentsThisMonth = totalSalesOrRentsThisMonth;
        this.userWhoSoldOrRentTheMost = userWhoSoldOrRentTheMost;
    }

    public ReportDTO() {
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

    public long getTotalSalesOrRentsThisMonth() {
        return totalSalesOrRentsThisMonth;
    }

    public void setTotalSalesOrRentsThisMonth(long totalSalesOrRentsThisMonth) {
        this.totalSalesOrRentsThisMonth = totalSalesOrRentsThisMonth;
    }

    public String getUserWhoSoldOrRentTheMost() {
        return userWhoSoldOrRentTheMost;
    }

    public void setUserWhoSoldOrRentTheMost(String userWhoSoldOrRentTheMost) {
        this.userWhoSoldOrRentTheMost = userWhoSoldOrRentTheMost;
    }

    public long getHowMuchThatMadafackerSoldOrRentThatMonth() {
        return howMuchThatMadafackerSoldOrRentThatMonth;
    }

    public void setHowMuchThatMadafackerSoldOrRentThatMonth(long howMuchThatMadafackerSoldOrRentThatMonth) {
        this.howMuchThatMadafackerSoldOrRentThatMonth = howMuchThatMadafackerSoldOrRentThatMonth;
    }

    public long getHowMuchMoneyWasMadeThatMonth() {
        return howMuchMoneyWasMadeThatMonth;
    }

    public void setHowMuchMoneyWasMadeThatMonth(long howMuchMoneyWasMadeThatMonth) {
        this.howMuchMoneyWasMadeThatMonth = howMuchMoneyWasMadeThatMonth;
    }
}

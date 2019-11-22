package com.chumbok.pos.dto;

import java.util.Date;

public class InventoryDTO {
    private int mes;
    private int año;
    private long totalModificationsThisMonth;
    private long totalAltasSaidMonth;
    private long totalBajasInSaidMonth;

    public InventoryDTO() {
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public long getTotalModificationsThisMonth() {
        return totalModificationsThisMonth;
    }

    public void setTotalModificationsThisMonth(long totalModificationsThisMonth) {
        this.totalModificationsThisMonth = totalModificationsThisMonth;
    }

    public long getTotalAltasSaidMonth() {
        return totalAltasSaidMonth;
    }

    public void setTotalAltasSaidMonth(long totalAltasSaidMonth) {
        this.totalAltasSaidMonth = totalAltasSaidMonth;
    }

    public long getTotalBajasInSaidMonth() {
        return totalBajasInSaidMonth;
    }

    public void setTotalBajasInSaidMonth(long totalBajasInSaidMonth) {
        this.totalBajasInSaidMonth = totalBajasInSaidMonth;
    }
}

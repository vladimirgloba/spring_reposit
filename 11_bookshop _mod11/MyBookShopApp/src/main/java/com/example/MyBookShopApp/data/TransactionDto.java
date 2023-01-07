package com.example.MyBookShopApp.data;

import java.text.DecimalFormat;

public class TransactionDto {
    private String time;
    private String summa;
    private String description;

    public TransactionDto() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSumma() {
        return summa;
    }

    public void setSumma(int summa) {
        DecimalFormat decimalFormat = new DecimalFormat( "#.##" );
        String result = decimalFormat.format(summa);
        this.summa = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

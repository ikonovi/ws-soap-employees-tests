package ik.soaptest.soaptest.models;

import java.util.Objects;
import java.util.StringJoiner;

public class Workdays {
    private int id;
    private int wdNumber;
    private int adNumber;
    private String month;

    public Workdays() {
    }

    public Workdays(int id, int wdNumber, int adNumber, String month) {
        this.id = id;
        this.wdNumber = wdNumber;
        this.adNumber = adNumber;
        this.month = month;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Number of working days in the month represented by this record.
     */
    public int getWdNumber() {
        return wdNumber;
    }

    public void setWdNumber(int wdNumber) {
        this.wdNumber = wdNumber;
    }

    /**
     * @return Number of holidays days of weekends in the month.
     */
    public int getAdNumber() {
        return adNumber;
    }

    public void setAdNumber(int adNumber) {
        this.adNumber = adNumber;
    }

    /**
     * @return Month in format mm-yyyy
     */
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workdays workdays = (Workdays) o;
        return id == workdays.id &&
                wdNumber == workdays.wdNumber &&
                adNumber == workdays.adNumber &&
                Objects.equals(month, workdays.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wdNumber, adNumber, month);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Workdays.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("wdNumber=" + wdNumber)
                .add("adNumber=" + adNumber)
                .add("month='" + month + "'")
                .toString();
    }
}

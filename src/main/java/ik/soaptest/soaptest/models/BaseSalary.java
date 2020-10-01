package ik.soaptest.soaptest.models;

import ik.soaptest.soaptest.db.BaseSalaryDAO;

import java.util.Objects;
import java.util.StringJoiner;

public class BaseSalary {
    private int id;
    private float salaryAmount;
    private String profName;

    public BaseSalary() {
    }

    public BaseSalary(int recordId) {
        id = recordId;
        BaseSalaryDAO dao = new BaseSalaryDAO();
        BaseSalary salary = dao.fetchBaseSalaryById(recordId);
        salaryAmount = salary.getSalaryAmount();
        this.profName = salary.getProfName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(float salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseSalary that = (BaseSalary) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BaseSalary.class.getSimpleName() + "[", "]")
                .add("Id='" + id + "'")
                .add("salaryAmount=" + salaryAmount)
                .add("profName='" + profName + "'")
                .toString();
    }
}

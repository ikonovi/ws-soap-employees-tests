package ik.soaptest.models;

import java.util.Objects;
import java.util.StringJoiner;

public class Employee implements Comparable<Employee> {
    private int id;
    private String privateId;
    private String firstName;
    private String lastName;
    private String middleName;
    private int experienceInYears;
    private BaseSalary baseSalary;

    public Employee() {
    }

    public Employee(int id, String privateId, String firstName, String lastName, String middleName, int experienceInYears,
                    int professionId) {
        this.id = id;
        this.privateId = privateId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.experienceInYears = experienceInYears;
        baseSalary = new BaseSalary(professionId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrivateId() {
        return privateId;
    }

    public void setPrivateId(String privateId) {
        this.privateId = privateId.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName.trim();
    }

    public int getExperienceInYears() {
        return experienceInYears;
    }

    public void setExperienceInYears(int experienceInYears) {
        this.experienceInYears = experienceInYears;
    }

    public BaseSalary getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BaseSalary baseSalary) {
        this.baseSalary = baseSalary;
    }

    @Override
    public int compareTo(Employee o) {
        int comp = privateId.compareTo(o.privateId);
        if (comp == 0) {
            comp = firstName.compareTo(o.firstName);
            if ((comp == 0)) {
                comp = lastName.compareTo(o.lastName);
                if (comp == 0) {
                    comp = middleName.compareTo(o.middleName);
                    if (comp == 0) {
                        comp = experienceInYears - o.experienceInYears;
                    }
                }
            }
        }
        return comp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                experienceInYears == employee.experienceInYears &&
                Objects.equals(privateId, employee.privateId) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(middleName, employee.middleName) &&
                baseSalary.equals(employee.baseSalary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, privateId, firstName, lastName, middleName, experienceInYears, baseSalary);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Employee.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("privateId='" + privateId + "'")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("middleName='" + middleName + "'")
                .add("experienceInYears=" + experienceInYears)
                .add("baseSalary=" + baseSalary)
                .toString();
    }
}

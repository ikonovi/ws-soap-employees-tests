package ik.soaptest.db;

import ik.soaptest.models.BaseSalary;
import ik.soaptest.models.Employee;

import java.sql.*;

public class EmployeeDAO extends AbstractDAO {

    private String selectEmployeeByIdQuery = "SELECT " +
            "emp.[id] emp_id, " +
            "emp.[private_id], " +
            "emp.[first_name], " +
            "emp.[last_name], " +
            "emp.[middle_name], " +
            "emp.[exp], " +
            "sal.[id] sal_id, " +
            "sal.[salary_amount], " +
            "sal.[prof_name] " +
        "FROM employees emp " +
        "JOIN base_salaries sal ON sal.[id] = emp.[profession_id]" +
        "WHERE emp.[id] = ?";

    public EmployeeDAO() {
        super();
    }

    public Employee selectEmployeeById(int id) {
        Employee employee = new Employee();
        try (Connection conn = pooledConn.getConnection();
             PreparedStatement pst = conn.prepareStatement(selectEmployeeByIdQuery)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    employee.setId(rs.getInt("emp_id"));
                    employee.setPrivateId(rs.getNString("private_id"));
                    employee.setFirstName(rs.getNString("first_name"));
                    employee.setLastName(rs.getNString("last_name"));
                    employee.setMiddleName(rs.getNString("middle_name"));
                    employee.setExperienceInYears(rs.getInt("exp"));
                    BaseSalary baseSalary = new BaseSalary();
                    baseSalary.setId(rs.getInt("sal_id"));
                    baseSalary.setSalaryAmount(rs.getFloat("salary_amount"));
                    baseSalary.setProfName(rs.getNString("prof_name"));
                    employee.setBaseSalary(baseSalary);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Fetched from DB: " + employee);
        return employee;
    }

}

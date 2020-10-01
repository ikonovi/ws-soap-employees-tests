package ik.soaptest.soaptest.db;

import ik.soaptest.soaptest.models.BaseSalary;
import ik.soaptest.soaptest.models.Employee;

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
        "FROM [dbo].[employees] emp " +
        "JOIN [dbo].[base_salaries] sal ON sal.[id] = emp.[profession_id]" +
        "WHERE emp.[id] = ?";

    private String deleteEmployeeByIdQuery = "DELETE FROM [dbo].[employees] where id = ?";

    public EmployeeDAO() {
        super();
    }

    public void deleteEmployeeById(int id) {
        try (Connection conn = pooledConn.getConnection();
             PreparedStatement pst = conn.prepareStatement(deleteEmployeeByIdQuery)) {
            pst.setInt(1, id);
            int rowCount = pst.executeUpdate();
            System.out.println("Deleted rows: " + rowCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

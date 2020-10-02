package ik.soaptest.soaptest.db;

import ik.soaptest.soaptest.models.BaseSalary;
import ik.soaptest.soaptest.models.Employee;

import java.sql.*;

public class EmployeeDAO extends AbstractDAO {

    private String selectEmployeeSalaryQuery = "SELECT " +
            "emp.[id] emp_id, " +
            "emp.[private_id], " +
            "emp.[first_name], " +
            "emp.[last_name], " +
            "emp.[middle_name], " +
            "emp.[exp], " +
            "sal.[id] sal_id, " +
            "sal.[salary_amount], " +
            "sal.[prof_name] " +
            "FROM [dbo].[employees] emp \n" +
            "JOIN [dbo].[base_salaries] sal ON sal.[id] = emp.[profession_id] \n";
    private String clauseEmployeeById = "WHERE emp.[id] = ?";
    private String clauseEmployeePrivateId = "WHERE emp.[private_id] = ?";

    public EmployeeDAO() {
        super();
    }

    public Employee selectEmployeeByPrivateId(String privateId) {
        Employee employee = new Employee();
        try (Connection conn = pooledConn.getConnection();
             PreparedStatement pst = conn.prepareStatement(selectEmployeeSalaryQuery + clauseEmployeePrivateId)) {
            pst.setString(1, privateId);
            try (ResultSet resultSet = pst.executeQuery()) {
                initializeEmployee(employee, resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("selectEmployeeByPrivateId: " + employee);
        return employee;
    }

    public Employee selectEmployeeById(int id) {
        Employee employee = new Employee();
        try (Connection conn = pooledConn.getConnection();
             PreparedStatement pst = conn.prepareStatement(selectEmployeeSalaryQuery + clauseEmployeeById)) {
            pst.setInt(1, id);
            try (ResultSet resultSet = pst.executeQuery()) {
                initializeEmployee(employee, resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("selectEmployeeById: " + employee);
        return employee;
    }

    public void deleteEmployeeById(int id) {
        String deleteEmployeeByIdQuery = "DELETE FROM [dbo].[employees] where id = ?";
        try (Connection conn = pooledConn.getConnection();
             PreparedStatement pst = conn.prepareStatement(deleteEmployeeByIdQuery)) {
            pst.setInt(1, id);
            int rowCount = pst.executeUpdate();
            System.out.println("Deleted rows: " + rowCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize Employee object fields by values from ResultSet object.
     *
     */
    private void initializeEmployee(Employee employee, ResultSet rs) throws SQLException {
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
}

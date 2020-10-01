package ik.soaptest.db;

import ik.soaptest.models.BaseSalary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseSalaryDAO extends AbstractDAO {

    private String selectIdByProfNameQuery = "SELECT id, prof_name \n" +
            "\tFROM [dbo].[base_salaries]\n" +
            "\tWHERE prof_name = ?";

    private String selectBaseSalaryByProfNameQuery = "SELECT id, salary_amount, prof_name \n" +
            "\tFROM [dbo].[base_salaries]\n" +
            "\tWHERE prof_name = ?";

    private String selectBaseSalaryByIdQuery = "SELECT id, salary_amount, prof_name \n" +
            "\tFROM [dbo].[base_salaries]\n" +
            "\tWHERE id = ?";

    public BaseSalaryDAO() {
        super();
    }

    public BaseSalary fetchBaseSalaryById(int id) {
        BaseSalary baseSalary = new BaseSalary();
        try (Connection conn = pooledConn.getConnection();
             PreparedStatement pst = conn.prepareStatement(selectBaseSalaryByIdQuery)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    baseSalary.setId(rs.getInt("id"));
                    baseSalary.setSalaryAmount(rs.getInt("salary_amount"));
                    baseSalary.setProfName(rs.getNString("prof_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Fetched by id=" + id + " - " + baseSalary);
        return baseSalary;
    }

    public BaseSalary fetchBaseSalaryByProfName(String profName) {
        BaseSalary baseSalary = new BaseSalary();
        try (Connection conn = pooledConn.getConnection();
             PreparedStatement pst = conn.prepareStatement(selectBaseSalaryByProfNameQuery)) {
            pst.setString(1, profName);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    baseSalary.setId(rs.getInt("id"));
                    baseSalary.setSalaryAmount(rs.getInt("salary_amount"));
                    baseSalary.setProfName(rs.getNString("prof_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Fetched by prof_name" + profName + " - " + baseSalary);
        return baseSalary;
    }

    public int selectIdByProfName(String profName) {
        int id = 0;
        try (Connection conn = pooledConn.getConnection();
             PreparedStatement pst = conn.prepareStatement(selectIdByProfNameQuery)) {
            pst.setString(1, profName);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Selected ID = " + id + " by profName=" + profName + " in base_salaries DB table.");
        return id;
    }

}

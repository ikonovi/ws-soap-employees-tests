package ik.soaptest.soaptest.db;

import ik.soaptest.soaptest.models.Workdays;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkDaysDAO extends AbstractDAO {

    private String selectWorkdaysQuery = "SELECT [id]\n" +
            "      ,[wd_number]\n" +
            "      ,[ad_number]\n" +
            "      ,[month]\n" +
            "  FROM [dbo].[work_days] \n";
    private String clauseWorkdaysByMonth = "WHERE [month] = ?";

    public WorkDaysDAO() {
        super();
    }

    public Workdays selectWorkdaysByMonth(String month){
        Workdays workdays = new Workdays();
        try (Connection conn = pooledConn.getConnection();
             PreparedStatement pst = conn.prepareStatement(selectWorkdaysQuery + clauseWorkdaysByMonth)) {
            pst.setString(1, month);
            try (ResultSet resultSet = pst.executeQuery()) {
                initializeWorkdays(workdays, resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("selectWorkdaysByMonth: " + workdays);
        return workdays;
    }

    private void initializeWorkdays(Workdays workdays, ResultSet rs) throws SQLException {
        while (rs.next()) {
            workdays.setId(rs.getInt("id"));
            workdays.setWdNumber(rs.getInt("wd_number"));
            workdays.setAdNumber(rs.getInt("ad_number"));
            workdays.setMonth(rs.getNString("month"));
        }
    }
}

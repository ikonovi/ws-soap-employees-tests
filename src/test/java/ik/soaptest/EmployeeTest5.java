package ik.soaptest;

import ik.soaptest.soaptest.db.EmployeeDAO;
import ik.soaptest.soaptest.db.WorkDaysDAO;
import ik.soaptest.soaptest.models.Employee;
import ik.soaptest.soaptest.models.Workdays;
import ik.soaptest.soaptest.ws.WsClient;
import ik.soaptest.soaptest.ws.WsGetEmployeeSalaryResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class EmployeeTest5 {
    private WsClient wsClient;
    private EmployeeDAO employeeDao;
    private WorkDaysDAO workdaysDAO;

    @BeforeClass
    public void beforeClass() {
        wsClient = new WsClient();
        employeeDao = new EmployeeDAO();
        workdaysDAO = new WorkDaysDAO();
    }

    @Test
    @Parameters({"employeePrivateId", "month", "sickDays", "overDays", "isPrivileges"})
    public void testGetEmpSalary(String employeePrivateId, String month, int sickDays, int overDays, int isPrivileges) {
        Employee employee = employeeDao.selectEmployeeByPrivateId(employeePrivateId);
        Workdays workdays = workdaysDAO.selectWorkdaysByMonth(month);
        WsGetEmployeeSalaryResponse wsResponse = wsClient.GetEmployeeSalary(employee, workdays,
                sickDays, overDays, isPrivileges);

        Assert.assertEquals(wsResponse.getHttpStatusCode(), 200, "HTTP Status Code");
        Assert.assertEquals(wsResponse.getEmployeeSalaryResult(), "480", "Employee Salary");
    }
}

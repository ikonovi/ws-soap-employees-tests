package ik.soaptest;

import ik.soaptest.soaptest.db.EmployeeDAO;
import ik.soaptest.soaptest.models.Employee;
import ik.soaptest.soaptest.ws.WsAddEmployeeResponse;
import ik.soaptest.soaptest.ws.WsClient;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.ThreadLocalRandom;

public class EmployeeTest1 {

    private WsClient wsClient;
    private EmployeeDAO employeeDao;
    private int id;

    @BeforeClass
    public void beforeClass() {
        wsClient = new WsClient();
        employeeDao = new EmployeeDAO();
        // Primary Key for a new record in database table.
        id = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Test
    @Parameters({"privateId", "firstName", "lastName", "middleName", "experienceInYears", "professionId"})
    public void testAddNewEmployee(String privateId, String firstName, String lastName, String middleName,
                                   int experienceInYears, int professionId) {
        Employee newEmployee = new Employee(id, privateId, firstName, lastName, middleName, experienceInYears, professionId);
        WsAddEmployeeResponse wsResponse = wsClient.AddNewEmployee(newEmployee);
        Assert.assertEquals(wsResponse.getHttpStatusCode(), 200, "HTTP response status code");
        Assert.assertEquals(wsResponse.getResultMessage(), "Данные добавлены успешно", "Result message");

        Employee persistedEmployee = employeeDao.selectEmployeeById(id);
        Assert.assertEquals(persistedEmployee, newEmployee);
    }

    //@AfterClass
    public void afterClass() {
        employeeDao.deleteEmployeeById(id);
    }
}

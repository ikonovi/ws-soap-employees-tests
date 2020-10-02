package ik.soaptest;

import ik.soaptest.soaptest.db.EmployeeDAO;
import ik.soaptest.soaptest.models.Employee;
import ik.soaptest.soaptest.ws.WsAddEmployeeResponse;
import ik.soaptest.soaptest.ws.WsClient;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class EmployeeTest3 {

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

    @Test(dataProvider = "NewEmployeesWithEmptyField")
    public void testAddNewEmployeeWithEmptyField(int id, String privateId,
                                                 String firstName, String lastName, String middleName,
                                                 int experienceInYears, int professionId) {
        Employee employee = new Employee(id, privateId,
                firstName, lastName, middleName,
                experienceInYears, professionId);
        WsAddEmployeeResponse wsResponse = wsClient.AddNewEmployee(employee);
        Assert.assertEquals(wsResponse.getHttpStatusCode(), 200, "HTTP Response Status Code");
        Assert.assertEquals(wsResponse.getResultMessage(), "Указаны не все параметры", "Result Message");
    }

    @DataProvider(name = "NewEmployeesWithEmptyField")
    public Object[][] generateEmployeesWithEmptyField() {
        List<Object[]> entries = new ArrayList<>();
        entries.add(new Object[]{ 0, "p113", "Ivan", "Ivanov", "Ivanovich", 10, 6});
        entries.add(new Object[]{id,     "", "Ivan", "Ivanov", "Ivanovich", 10, 6});
        entries.add(new Object[]{id, "p113",     "", "Ivanov", "Ivanovich", 10, 6});
        entries.add(new Object[]{id, "p113", "Ivan",       "", "Ivanovich", 10, 6});
        entries.add(new Object[]{id, "p113", "Ivan", "Ivanov",          "", 10, 6});
        entries.add(new Object[]{id, "p113", "Ivan", "Ivanov", "Ivanovich",  0, 6});
        entries.add(new Object[]{id, "p113", "Ivan", "Ivanov", "Ivanovich", 10, 0});
        return entries.toArray(new Object[entries.size()][]);
    }
}

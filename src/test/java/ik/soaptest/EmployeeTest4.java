package ik.soaptest;

import ik.soaptest.soaptest.models.Employee;
import ik.soaptest.soaptest.ws.WsClient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class EmployeeTest4 {

    private WsClient wsClient;

    @BeforeMethod
    public void setUp() {
        wsClient = new WsClient();
    }

    @Parameters({"employeePrivateId"})
    @Test()
    public void testSearchByPrivateId(String employeePrivateId) {
        Employee employee = wsClient.GetEmpByPI(employeePrivateId);
        System.out.println("Service returned: " + employee);
        //Employee e1 = new Employee("w", "v", "", "", 0, "a", 0);
        //Employee e2 = new Employee("w", "c", "", "", 0, "a", 0);
        //System.out.printf("" + e1.compareTo(e2));
    }
}

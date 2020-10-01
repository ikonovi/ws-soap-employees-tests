package ik.soaptest;

import ik.soaptest.soaptest.models.Employee;
import ik.soaptest.soaptest.ws.WsAddEmployeeResponse;
import ik.soaptest.soaptest.ws.WsClient;
import ik.soaptest.soaptest.ws.WsGetEmpByPiResponse;
import org.testng.Assert;
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
        WsGetEmpByPiResponse wsResponse = wsClient.GetEmpByPi(employeePrivateId);
        Assert.assertEquals(wsResponse.getHttpStatusCode(), 200, "HTTP response status code");
        Assert.assertNotEquals(wsResponse.getResultMessage(), "Работники по указанным параметрам не найдены",
                "WS result message");
    }
}

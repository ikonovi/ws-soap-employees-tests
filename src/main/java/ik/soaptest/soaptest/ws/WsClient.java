package ik.soaptest.soaptest.ws;

import ik.soaptest.soaptest.models.Employee;
import ik.soaptest.soaptest.models.Workdays;
import ik.soaptest.soaptest.xml.sax.ResponseBodyParser;
import ik.soaptest.soaptest.xml.sax.AddEmployeeHandler;
import ik.soaptest.soaptest.xml.sax.GetEmployeeSalaryHandler;
import ik.soaptest.soaptest.xml.sax.GetEmployeeHandler;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;


public class WsClient {

    private static final String WS_ENDPOINT = "http://172.20.68.55:1060/service/Service.asmx";
    private ResponseBodyParser responseBodyParser = new ResponseBodyParser();
    private List<Employee> employees;
    private CloseableHttpClient client;

    public WsGetEmployeeSalaryResponse GetEmployeeSalary(Employee employee, Workdays workdays, int sickDays, int overDays, int isPrivileges) {
        System.out.println("GetEmpSalary: ");
        WsGetEmployeeSalaryResponse wsResponse = new WsGetEmployeeSalaryResponse();
        String xmlDoc = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"Service\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <ser:GetEmpSalary>\n" +
                "         <ser:pi>" + employee.getPrivateId() + "</ser:pi>\n" +
                "         <ser:workDays>" + workdays.getWdNumber() + "</ser:workDays>\n" +
                "         <ser:sickDays>" + sickDays + "</ser:sickDays>\n" +
                "         <ser:overDays>" + overDays + "</ser:overDays>\n" +
                "         <ser:month>" + workdays.getMonth() + "</ser:month>\n" +
                "         <ser:isPriv>" + isPrivileges + "</ser:isPriv>\n" +
                "      </ser:GetEmpSalary>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        System.out.println("Request body: " + xmlDoc);
        HttpPost request = buildWsRequest("Service/GetEmpSalary", xmlDoc);
        try (CloseableHttpResponse httpResponse = client.execute(request)) {
            wsResponse.setHttpStatusCode(httpResponse.getStatusLine().getStatusCode());
            HttpEntity respEntity = httpResponse.getEntity();
            String responseXmlDoc = EntityUtils.toString(respEntity);
            // parse XML doc
            GetEmployeeSalaryHandler handler = new GetEmployeeSalaryHandler();
            String salary = responseBodyParser.getEmployeeSalaryFromXml(responseXmlDoc, handler);
            wsResponse.setEmployeeSalaryResult(salary);
            System.out.println("WS returned employee salary = " + salary);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wsResponse;
    }

    /**
     * Create new Employee
     *
     * @param employee
     * @return custom response suitable for verification in test.
     * NOTE: to pass empty Employee field value in request, set it 0.
     */
    public WsAddEmployeeResponse AddNewEmployee(Employee employee) {
        System.out.println("AddNewEmployee: " + employee);
        WsAddEmployeeResponse wsResponse = new WsAddEmployeeResponse();
        // How to pass empty values for ws request:
        // Numeric value of 0 means "" empty element value in Xml.
        String newId = employee.getId() == 0 ? "" : String.valueOf(employee.getId());
        String newExp = employee.getExperienceInYears() == 0 ? "" : String.valueOf(employee.getExperienceInYears());
        String newProfessionId = employee.getBaseSalary().getId() == 0 ? "" : String.valueOf(employee.getBaseSalary().getId());

        String xmlDoc = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"Service\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <ser:AddNewEmployee>\n" +
                "         <ser:newID>" + newId + "</ser:newID>\n" +
                "         <ser:newPrivate_id>" + employee.getPrivateId() + "</ser:newPrivate_id>\n" +
                "         <ser:newFirst_name>" + employee.getFirstName() + "</ser:newFirst_name>\n" +
                "         <ser:newLast_name>" + employee.getLastName() + "</ser:newLast_name>\n" +
                "         <ser:newMiddle_name>" + employee.getMiddleName() + "</ser:newMiddle_name>\n" +
                "         <ser:newExp>" + newExp + "</ser:newExp>\n" +
                "         <ser:newProfession_id>" + newProfessionId + "</ser:newProfession_id>\n" +
                "      </ser:AddNewEmployee>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        System.out.println("Request body: " + xmlDoc);
        HttpPost request = buildWsRequest("Service/AddNewEmployee", xmlDoc);

        try (CloseableHttpResponse response = client.execute(request)) {
            wsResponse.setHttpStatusCode(response.getStatusLine().getStatusCode());
            HttpEntity respEntity = response.getEntity();
            String xmlDocumentContent = EntityUtils.toString(respEntity);
            // parse
            AddEmployeeHandler handler = new AddEmployeeHandler();
            wsResponse.setResultMessage(
                    responseBodyParser.getAddEmployeeResponseResultFromXml(xmlDocumentContent, handler));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wsResponse;
    }

    /**
     * Get employee by private ID.
     */
    public WsGetEmpByPiResponse GetEmpByPi(String privateId) {
        WsGetEmpByPiResponse wsResponse = new WsGetEmpByPiResponse();
        String reqBodyXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"Service\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <ser:GetEmpByPI>\n" +
                "         <ser:pi>" + privateId + "</ser:pi>\n" +
                "      </ser:GetEmpByPI>\n" +
                "   </soapenv:Body>\n" +
        "</soapenv:Envelope>";
        HttpPost request = buildWsRequest("Service/GetEmpByPI", reqBodyXml);

        try (CloseableHttpResponse httpResponse = client.execute(request)) {
            wsResponse.setHttpStatusCode(httpResponse.getStatusLine().getStatusCode());
            HttpEntity respEntity = httpResponse.getEntity();
            String xmlDocumentContent = EntityUtils.toString(respEntity);
            // parse XML doc
            GetEmployeeHandler handler = new GetEmployeeHandler();
            employees = responseBodyParser.getEmployeesFromXml(xmlDocumentContent, handler);
            wsResponse.setEmployee(employees.get(0));
            wsResponse.setResultMessage(employees.get(0).getPrivateId());
            System.out.println("WS returned " + employees.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wsResponse;
    }

    private HttpPost buildWsRequest(String soapAction, String xmlDoc) {
        StringEntity reqBodyEntity = new StringEntity(xmlDoc, ContentType.TEXT_XML);
        client = HttpClients.createDefault();
        HttpPost req = new HttpPost(WS_ENDPOINT);
        req.addHeader("Content-Type", "text/xml;charset=UTF-8");
        req.addHeader("SOAPAction", soapAction);
        req.setEntity(reqBodyEntity);
        return req;
    }
}

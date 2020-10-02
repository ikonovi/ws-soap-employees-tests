package ik.soaptest.soaptest.ws;

import ik.soaptest.soaptest.models.Employee;
import ik.soaptest.soaptest.service.EmployeeService;
import ik.soaptest.soaptest.xml.sax.AddEmployeeHandler;
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

    private EmployeeService employeeService = new EmployeeService();
    private List<Employee> employees;
    private CloseableHttpClient client;

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

        // Numeric value of 0 means "" empty element value in Xml.
        String newId = employee.getId() == 0 ? "" : String.valueOf(employee.getId());
        String newExp = employee.getExperienceInYears() == 0 ? "" : String.valueOf(employee.getExperienceInYears());
        String newProfessionId = employee.getBaseSalary().getId() == 0 ? "" : String.valueOf(employee.getBaseSalary().getId());

        String envelopeXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"Service\">\n" +
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
        System.out.println("Request body: " + envelopeXml);
        StringEntity reqBodyEntity = new StringEntity(envelopeXml, ContentType.TEXT_XML);
        HttpPost request = SoapPostRequest("Service/AddNewEmployee", reqBodyEntity);

        try (CloseableHttpResponse response = client.execute(request)) {
            wsResponse.setHttpStatusCode(response.getStatusLine().getStatusCode());
            HttpEntity respEntity = response.getEntity();
            String xmlDocumentContent = EntityUtils.toString(respEntity);
            // parse
            AddEmployeeHandler handler = new AddEmployeeHandler();
            wsResponse.setResultMessage(
                    employeeService.getAddEmployeeResponseResultFromXml(xmlDocumentContent, handler));
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
        StringEntity reqBodyEntity = new StringEntity(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"Service\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <ser:GetEmpByPI>\n" +
                        "         <ser:pi>" + privateId + "</ser:pi>\n" +
                        "      </ser:GetEmpByPI>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>", ContentType.TEXT_XML);
        HttpPost request = SoapPostRequest("Service/GetEmpByPI", reqBodyEntity);

        try (CloseableHttpResponse httpResponse = client.execute(request)) {
            wsResponse.setHttpStatusCode(httpResponse.getStatusLine().getStatusCode());
            HttpEntity respEntity = httpResponse.getEntity();
            String xmlDocumentContent = EntityUtils.toString(respEntity);
            // parse XML doc
            GetEmployeeHandler handler = new GetEmployeeHandler();
            employees = employeeService.getEmployeesFromXml(xmlDocumentContent, handler);
            wsResponse.setEmployee(employees.get(0));
            wsResponse.setResultMessage(employees.get(0).getPrivateId());
            System.out.println("WS returned " + employees.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wsResponse;
    }

    private HttpPost SoapPostRequest(String soapActionHeader, StringEntity reqBodyEntity) {
        client = HttpClients.createDefault();
        HttpPost req = new HttpPost(WS_ENDPOINT);
        req.addHeader("Content-Type", "text/xml;charset=UTF-8");
        req.addHeader("SOAPAction", soapActionHeader);
        req.setEntity(reqBodyEntity);
        return req;
    }
}

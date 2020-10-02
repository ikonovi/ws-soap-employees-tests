package ik.soaptest.soaptest.xml.sax;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetEmployeeSalaryHandler extends DefaultHandler {
    private static final String GET_EMP_SALARY_RESULT = "GetEmpSalaryResult";
    private String elementValue;
    private String employeeSalaryResult;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case GET_EMP_SALARY_RESULT:
                employeeSalaryResult = elementValue;
                break;
        }
    }

    public String getEmployeeSalaryResult() {
        return employeeSalaryResult;
    }
}

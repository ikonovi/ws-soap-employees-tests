package ik.soaptest.soaptest.xml.sax;

import ik.soaptest.soaptest.models.BaseSalary;
import ik.soaptest.soaptest.models.Employee;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class GetEmployeeHandler extends DefaultHandler {
    private static final String NEW_DATA_SET = "NewDataSet";
    private static final String TABLE = "Table";
    private static final String PRIVATE_ID = "private_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String EXP = "exp";
    private static final String PROF_NAME = "prof_name";
    private static final String SALARY_AMOUNT = "salary_amount";

    private String elementValue;
    private Employee employee;

    public List<Employee> employees;

    @Override
    public void startDocument() throws SAXException {
        employees = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case NEW_DATA_SET:
                employees = new ArrayList<>();
                break;
            case TABLE:
                employee = new Employee();
                employee.setBaseSalary(new BaseSalary());
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case PRIVATE_ID:
                employee.setPrivateId(elementValue);
                break;
            case FIRST_NAME:
                employee.setFirstName(elementValue);
                break;
            case LAST_NAME:
                employee.setLastName(elementValue);
                break;
            case MIDDLE_NAME:
                employee.setMiddleName(elementValue);
                break;
            case EXP:
                employee.setExperienceInYears(Integer.valueOf(elementValue));
                break;
            case PROF_NAME:
                employee.getBaseSalary().setProfName(elementValue);
                break;
            case SALARY_AMOUNT:
                employee.getBaseSalary().setSalaryAmount(Float.valueOf(elementValue));
                break;
            case TABLE:
                employees.add(employee);
                break;
        }
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}

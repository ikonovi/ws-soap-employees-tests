package ik.soaptest.soaptest.xml.sax;

import ik.soaptest.soaptest.models.Employee;
import ik.soaptest.soaptest.xml.sax.AddEmployeeHandler;
import ik.soaptest.soaptest.xml.sax.GetEmployeeHandler;
import ik.soaptest.soaptest.xml.sax.GetEmployeeSalaryHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class ResponseBodyParser {

    public String getEmployeeSalaryFromXml(String xmlDocContent, GetEmployeeSalaryHandler handler) {
        parseXml(xmlDocContent, handler);
        return handler.getEmployeeSalaryResult();
    }

    public List<Employee> getEmployeesFromXml(String xmlDocContent, GetEmployeeHandler handler) {
        parseXml(xmlDocContent, handler);
        return handler.employees;
    }

    public String getAddEmployeeResponseResultFromXml(String xmlDocContent, AddEmployeeHandler handler) {
        parseXml(xmlDocContent, handler);
        return handler.getResponse();
    }

    private void parseXml(String xmlDocContent, DefaultHandler handler) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();
            saxParser.parse(new ByteArrayInputStream(xmlDocContent.getBytes()), handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

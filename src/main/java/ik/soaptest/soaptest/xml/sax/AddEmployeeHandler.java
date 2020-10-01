package ik.soaptest.soaptest.xml.sax;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AddEmployeeHandler extends DefaultHandler {
    private static final String ADD_EMPLOYEE_RESULT = "AddNewEmployeeResult";
    private String elementValue;
    private String response;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case ADD_EMPLOYEE_RESULT:
                response = elementValue;
                break;
        }
    }

    public String getResponse() {
        return response;
    }
}

package ik.soaptest.soaptest.ws;

import ik.soaptest.soaptest.models.Employee;

import java.util.StringJoiner;

public class WsGetEmpByPiResponse {
    private String resultMessage;
    private int httpStatusCode;
    private Employee employee;

    public WsGetEmpByPiResponse() {
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WsGetEmpByPiResponse.class.getSimpleName() + "[", "]")
                .add("resultMessage='" + resultMessage + "'")
                .add("httpStatusCode=" + httpStatusCode)
                .add("employee=" + employee)
                .toString();
    }
}

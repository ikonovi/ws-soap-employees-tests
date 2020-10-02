package ik.soaptest.soaptest.ws;

import java.util.StringJoiner;

public class WsGetEmployeeSalaryResponse {

    private int httpStatusCode;
    private String employeeSalaryResult;

    public WsGetEmployeeSalaryResponse() {
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getEmployeeSalaryResult() {
        return employeeSalaryResult;
    }

    public void setEmployeeSalaryResult(String employeeSalaryResult) {
        this.employeeSalaryResult = employeeSalaryResult;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WsGetEmployeeSalaryResponse.class.getSimpleName() + "[", "]")
                .add("httpStatusCode=" + httpStatusCode)
                .add("employeeSalaryResult=" + employeeSalaryResult)
                .toString();
    }
}

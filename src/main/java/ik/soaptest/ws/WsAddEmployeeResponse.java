package ik.soaptest.ws;

import java.util.StringJoiner;

public class WsAddEmployeeResponse {
    private String resultMessage;
    private int httpStatusCode;

    public WsAddEmployeeResponse() {
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

    @Override
    public String toString() {
        return new StringJoiner(", ", WsAddEmployeeResponse.class.getSimpleName() + "[", "]")
                .add("resultMessage='" + resultMessage + "'")
                .add("httpStatusCode=" + httpStatusCode)
                .toString();
    }
}

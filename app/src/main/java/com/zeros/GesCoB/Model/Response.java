package com.zeros.GesCoB.Model;

public class Response {
    private String result;
    private boolean error;
    private String message;

    public Response() {

    }

    public Response(String result, boolean error, String message) {
        this.result = result;
        this.error = error;
        this.message = message;
    }


    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

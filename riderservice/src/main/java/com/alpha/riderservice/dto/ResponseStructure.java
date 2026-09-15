package com.alpha.riderservice.dto;

public class ResponseStructure<T> {

    private int statuscode;

    private String message;

    private T data;

    public ResponseStructure() {
        super();
    }

    public ResponseStructure(
            int statuscode,
            String message,
            T data) {

        this.statuscode = statuscode;
        this.message = message;
        this.data = data;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
package com.study.tesma;

public class ApiResponse {
    private int status;
    private String message;

    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}

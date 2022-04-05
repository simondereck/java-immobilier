package com.utudo.hwwd.models.models;

public class ResponseMessage {
    private int status;
    private String message;

    public ResponseMessage(){
    }

    public ResponseMessage(int status,String message){
        this.status = status;
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

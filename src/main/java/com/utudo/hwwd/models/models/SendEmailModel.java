package com.utudo.hwwd.models.models;

import java.util.ArrayList;

public class SendEmailModel {


    private String description;


    private String subject;


    private String users;



    public String getDescription() {
        return description;
    }


    public String getUsers() {
        return users;
    }

    public String getSubject() {
        return subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }


    public void setUsers(String users) {
        this.users = users;
    }
}

package com.vaadin.spring.ims.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Intern extends AbstractEntity implements Cloneable {


    @NotNull
    @NotEmpty
    private String firstName = "";

    @NotNull
    @NotEmpty
    private String lastName = "";


    @NotNull
    @NotEmpty
    private String collegeName="";


    private String workInProgressDetails ="";


    @Email
    @NotNull
    @NotEmpty
    private String email = "";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }


    public String getWorkInProgressDetails() {
        return workInProgressDetails;
    }

    public void setWorkInProgressDetails(String workInProgressDetails) {
        this.workInProgressDetails = workInProgressDetails;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

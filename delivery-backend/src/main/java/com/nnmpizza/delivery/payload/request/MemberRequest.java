package com.nnmpizza.delivery.payload.request;

import com.nnmpizza.delivery.payload.beans.PojoRole;

import java.util.Set;

public class MemberRequest {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Set<PojoRole> roles;

    public Set<PojoRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<PojoRole> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

package com.nnmpizza.delivery.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nnmpizza.delivery.payload.request.MemberRequest;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phoneNumber;

    @ManyToMany
    //@JsonIgnore
    @JoinTable(name = "member_role",
        joinColumns = @JoinColumn(name="member_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Member(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Member(MemberRequest memberRequest){
        this.id = memberRequest.getId();
        this.username = memberRequest.getUsername();
        this.password = memberRequest.getPassword();
        this.email = memberRequest.getEmail();
        this.phoneNumber = memberRequest.getPhoneNumber();
        this.lastName = memberRequest.getLastName();
        this.firstName = memberRequest.getFirstName();
    }

    public Member() {

    }


    public Long getId() {
        return id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

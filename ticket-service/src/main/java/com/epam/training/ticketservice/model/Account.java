package com.epam.training.ticketservice.model;

import com.epam.training.ticketservice.model.enums.AccountPrivilege;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "privilege", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountPrivilege privilege;

    public Integer getId() {
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

    public AccountPrivilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(AccountPrivilege privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", username=" + username + ", password=" + password + ", privilege=" + privilege.toString() + "]";
    }
}

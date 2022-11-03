package com.epam.training.ticketservice.util.session.impl;

import com.epam.training.ticketservice.model.enums.AccountPrivilege;
import com.epam.training.ticketservice.util.session.Session;


public class SessionImpl implements Session {

    public SessionImpl() {
        loggedIn = false;
    }

    private String username;
    private AccountPrivilege privilege;
    private boolean loggedIn;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AccountPrivilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(AccountPrivilege privilege) {
        this.privilege = privilege;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean current) {
        loggedIn = current;
    }

    @Override
    public String toString() {
        return "Session [username=" + username + ", privilege=" + privilege.toString() + ", is_current=" + loggedIn + "]";
    }
}

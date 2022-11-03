package com.epam.training.ticketservice.util.session;

import com.epam.training.ticketservice.model.enums.AccountPrivilege;

public interface Session {
    String getUsername();

    void setUsername(String username);

    AccountPrivilege getPrivilege();

    void setPrivilege(AccountPrivilege privilege);

    boolean getLoggedIn();

    void setLoggedIn(boolean current);
}

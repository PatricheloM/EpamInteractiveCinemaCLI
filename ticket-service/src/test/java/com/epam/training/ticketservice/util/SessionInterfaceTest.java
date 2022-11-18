package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.model.enums.AccountPrivilege;
import com.epam.training.ticketservice.util.session.Session;
import com.epam.training.ticketservice.util.session.impl.SessionImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionInterfaceTest {

    private Session session;

    @BeforeEach
    public void beforeEach() {
        session = new SessionImpl();
    }

    @Test
    public void testShouldReturnEmptySessionWhenCreatingBean() {

        // Given

        // When

        // Then
        Assertions.assertNull(session.getUsername());
        Assertions.assertNull(session.getPrivilege());
        Assertions.assertFalse(session.getLoggedIn());
    }

    @Test
    public void testShouldReturnCorrectSessionWhenSettingUpGivenAUsernameAndPrivilege() {

        // Given
        Session s = new SessionImpl();
        s.setUsername("admin");
        s.setPrivilege(AccountPrivilege.ADMIN);
        s.setLoggedIn(true);

        // When
        session.setUsername("admin");
        session.setPrivilege(AccountPrivilege.ADMIN);
        session.setLoggedIn(true);

        // Then
        Assertions.assertEquals(s, session);
    }

    @Test
    public void testShouldReturnEmptySessionWhenLoggingOut() {

        // Given
        session.setUsername("admin");
        session.setPrivilege(AccountPrivilege.ADMIN);
        session.setLoggedIn(true);

        // When
        session.setUsername(null);
        session.setPrivilege(null);
        session.setLoggedIn(false);

        // Then
        Assertions.assertNull(session.getUsername());
        Assertions.assertNull(session.getPrivilege());
        Assertions.assertFalse(session.getLoggedIn());
    }

    @Test
    public void testShouldReturnCorrectStringWhenPrintingGivenASession() {

        // Given
        session.setUsername("user");
        session.setPrivilege(AccountPrivilege.USER);
        session.setLoggedIn(true);

        // When
        String result = session.toString();

        // Then
        Assertions.assertEquals("Session "
                + "[username=user, "
                + "privilege=USER, "
                + "is_current=true]", result);
    }
}

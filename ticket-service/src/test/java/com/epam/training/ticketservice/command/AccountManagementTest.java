package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.model.enums.AccountPrivilege;
import com.epam.training.ticketservice.repository.AccountRepository;
import com.epam.training.ticketservice.util.encryption.EncryptionFactory;
import com.epam.training.ticketservice.util.session.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountManagementTest {

    private Account account;

    @InjectMocks
    private AccountManagementCommands accountManagementCommands;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Session session;

    @BeforeEach
    public void beforeEach() {
        account = new Account();
    }

    @Test
    public void testShouldReturnNullWhenLoggingInAsAdminGivenAdminAccount() {

        // Given
        account.setUsername("admin");
        account.setPassword(EncryptionFactory.encrypt("admin"));
        account.setPrivilege(AccountPrivilege.ADMIN);

        // When
        Mockito.when(accountRepository.findByUsername("admin")).thenReturn(Optional.of(account));
        String result = accountManagementCommands.signInPrivileged("admin", "admin");

        // Then
        Assertions.assertNull(result);
    }

    @Test
    public void testShouldReturnErrorWhenLoggingInAsAdminGivenUserAccount() {

        // Given
        account.setUsername("user");
        account.setPassword(EncryptionFactory.encrypt("pass"));
        account.setPrivilege(AccountPrivilege.USER);

        // When
        Mockito.when(accountRepository.findByUsername("user")).thenReturn(Optional.of(account));
        String result = accountManagementCommands.signInPrivileged("user", "pass");

        // Then
        Assertions.assertEquals("Login failed due to incorrect credentials", result);
    }
}

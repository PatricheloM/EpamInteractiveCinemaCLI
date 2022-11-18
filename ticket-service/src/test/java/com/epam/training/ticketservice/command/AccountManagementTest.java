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

    @Test
    public void testShouldReturnNullWhenLoggingInAsUserGivenUserAccount() {

        // Given
        account.setUsername("user");
        account.setPassword(EncryptionFactory.encrypt("pass"));
        account.setPrivilege(AccountPrivilege.USER);

        // When
        Mockito.when(accountRepository.findByUsername("user")).thenReturn(Optional.of(account));
        String result = accountManagementCommands.signIn("user", "pass");

        // Then
        Assertions.assertNull(result);
    }

    @Test
    public void testShouldReturnErrorWhenLoggingInAsUserGivenUserAccountWithWrongPass() {

        // Given
        account.setUsername("user");
        account.setPassword(EncryptionFactory.encrypt("pass"));
        account.setPrivilege(AccountPrivilege.USER);

        // When
        Mockito.when(accountRepository.findByUsername("user")).thenReturn(Optional.of(account));
        String result = accountManagementCommands.signIn("user", "wrongPass");

        // Then
        Assertions.assertEquals("Login failed due to incorrect credentials", result);
    }

    @Test
    public void testShouldReturnNullWhenLoggingOut() {

         // Given

        // When
        String result = accountManagementCommands.signOut();

        // Then
        Assertions.assertNull(result);
        Assertions.assertNull(session.getUsername());
        Assertions.assertNull(session.getPrivilege());
        Assertions.assertFalse(session.getLoggedIn());
    }

    @Test
    public void testShouldReturnNullWhenSigningUpGivenNonExistingUsername() {

        // Given

        // When
        Mockito.when(accountRepository.findByUsername("movieLover")).thenReturn(Optional.empty());
        String result = accountManagementCommands.signUp("movieLover", "Avatar2");

        // Then
        Assertions.assertNull(result);
    }

    @Test
    public void testShouldReturnErrorWhenSigningUpGivenExistingUsername() {

        // Given
        account.setUsername("movieLover");
        account.setPassword(EncryptionFactory.encrypt("Avatar2"));
        account.setPrivilege(AccountPrivilege.USER);

        // When
        Mockito.when(accountRepository.findByUsername("movieLover")).thenReturn(Optional.of(account));
        String result = accountManagementCommands.signUp("movieLover", "StarWars8");

        // Then
        Assertions.assertEquals("Username already exists", result);
    }

    @Test
    public void testShouldReturnPrivilegedDescriptionWhenDescribingAccountGivenAdminAccount() {

        // Given

        // When
        Mockito.when(session.getUsername()).thenReturn("sysAdmin");
        Mockito.when(session.getPrivilege()).thenReturn(AccountPrivilege.ADMIN);
        Mockito.when(session.getLoggedIn()).thenReturn(true);
        String result = accountManagementCommands.describeAccount();

        // Then
        Assertions.assertEquals("Signed in with privileged account 'sysAdmin'", result);
    }

    @Test
    public void testShouldReturnDescriptionWhenDescribingAccountGivenUserAccount() {

        // Given

        // When
        Mockito.when(session.getUsername()).thenReturn("littleMermaid");
        Mockito.when(session.getPrivilege()).thenReturn(AccountPrivilege.USER);
        Mockito.when(session.getLoggedIn()).thenReturn(true);
        String result = accountManagementCommands.describeAccount();

        // Then
        Assertions.assertEquals("Signed in with account 'littleMermaid'", result);
    }

    @Test
    public void testShouldReturnErrorWhenDescribingAccountGivenNoSession() {

        // Given

        // When
        String result = accountManagementCommands.describeAccount();

        // Then
        Assertions.assertEquals("You are not signed in", result);
    }
}

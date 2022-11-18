package com.epam.training.ticketservice.util;

import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.model.enums.AccountPrivilege;
import com.epam.training.ticketservice.util.encryption.EncryptionFactory;
import com.epam.training.ticketservice.util.encryption.PasswordPrivilegeChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class EncryptionTest {

    private Account account = new Account();

    @BeforeEach
    public void beforeEach() {
        account = new Account();
    }

    @Test
    public void testShouldReturnCorrectSHA1HashWhenEncryptedGivenHash() {

        // Given
        String hash = "test";

        // When
        String result = EncryptionFactory.encrypt(hash);

        // Then
        Assertions.assertEquals("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", result);
    }

    @Test
    public void testShouldReturnTrueWhenCheckingPassForAdminGivenAdminAccountWithCorrectPass() {

        // Given
        account.setUsername("adminUser");
        account.setPassword(EncryptionFactory.encrypt("password"));
        account.setPrivilege(AccountPrivilege.ADMIN);

        // When
        boolean result = PasswordPrivilegeChecker.check(account, "password", AccountPrivilege.ADMIN);

        // Then
        Assertions.assertTrue(result);
    }

    @Test
    public void testShouldReturnFalseWhenCheckingPassForAdminGivenUserAccountWithCorrectPass() {

        // Given
        account.setUsername("user");
        account.setPassword(EncryptionFactory.encrypt("password"));
        account.setPrivilege(AccountPrivilege.USER);

        // When
        boolean result = PasswordPrivilegeChecker.check(account, "password", AccountPrivilege.ADMIN);

        // Then
        Assertions.assertFalse(result);
    }

    @Test
    public void testShouldReturnTrueWhenCheckingPassForUserGivenUserAccountWithCorrectPass() {

        // Given
        account.setUsername("user");
        account.setPassword(EncryptionFactory.encrypt("password"));
        account.setPrivilege(AccountPrivilege.USER);

        // When
        boolean result = PasswordPrivilegeChecker.check(account, "password", AccountPrivilege.USER);

        // Then
        Assertions.assertTrue(result);
    }

    @Test
    public void testShouldReturnFalseWhenCheckingPassForUserGivenUserAccountWithIncorrectPass() {

        // Given
        account.setUsername("adminUser");
        account.setPassword(EncryptionFactory.encrypt("password"));
        account.setPrivilege(AccountPrivilege.ADMIN);

        // When
        boolean result = PasswordPrivilegeChecker.check(account, "wrongPassword", AccountPrivilege.ADMIN);

        // Then
        Assertions.assertFalse(result);
    }

    @Test
    public void testShouldReturnFalseWhenCheckingPassForAdminGivenAdminAccountWithIncorrectPass() {

        // Given
        account.setUsername("user");
        account.setPassword(EncryptionFactory.encrypt("password"));
        account.setPrivilege(AccountPrivilege.USER);

        // When
        boolean result = PasswordPrivilegeChecker.check(account, "wrongPassword", AccountPrivilege.USER);

        // Then
        Assertions.assertFalse(result);
    }
}

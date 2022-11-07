package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.util.encryption.EncryptionFactory;
import com.epam.training.ticketservice.util.session.Session;
import com.epam.training.ticketservice.model.enums.AccountPrivilege;
import com.epam.training.ticketservice.repository.AccountRepository;
import com.epam.training.ticketservice.util.encryption.CheckPassAgainst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
public class AccountManagementCommands {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    Session session;

    @ShellMethod(value = "Signing up.", key = "sign up")
    public String signUp(String username, String password) {
        Optional<Account> existsCheck = accountRepository.findByUsername(username);
        if (existsCheck.isEmpty()) {
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(EncryptionFactory.encrypt(password));
            account.setPrivilege(AccountPrivilege.USER);
            accountRepository.save(account);
            return null;
        } else {
            return "Username already exists";
        }
    }

    @ShellMethod(value = "Admin sign in.", key = "sign in privileged")
    public String signInPrivileged(String username, String password) {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent() && CheckPassAgainst.check(account.get(), password, AccountPrivilege.ADMIN)) {
            session.setUsername(account.get().getUsername());
            session.setPrivilege(AccountPrivilege.ADMIN);
            session.setLoggedIn(true);
            return null;
        } else {
            return "Login failed due to incorrect credentials";
        }
    }

    @ShellMethod(value = "User sign in.", key = "sign in")
    public String signIn(String username, String password) {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent() && CheckPassAgainst.check(account.get(), password, AccountPrivilege.USER)) {
            session.setUsername(account.get().getUsername());
            session.setPrivilege(AccountPrivilege.USER);
            session.setLoggedIn(true);
            return null;
        } else {
            return "Login failed due to incorrect credentials";
        }
    }

    @ShellMethod(value = "Sign out of account.", key = "sign out")
    public String signOut() {
        session.setUsername(null);
        session.setPrivilege(null);
        session.setLoggedIn(false);
        return null;
    }

    @ShellMethod(value = "Describe your account.", key = "describe account")
    public String describeAccount() {

        if (session.getLoggedIn()) {
            return session.getPrivilege() == AccountPrivilege.USER
                    ? "Signed in with account '" + session.getUsername() + "'"
                    : "Signed in with privileged account '" + session.getUsername() + "'";
        } else {
            return "You are not signed in";
        }
    }
}

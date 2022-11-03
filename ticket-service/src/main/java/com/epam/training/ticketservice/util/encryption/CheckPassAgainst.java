package com.epam.training.ticketservice.util.encryption;

import com.epam.training.ticketservice.model.Account;
import com.epam.training.ticketservice.model.enums.AccountPrivilege;

public class CheckPassAgainst {

    private static final AccountPrivilege ADMIN = AccountPrivilege.ADMIN;

    public static boolean check(Account account, String hash, AccountPrivilege privilege) {
        boolean passCorrect = account.getPassword().equals(EncryptionFactory.encrypt(hash));
        return privilege == ADMIN ? passCorrect && account.getPrivilege() == ADMIN : passCorrect;
    }
}

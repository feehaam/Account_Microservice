package com.prep.account.exception;

import org.springframework.http.HttpStatus;

public class AccountSuspensionException  extends CustomException {
    public AccountSuspensionException () {
        super("AccountSuspensionException", "Account", "Can not perform operation due to account suspension.", HttpStatus.FORBIDDEN);
    }
}
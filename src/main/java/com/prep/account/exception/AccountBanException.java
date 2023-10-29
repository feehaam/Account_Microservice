package com.prep.account.exception;

import org.springframework.http.HttpStatus;

public class AccountBanException extends CustomException {
    public AccountBanException() {
        super("AccountBanException", "Account", "Can not perform operation due to account ban.", HttpStatus.FORBIDDEN);
    }
}
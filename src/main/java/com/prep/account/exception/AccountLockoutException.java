package com.prep.account.exception;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class AccountLockoutException extends CustomException {
    public AccountLockoutException() {
        super("AccountLockoutException", "Account", "Can not perform operation because account is locked.", HttpStatus.FORBIDDEN);
    }
}
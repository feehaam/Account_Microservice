package com.prep.account.exception;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class AccountNotFoundException extends CustomException {
    public AccountNotFoundException(String identifier) {
        super("AccountNotFoundException", "Account", "Account with " + identifier + " doesn't exist", HttpStatus.NOT_FOUND);
    }
}
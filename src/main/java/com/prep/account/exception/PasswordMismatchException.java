package com.prep.account.exception;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class PasswordMismatchException extends CustomException {
    public PasswordMismatchException() {
        super("PasswordMismatchException", "Password", "Given password doesn't match with existing password.", HttpStatus.BAD_REQUEST);
    }
}
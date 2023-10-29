package com.prep.account.exception;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class AccessDeniedException extends CustomException {
    public AccessDeniedException(String message) {
        super("AccessDeniedException", "Access", "User has no access for - " + message, HttpStatus.FORBIDDEN);
    }
}
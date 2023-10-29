package com.prep.account.exception;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class DeactivationException extends CustomException {
    public DeactivationException() {
        super("DeactivationException", "Account", "Can not perform operation because account is deactivated.", HttpStatus.FORBIDDEN);
    }
}
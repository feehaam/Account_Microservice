package com.prep.account.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ActionNotAllowedException extends CustomException {
    public ActionNotAllowedException(String message) {
        super("ActionNotAllowedException", "Operation", message, HttpStatus.BAD_REQUEST);
    }
}
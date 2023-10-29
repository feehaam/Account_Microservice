package com.prep.account.exception;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class OTPValidationException extends CustomException {
    public OTPValidationException(Integer otp) {
        super("OTPValidationException", "OTP", "Given OTP " + otp + " is invalid.", HttpStatus.BAD_REQUEST);
    }
}
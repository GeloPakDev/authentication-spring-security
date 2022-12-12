package com.epam.esm.validator;

import com.epam.esm.User;
import com.epam.esm.exception.ExceptionResult;
import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@UtilityClass
public class UserValidator {
    private final int MAX_LENGTH_NAME = 100;
    private final int MIN_LENGTH_NAME = 1;
    private final int MIN_LENGTH_PASSWORD = 8;
    private final String EMAIL_PATTERN = "^.+@.+\\..+$";

    public static final String BAD_USER_NAME = "Wrong username";
    public static final String BAD_USER_EMAIL = "Wrong user email";
    public static final String BAD_USER_PASSWORD = "Wrong user password";

    public void validate(User user, ExceptionResult er) {
        validateEmail(user.getEmail(), er);
        validatePassword(user.getPassword(), er);
        validateName(user.getName(), er);
    }

    public void validateEmail(String email, ExceptionResult er) {
        if (email == null) {
            er.addException(BAD_USER_EMAIL, email);
        } else {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                er.addException(BAD_USER_EMAIL, email);
            }
        }
    }

    public void validatePassword(String password, ExceptionResult er) {
        if (password == null || password.length() < MIN_LENGTH_PASSWORD) {
            er.addException(BAD_USER_PASSWORD, password);
        }
    }

    private void validateName(String name, ExceptionResult er) {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            er.addException(BAD_USER_NAME, name);
        }
    }
}

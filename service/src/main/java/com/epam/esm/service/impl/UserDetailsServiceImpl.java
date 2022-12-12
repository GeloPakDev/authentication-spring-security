package com.epam.esm.service.impl;

import com.epam.esm.User;
import com.epam.esm.UserDao;
import com.epam.esm.exception.ExceptionIncorrectParameterMessageCodes;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDao;

    private static final String ROLE_PREFIX = "ROLE_";


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ExceptionResult exceptionResult = new ExceptionResult();
        UserValidator.validateEmail(email, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Optional<User> user = userDao.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchEntityException(ExceptionIncorrectParameterMessageCodes.USER_NOT_FOUND);
        }

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX
                + user.get().getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), authorities);
    }
}

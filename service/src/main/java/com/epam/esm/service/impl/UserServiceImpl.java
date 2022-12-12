package com.epam.esm.service.impl;

import com.epam.esm.Role;
import com.epam.esm.User;
import com.epam.esm.UserDao;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.ExceptionIncorrectParameterMessageCodes;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncode;



    @Override
    public User create(User user) {
        ExceptionResult exceptionResult = new ExceptionResult();
        UserValidator.validate(user, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        user.setPassword(bCryptPasswordEncode.encode(user.getPassword()));

        String userEmail = user.getEmail();
        boolean isUserExist = userDao.findByEmail(userEmail).isPresent();
        if (isUserExist) {
            throw new DuplicateEntityException(ExceptionIncorrectParameterMessageCodes.USER_EXIST);
        }
        user.setRole(Role.USER);
        return userDao.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public Page<User> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userDao.findAll(pageable);
    }
}

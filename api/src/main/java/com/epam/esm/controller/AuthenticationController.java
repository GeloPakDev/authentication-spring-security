package com.epam.esm.controller;

import com.epam.esm.User;
import com.epam.esm.dto.UserCredentialDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.jwt.JWTProvider;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.epam.esm.util.EndpointName.*;


@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final DtoConverter<User, UserDto> userDtoConverter;
    private final JWTProvider jwtProvider;
    private final HateoasAdder<UserDto> hateoasAdder;
    private final AuthenticationManager authenticationManager;


    @PostMapping(REGISTRATION)
    @ResponseStatus(HttpStatus.OK)
    public UserDto registerUser(@RequestBody UserDto user) {
        User addedUser = userService.create(userDtoConverter.convertToEntity(user));

        UserDto userDto = userDtoConverter.convertToDto(addedUser);
        hateoasAdder.addLinks(userDto);
        return userDto;
    }


    @PostMapping(AUTHENTICATION)
    @ResponseStatus(HttpStatus.OK)
    public UserCredentialDto authorizeUser(@RequestBody UserCredentialDto userCredentialDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentialDto.getEmail(), userCredentialDto.getPassword()));
        String token = jwtProvider.generateToken(userCredentialDto.getEmail());
        userCredentialDto.setToken(token);
        return userCredentialDto;
    }
}
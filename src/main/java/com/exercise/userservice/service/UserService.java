package com.exercise.userservice.service;

import com.exercise.userservice.dto.*;
import org.springframework.stereotype.Service;

public interface UserService {
    GetUsersResponseDto getUsers(GetUsersRequestDto requestDto);

    GetUserByIdResponseDto getUserById(Integer id);

    CreateUserResponseDto createUser(CreateUserRequestDto requestDto);
}

package com.exercise.userservice.service.impl;

import com.exercise.userservice.dto.*;
import com.exercise.userservice.entity.User;
import com.exercise.userservice.repository.UserRepository;
import com.exercise.userservice.service.UserService;
import org.apache.catalina.mbeans.MBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public GetUsersResponseDto getUsers(GetUsersRequestDto getUsersRequestDto) {
        PageRequest pageRequest = PageRequest.of(getUsersRequestDto.getPageNum(), getUsersRequestDto.getPageSize(), Sort.by("createdAt").descending());

        List<User> users = userRepository.findAll(pageRequest).getContent();
        List<UserDto> userDtoList = users.stream().map(this::convertUserToUserDto).toList();

        GetUsersResponseDto getUsersResponseDto = new GetUsersResponseDto();
        getUsersResponseDto.setResult(true);
        getUsersResponseDto.setUsers(userDtoList);

        return getUsersResponseDto;
    }

    @Override
    public GetUserByIdResponseDto getUserById(Integer id) {
        User user = userRepository.findById(id).orElse(null);

        GetUserByIdResponseDto getUserByIdResponseDto = new GetUserByIdResponseDto();

        if(user == null) {
            getUserByIdResponseDto.setResult(false);
            getUserByIdResponseDto.setUser(null);
        } else {
            getUserByIdResponseDto.setResult(true);
            getUserByIdResponseDto.setUser(convertUserToUserDto(user));
        }
        return getUserByIdResponseDto;
    }

    @Override
    public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        User user = new User();
        user.setName(createUserRequestDto.getName());

        final Long timestampInMicroSeconds = nowInEpochMicroSeconds();
        user.setCreatedAt(timestampInMicroSeconds);
        user.setUpdatedAt(timestampInMicroSeconds);

        userRepository.save(user);

        CreateUserResponseDto createUserResponseDto = new CreateUserResponseDto();
        createUserResponseDto.setResult(true);
        createUserResponseDto.setUser(convertUserToUserDto(user));
        return createUserResponseDto;

    }

    private UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);

        return userDto;
    }

    private long nowInEpochMicroSeconds() { return ChronoUnit.MICROS.between(Instant.EPOCH, Instant.now()); }

}

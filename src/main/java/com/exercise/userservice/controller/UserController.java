package com.exercise.userservice.controller;

import com.exercise.userservice.dto.*;
import com.exercise.userservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public GetUsersResponseDto getUsers(
            @RequestParam(name = "pageNum", defaultValue = "1") @Min(1) Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") @Min(1) Integer pageSize
    ) {
        GetUsersRequestDto getUsersRequestDto = new GetUsersRequestDto();
        getUsersRequestDto.setPageNum(pageNum - 1);
        getUsersRequestDto.setPageSize(pageSize);

        return userService.getUsers(getUsersRequestDto);
    }

    @GetMapping("/{id}")
    public GetUserByIdResponseDto getUserById(@PathVariable @Min(1) Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public CreateUserResponseDto createUser(@Valid CreateUserRequestDto createUserRequestDto) {
        return userService.createUser(createUserRequestDto);
    }
}

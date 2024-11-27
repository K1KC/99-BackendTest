package com.exercise.publicapi.dto;

import com.exercise.userservice.dto.UserDto;

public class CreatePublicUserResponseDto {
    private UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}

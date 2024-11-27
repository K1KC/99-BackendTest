package com.exercise.publicapi.dto;

import com.exercise.userservice.dto.UserDto;

import java.util.List;

public class GetPublicUsersResponseDto {
    private List<UserDto> users;

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}

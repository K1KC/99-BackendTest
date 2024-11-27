package com.exercise.userservice.dto;

public class GetAllUsersRequestDto {
    private Integer pageNum;
    private Integer pageSize;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}

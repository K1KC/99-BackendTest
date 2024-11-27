package com.exercise.publicapi.controller;

import com.exercise.listingservice.dto.CreateListingRequestDto;
import com.exercise.listingservice.dto.GetListingsRequestDto;
import com.exercise.publicapi.dto.*;
import com.exercise.publicapi.service.PublicApiService;
import com.exercise.userservice.dto.CreateUserRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public-api")
public class PublicApiController {

    @Autowired
    private PublicApiService publicApiService;

    @GetMapping("/listings")
    public GetPublicListingsResponseDto getListings(
            @RequestParam(name = "pageNum", defaultValue = "1") @Min(1) Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(name = "userId", required = false) @Min(1) Integer userId)
    {
        GetPublicListingsRequestDto getPublicListingsRequestDto = new GetPublicListingsRequestDto();
        getPublicListingsRequestDto.setPageNum(pageNum - 1);
        getPublicListingsRequestDto.setPageSize(pageSize);
        getPublicListingsRequestDto.setUserId(userId);

        return publicApiService.getPublicListings(getPublicListingsRequestDto);
    }

    @PostMapping("/users")
    public CreatePublicUserResponseDto createUser(@Valid @RequestBody CreatePublicUserRequestDto createPublicUserRequestDto) {
        return publicApiService.createPublicUser(createPublicUserRequestDto);
    }

    @PostMapping("/listings")
    public CreatePublicListingResponseDto createListing(@Valid @RequestBody CreatePublicListingRequestDto createPublicListingRequestDto) {
        return publicApiService.createPublicListing(createPublicListingRequestDto);
    }
}

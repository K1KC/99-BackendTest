package com.exercise.publicapi.service.impl;

import com.exercise.listingservice.dto.CreateListingRequestDto;
import com.exercise.listingservice.dto.CreateListingResponseDto;
import com.exercise.listingservice.dto.GetListingsRequestDto;
import com.exercise.listingservice.entity.Listing;
import com.exercise.listingservice.repository.ListingRepository;
import com.exercise.listingservice.service.ListingService;
import com.exercise.publicapi.dto.*;
import com.exercise.publicapi.service.PublicApiService;
import com.exercise.userservice.dto.CreateUserRequestDto;
import com.exercise.userservice.dto.CreateUserResponseDto;
import com.exercise.userservice.dto.UserDto;
import com.exercise.userservice.entity.User;
import com.exercise.userservice.repository.UserRepository;
import com.exercise.userservice.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicApiServiceImpl implements PublicApiService {

    @Autowired
    private UserService userService;

    @Autowired
    private ListingService listingService;
    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public GetPublicListingsResponseDto getPublicListings(GetPublicListingsRequestDto getPublicListingsRequestDto) {
        if (getPublicListingsRequestDto.getPageNum() < 0 || getPublicListingsRequestDto.getPageSize() <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }
        // Create PageRequest
        PageRequest pageRequest = PageRequest.of(
                getPublicListingsRequestDto.getPageNum(),
                getPublicListingsRequestDto.getPageSize(),
                Sort.by("createdAt").descending()
        );

        // Fetch listings
        List<Listing> result = Optional.ofNullable(getPublicListingsRequestDto.getUserId())
                .map(id -> listingRepository.findByUserId(id, pageRequest))
                .orElseGet(() -> listingRepository.findAll(pageRequest).getContent());

        // Map Listing to ListingWithUsersDto
        List<ListingWithUsersDto> listingWithUsersDtoList = result.stream()
                .map(listing -> {
                    ListingWithUsersDto listingWithUsersDto = new ListingWithUsersDto();
                    listingWithUsersDto.setId(listing.getId());
                    listingWithUsersDto.setListingType(listing.getListingType());
                    listingWithUsersDto.setPrice(listing.getPrice());
                    listingWithUsersDto.setCreatedAt(listing.getCreatedAt());
                    listingWithUsersDto.setUpdatedAt(listing.getUpdatedAt());

                    // Fetch user details
                    User user = userRepository.findById(listing.getUserId()).orElse(null);

                    if (user != null) {
                        UserDto userDto = new UserDto();
                        BeanUtils.copyProperties(user, userDto);
                        listingWithUsersDto.setUser(userDto);
                    }

                    return listingWithUsersDto;
                })
                .collect(Collectors.toList());

        // Build response DTO
        GetPublicListingsResponseDto getPublicListingsResponseDto = new GetPublicListingsResponseDto();
        getPublicListingsResponseDto.setListings(listingWithUsersDtoList);

        return getPublicListingsResponseDto;
    }



    @Override
    public CreatePublicListingResponseDto createPublicListing(CreatePublicListingRequestDto createPublicListingRequestDto) {
        // Map Public API Request DTO to Service Layer Request DTO
        CreateListingRequestDto createListingRequestDto = new CreateListingRequestDto();
        BeanUtils.copyProperties(createPublicListingRequestDto, createListingRequestDto);

        // Call the Service Layer
        CreateListingResponseDto createListingResponseDto = listingService.createListing(createListingRequestDto);

        // Fetch User Information based on userId
        User user = userRepository.findById(createListingRequestDto.getUserId()).orElse(null);
        UserDto userDto = new UserDto();
        if(user != null) {
            BeanUtils.copyProperties(user, userDto);
        }
        // Prepare the Public API Response
        CreatePublicListingResponseDto createPublicListingResponseDto = new CreatePublicListingResponseDto();

        // Map Listing from Service Response to Public API Response DTO
        ListingWithUsersDto listingWithUsersDto = new ListingWithUsersDto();
        BeanUtils.copyProperties(createListingResponseDto.getListing(), listingWithUsersDto);

        // Set user information in the response
        listingWithUsersDto.setUser(userDto);

        // Set Listing in Response
        createPublicListingResponseDto.setListing(listingWithUsersDto);

        return createPublicListingResponseDto;
    }



    @Override
    public CreatePublicUserResponseDto createPublicUser(CreatePublicUserRequestDto createPublicUserRequestDto) {
        // Create User Request DTO for Public API
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto();
        BeanUtils.copyProperties(createPublicUserRequestDto, createUserRequestDto);

        // Create user from User Service
        CreateUserResponseDto createUserResponseDto = userService.createUser(createUserRequestDto);

        CreatePublicUserResponseDto createPublicUserResponseDto = new CreatePublicUserResponseDto();

        // Manually map each field to Public User Response DTO
        if(createUserResponseDto != null && createUserResponseDto.getUser() != null) {
            UserDto userDto = new UserDto();
            userDto.setId(createUserResponseDto.getUser().getId());
            userDto.setName(createUserResponseDto.getUser().getName());
            userDto.setCreatedAt(createUserResponseDto.getUser().getCreatedAt());
            userDto.setUpdatedAt(createUserResponseDto.getUser().getUpdatedAt());
            createPublicUserResponseDto.setUser(userDto);
        } else {
            createPublicUserResponseDto.setUser(null);
        }

        return createPublicUserResponseDto;
    }
}

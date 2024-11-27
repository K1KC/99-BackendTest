package com.exercise.publicapi.service;

import com.exercise.publicapi.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface PublicApiService {
    GetPublicListingsResponseDto getPublicListings(GetPublicListingsRequestDto getPublicListingsRequestDto);
    CreatePublicListingResponseDto createPublicListing(CreatePublicListingRequestDto createPublicListingRequestDto);
    CreatePublicUserResponseDto createPublicUser(CreatePublicUserRequestDto createPublicUserRequestDto);
}

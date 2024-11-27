package com.exercise.publicapi.dto;

import java.util.List;

public class GetPublicListingsResponseDto {
    private List<ListingWithUsersDto> listings;

    public List<ListingWithUsersDto> getListings() {
        return listings;
    }

    public void setListings(List<ListingWithUsersDto> listings) {
        this.listings = listings;
    }
}

package com.exercise.publicapi.dto;

import com.exercise.listingservice.dto.ListingDto;

import java.util.List;

public class GetListingsResponseDto {
    private boolean result;
    private List<ListingWithUsersDto> listings;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<ListingWithUsersDto> getListings() {
        return listings;
    }

    public void setListings(List<ListingWithUsersDto> listings) {
        this.listings = listings;
    }
}

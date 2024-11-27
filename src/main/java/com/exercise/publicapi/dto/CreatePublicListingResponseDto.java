package com.exercise.publicapi.dto;

import com.exercise.listingservice.dto.ListingDto;

public class CreateListingResponseDto {

    private boolean result;
    private ListingWithUsersDto listing;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ListingWithUsersDto getListing() {
        return listing;
    }

    public void setListing(ListingWithUsersDto listing) {
        this.listing = listing;
    }
}

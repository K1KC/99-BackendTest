package com.exercise.publicapi.dto;

public class CreatePublicListingResponseDto {

    private ListingWithUsersDto listing;

    public ListingWithUsersDto getListing() {
        return listing;
    }

    public void setListing(ListingWithUsersDto listing) {
        this.listing = listing;
    }
}

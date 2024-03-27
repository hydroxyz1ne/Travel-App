package com.example.travelapp;

public class Place {
    public String id,name, description, location, reviews,price, imageId,favorite;

    public Place(){
    }

    public Place(String id, String name, String description, String location, String reviews, String price, String imageId,String favorite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.reviews = reviews;
        this.price = price;
        this.imageId = imageId;
        this.favorite = favorite;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getReviews() {
        return reviews;
    }

    public String getPrice() {
        return price;
    }

    public String getImageId() {
        return imageId;
    }


}

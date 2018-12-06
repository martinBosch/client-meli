package com.martinb.meli.network.object_request;

public class Ubication {

    private String destination_address;
    private Double destination_latitude;
    private Double destination_longitude;

    public Ubication(String destination_adress, Double destination_latitude, Double destination_longitude) {
        this.destination_address = destination_adress;
        this.destination_latitude = destination_latitude;
        this.destination_longitude = destination_longitude;
    }
}

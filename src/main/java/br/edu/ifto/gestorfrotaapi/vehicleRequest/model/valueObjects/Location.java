package br.edu.ifto.gestorfrotaapi.vehicleRequest.model.valueObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Location {

    @Column(name = "dest_city", nullable = false)
    private String city;

    @Column(name = "dest_state", nullable = false)
    private String state;

    @Column(name = "dest_latitude", nullable = true)
    private Double latitude;

    @Column(name = "dest_longitude", nullable = true)
    private Double longitude;

    protected Location() {
    }

    public Location(String city, String state, Double latitude, Double longitude) {
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}

package com.example.training_intern.modules.Locations.service;

import com.example.training_intern.modules.Locations.model.Location;
import utils.Result;

import java.util.List;

public interface LocationService {
    Result<List<Location>> findAll(Long limit, Long offset, Boolean isFull);
    Location findLocationById(Long id);
    Location createLocation(Location location);
    Location updateLocationById(Long id, Location location);
    void deleteLocationById(Long id);
}

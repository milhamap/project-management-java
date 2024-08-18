package com.example.training_intern.modules.Locations.controller;

import com.example.training_intern.modules.Locations.model.Location;
import com.example.training_intern.modules.Locations.service.LocationService;
import helpers.Response;
import helpers.ResponsePagination;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.Result;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/locations")
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponsePagination<List<Location>>> findAllLocation(
            @RequestParam(defaultValue = "0") Long start,
            @RequestParam(defaultValue = "10") Long quantity,
            @RequestParam(defaultValue = "false") Boolean isFull) {

        Result<List<Location>> result = locationService.findAll(quantity, start, isFull);

        ResponsePagination<List<Location>> response = new ResponsePagination<>(
                true, result.getData(), "List of locations", 200, result.getMeta()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Location>> findLocationById(@PathVariable("id") Long id) {
        Location location = locationService.findLocationById(id);
        Response<Location> response = new Response<>(
                true, location, "Location found", 200
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response<Location>> saveLocation(@RequestBody Location location) {
        Location newLocation = locationService.createLocation(location);
        Response<Location> response = new Response<>(
                true, newLocation, "Location created successfully", 201
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Location>> updateLocation(
            @PathVariable("id") Long id, @RequestBody Location location) {

        Location updatedLocation = locationService.updateLocationById(id, location);
        Response<Location> response = new Response<>(
                true, updatedLocation, "Location updated successfully", 200
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> deleteLocation(@PathVariable("id") Long id) {
        locationService.deleteLocationById(id);
        Map<String, String> responseData = Map.of("message", "Location deleted successfully");

        Response<Object> response = new Response<>(
                true, responseData, "Location deleted", 200
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
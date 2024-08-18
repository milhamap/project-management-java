package com.example.training_intern.modules.Locations.service;

import com.example.training_intern.modules.Locations.model.Location;
import com.example.training_intern.modules.Locations.repository.LocationRepository;
import com.example.training_intern.modules.Project.model.Project;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import utils.Result;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {
    private LocationRepository locationRepository;
    @Override
    public Result<List<Location>> findAll(Long limit, Long start, Boolean isFull) {
        Long countData = locationRepository.count();

        // Handle the case where countData might be zero to avoid division by zero
        if (countData == 0) {
            return new Result<>(List.of());
        }

        if (isFull) {
            List<Location> resultQuery = locationRepository.findAll();
            return new Result<>(resultQuery);
        }
        List<Location> resultQuery = locationRepository.findAll().stream()
                .skip(start)
                .limit(limit)
                .toList();

        int totalPage = (int) Math.ceil((double) countData / limit);

        Map<String, Object> meta = new HashMap<>();
        meta.put("page", (start/10) + 1);
        meta.put("quantity", resultQuery.size());
        meta.put("totalPage", totalPage);
        meta.put("totalData", countData);

        return new Result<>(resultQuery, meta);
    }

    @Override
    public Location findLocationById(Long id) {
        return locationRepository.findById(id).get();
    }

    @Override
    public Location createLocation(Location location) {
        location.setCreatedAt(new Date());
        return locationRepository.save(location);
    }

    @Override
    public Location updateLocationById(Long id, Location location) {
        Location existingLocation = locationRepository.findById(id).get();
        existingLocation.setName(location.getName());
        existingLocation.setCountry(location.getCountry());
        existingLocation.setProvince(location.getProvince());
        existingLocation.setCity(location.getCity());
        existingLocation.setUpdatedAt(new Date());
        return locationRepository.save(existingLocation);
    }

    @Override
    public void deleteLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));

        for (Project project : location.getProjects()) {
            project.getLocations().remove(location);
        }

        locationRepository.deleteById(id);
    }
}

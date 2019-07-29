package com.example.cartest.repo;

import com.example.cartest.domain.Car;

import java.util.List;
import java.util.Map;

/**
 * Base interface for custom car filtering
 */
public interface CarCustomRepository {
    List<Car> findCars(List<Map<String, String>> filterRules);
}

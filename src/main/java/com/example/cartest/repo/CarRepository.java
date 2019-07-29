package com.example.cartest.repo;

import com.example.cartest.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
 * CRUD refers Create, Read, Update, Delete
 */
public interface CarRepository extends JpaRepository<Car, Integer>, CarCustomRepository {
}

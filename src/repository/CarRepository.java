package repository;

import model.Car;
import model.enums.CarStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository for managing {@link Car} entities.
 * Provides additional methods specific to car operations.
 */
public class CarRepository extends InMemoryRepository<Car> {

    /**
     * Finds all available cars in the repository.
     *
     * @return a list of cars with the status {@link CarStatus#AVAILABLE}.
     */
    public List<Car> findAvailableCars() {
        return readAll().stream()
                .filter(car -> car.getStatus() == CarStatus.AVAILABLE)
                .collect(Collectors.toList());
    }
}

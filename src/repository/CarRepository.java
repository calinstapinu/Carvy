package repository;

import model.Car;
import model.enums.CarStatus;
import repository.parsers.EntityParser;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository for managing {@link Car} entities.
 * Provides additional methods specific to car operations.
 */
public class CarRepository extends FileRepository<Car> {
    public CarRepository(File file, EntityParser<Car> parser) {
        super(file, parser);
    }
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

    public List<Car> findSoldCars() {
        return readAll().stream()
                .filter(car -> car.getStatus() == CarStatus.SOLD)
                .collect(Collectors.toList());
    }

    public List<Car> findLeasedCars() {
        return readAll().stream()
                .filter(car -> car.getStatus() == CarStatus.LEASED)
                .collect(Collectors.toList());
    }
}
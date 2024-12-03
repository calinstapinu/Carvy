package service;

import model.Car;
import model.enums.CarStatus;
import repository.CarRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing {@link Car} entities.
 * This class provides business logic for operations such as adding, retrieving,
 * and updating cars, as well as marking cars as sold or leased.
 */
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    public void addCar(Car car) {
        carRepository.create(car);
    }


    public List<Car> getAllCars() {
        return carRepository.readAll();
    }


    public List<Car> getAvailableCars() {
        return carRepository.findAvailableCars();
    }

    public List<Car> getSoldCars() {
        return carRepository.findSoldCars();
    }

    public List<Car> getLeasedCars() {
        return carRepository.findLeasedCars();
    }

    /**
     * Marks a car as sold by updating its status to {@link CarStatus#SOLD}.
     *
     * @param carId the ID of the car to mark as sold
     * @throws IllegalArgumentException if the car is not available or does not exist
     */

    public void markCarAsSold(long carId) {
        Car car = carRepository.read(carId);
        if (car != null && car.getStatus() == CarStatus.AVAILABLE) {
            car.setStatus(CarStatus.SOLD);
            carRepository.update(car);
        } else {
            throw new IllegalArgumentException("Car not available for selling.");
        }
    }

    /**
     * Marks a car as leased by updating its status to {@link CarStatus#LEASED}.
     *
     * @param carId the ID of the car to mark as leased
     * @throws IllegalArgumentException if the car is not available or does not exist
     */
    public void markCarAsLeased(long carId) {
        Car car = carRepository.read(carId);
        if (car != null && car.getStatus() == CarStatus.AVAILABLE) {
            car.setStatus(CarStatus.LEASED);
            carRepository.update(car);
        } else {
            throw new IllegalArgumentException("Car not available for leasing..");
        }
    }

    /**
     * Finds a car by its unique ID.
     *
     * @param carId the ID of the car to find
     * @return the {@link Car} entity with the specified ID
     * @throws IllegalArgumentException if the car does not exist
     */
    public Car findCarById(long carId) {
        Car car = carRepository.read(carId);
        if (car == null) {
            throw new IllegalArgumentException("The Car with ID " + carId + " does not exist.");
        }
        return car;
    }

    public List<Car> findCarsByName(String name) {
        return carRepository.readAll().stream()
                .filter(car -> car.getModel().toLowerCase().contains(name.toLowerCase()) ||
                        car.getBrand().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Car> getCarsNewerThan(int year) {
        return carRepository.readAll().stream()
                .filter(car -> car.getYear() > year)
                .collect(Collectors.toList());
    }

    public List<Car> getCarsWithinBudget(float maxBudget) {
        return carRepository.readAll().stream() // Read all cars
                .filter(car -> car.getPrice() <= maxBudget) // Filter cars within the budget
                .collect(Collectors.toList());
    }


}
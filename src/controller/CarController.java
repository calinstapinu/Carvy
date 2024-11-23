package controller;

import model.Car;
import model.enums.CarStatus;
import service.CarService;

import java.util.List;

/**
 * Controller class for managing {@link Car} entities.
 * Provides methods for adding cars, listing all or available cars,
 * and marking cars as sold or leased.
 */
public class CarController {
    private final CarService carService;

    /**
     * Constructs a new {@code CarController} with the specified car service.
     *
     * @param carService the service responsible for managing car operations
     */
    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Adds a new car to the system with the specified details.
     * The car is initially marked as {@link CarStatus#AVAILABLE}.
     * */
    public void addCar(long carId, String brand, String model, int year, float price, int mileage) {
        Car car = new Car(carId, brand, model, year, price, mileage, CarStatus.AVAILABLE);
        carService.addCar(car);
        System.out.println("The Car was added successfully.");
    }

    public void listAllCars() {
        List<Car> cars = carService.getAllCars();
        System.out.println("The list of all cars:");
        cars.forEach(System.out::println);
    }

    public void listAvailableCars() {
        List<Car> cars = carService.getAvailableCars();
        System.out.println("The list of available cars:");
        cars.forEach(System.out::println);
    }


    public void markCarAsSold(long carId) {
        try {
            carService.markCarAsSold(carId);
            System.out.println("The Car with ID " + carId + " was marked as sold.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void markCarAsLeased(long carId) {
        try {
            carService.markCarAsLeased(carId);
            System.out.println("The Car with ID " + carId + " was marked as leased.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Car findCarById(long carId) {
        return carService.findCarById(carId);
    }

    public List<Car> findCarsByName(String name) {
        return carService.findCarsByName(name); // Implement in `CarService`
    }

}

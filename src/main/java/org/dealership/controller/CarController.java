package org.dealership.controller;

import org.dealership.model.Car;
import org.dealership.model.enums.CarStatus;
import org.dealership.service.CarService;

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

    /**
     * Lists all cars in the system.
     */
    public void listAllCars() {
        List<Car> cars = carService.getAllCars();
        System.out.println("The list of all cars:");
        cars.forEach(System.out::println);
    }

    /**
     * Lists all cars currently available for sale or lease.
     */
    public void listAvailableCars() {
        List<Car> cars = carService.getAvailableCars();
        System.out.println("The list of available cars:");
        cars.forEach(System.out::println);
    }


    /**
     * Lists all cars that have been sold.
     */
    public void listSoldCars() {
        List<Car> cars = carService.getSoldCars();
        System.out.println("The list of sold cars:");
        cars.forEach(System.out::println);
    }

    public void listLeasedCars() {
        List<Car> cars = carService.getLeasedCars();
        System.out.println("The list of leased cars:");
        cars.forEach(System.out::println);
    }

    /**
     * Marks a car with the specified ID as sold.
     *
     * @param carId the ID of the car to mark as sold
     */
    public void markCarAsSold(long carId) {
        try {
            carService.markCarAsSold(carId);
            System.out.println("The Car with ID " + carId + " was marked as sold.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Marks a car with the specified ID as leased.
     *
     * @param carId the ID of the car to mark as leased
     */
    public void markCarAsLeased(long carId) {
        try {
            carService.markCarAsLeased(carId);
            System.out.println("The Car with ID " + carId + " was marked as leased.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Finds and returns a car by its unique ID.
     *
     * @param carId the ID of the car to find
     * @return the car with the specified ID, or {@code null} if not found
     */
    public Car findCarById(long carId) {
        return carService.findCarById(carId);
    }

    /**
     * Finds and returns a list of cars matching the specified name (brand or model).
     *
     * @param name the name to search for (brand or model)
     * @return a list of cars matching the specified name
     */
    public List<Car> findCarsByName(String name) {
        return carService.findCarsByName(name);
    }

    /**
     * Lists all cars newer than the specified year.
     *
     * @param year the year to filter cars
     */
    public void listCarsNewerThan(int year) {
        List<Car> cars = carService.getCarsNewerThan(year);
        if (cars.isEmpty()) {
            System.out.println("No cars found newer than year " + year);
        } else {
            System.out.println("Cars newer than " + year + ":");
            cars.forEach(System.out::println);
        }
    }

    /**
     * Lists all cars within the specified budget.
     *
     * @param maxBudget the maximum budget for filtering cars
     */
    public void listCarsWithinBudget(float maxBudget) {
        List<Car> cars = carService.getCarsWithinBudget(maxBudget);
        System.out.println("Cars within your budget:");
        cars.forEach(System.out::println);
    }

    /**
     * Lists all cars sorted by their year of manufacture in ascending order.
     */
    public void listCarsSortedByYear() {
        List<Car> sortedCars = carService.getCarsSortedByYearAscending();
        System.out.println("Cars sorted by Year (Ascending):");
        sortedCars.forEach(System.out::println);
    }


    /**
     * Lists all cars sorted by their price in ascending order.
     */
    public void listCarsSortedByPrice() {
        List<Car> sortedCars = carService.getCarsSortedByPriceAscending();
        System.out.println("Cars sorted by Price (Ascending):");
        sortedCars.forEach(System.out::println);
    }

}

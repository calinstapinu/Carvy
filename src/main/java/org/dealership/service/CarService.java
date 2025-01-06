package org.dealership.service;

import org.dealership.model.Car;
import org.dealership.model.enums.CarStatus;
import org.dealership.repository.DBRepository;
import org.dealership.repository.InMemoryRepository;
import org.dealership.repository.entityRepos.CarRepository;

import org.dealership.exceptions.BusinessLogicException;
import org.dealership.exceptions.DatabaseException;
import org.dealership.exceptions.EntityNotFoundException;
import org.dealership.exceptions.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing {@link Car} entities.
 * This class provides business logic for operations such as adding, retrieving,
 * and updating cars, as well as marking cars as sold or leased.
 */
public class CarService {
    private final CarRepository carRepository;
    private final DBRepository<Car> dbCarRepository;
    private final InMemoryRepository<Car> inMemoryCarRepository;


    public CarService(CarRepository carRepository, DBRepository<Car> dbCarRepository, InMemoryRepository<Car> inMemoryCarRepository) {
        this.carRepository = carRepository;
        this.dbCarRepository = dbCarRepository;
        this.inMemoryCarRepository = inMemoryCarRepository;
    }

    public void addCar(Car car) {
        Car existingCar = carRepository.read(car.getId());
        if (existingCar != null) {
            throw new ValidationException("A car with ID " + car.getId() + " already exists.");
        }
        carRepository.create(car);
    }

    public List<Car> getAllCars() {
        return carRepository.readAll();
    }

    public List<Car> getAvailableCars() {
        if (dbCarRepository != null) {
            return dbCarRepository.executeQuery("SELECT * FROM cars WHERE status = 'Available'");
        } else {
            return carRepository.findAvailableCars();
        }
    }

    public List<Car> getSoldCars() {
        if (dbCarRepository != null) {
            return dbCarRepository.executeQuery("SELECT * FROM cars WHERE status = 'Sold'");
        } else {
            return carRepository.findSoldCars();
        }
    }

    public List<Car> getLeasedCars() {
        if (dbCarRepository != null) {
            return dbCarRepository.executeQuery("SELECT * FROM cars WHERE status = 'Leased'");
        } else {
            return carRepository.findLeasedCars();
        }
    }

    /**
     * Marks a car as sold by updating its status to {@link CarStatus#SOLD}.
     *
     * @param carId the ID of the car to mark as sold
     * @throws IllegalArgumentException if the car is not available or does not exist
     */
//    public void markCarAsSold(long carId) {
//        Car car = carRepository.read(carId);
//        if (car != null && car.getStatus() == CarStatus.AVAILABLE) {
//            car.setStatus(CarStatus.SOLD);
//            carRepository.update(car);
//        } else {
//            throw new IllegalArgumentException("Car not available for selling.");
//        }
//    }

    public void markCarAsSold(long carId) {
        if (dbCarRepository != null) {
            // Database repository logic
            Car car = dbCarRepository.read(carId);
            if (car != null && car.getStatus() == CarStatus.AVAILABLE) {
                car.setStatus(CarStatus.SOLD);
                dbCarRepository.update(car);
                System.out.println("Car ID " + carId + " marked as SOLD in the database.");
            } else {
                throw new IllegalArgumentException("Car not available for selling.");
            }
        } else {
            // File repository logic
            Car car = carRepository.read(carId);
            if (car != null && car.getStatus() == CarStatus.AVAILABLE) {
                car.setStatus(CarStatus.SOLD);
                carRepository.update(car);
                System.out.println("Car ID " + carId + " marked as SOLD in the file repository.");
            } else {
                throw new IllegalArgumentException("Car not available for selling.");
            }
        }
    }


    /**
     * Marks a car as leased by updating its status to {@link CarStatus#LEASED}.
     *
     * @param carId the ID of the car to mark as leased
     * @throws IllegalArgumentException if the car is not available or does not exist
     */
    public void markCarAsLeased(long carId) {
        if (dbCarRepository != null) {
            // Database repository logic
            Car car = dbCarRepository.read(carId);
            if (car != null && car.getStatus() == CarStatus.AVAILABLE) {
                car.setStatus(CarStatus.LEASED);
                dbCarRepository.update(car);
                System.out.println("Car ID " + carId + " marked as LEASED in the database.");
            } else {
                throw new IllegalArgumentException("Car not available for leasing.");
            }
        } else {
            // File repository logic
            Car car = carRepository.read(carId);
            if (car != null && car.getStatus() == CarStatus.AVAILABLE) {
                car.setStatus(CarStatus.LEASED);
                carRepository.update(car);
                System.out.println("Car ID " + carId + " marked as LEASED in the file repository.");
            } else {
                throw new IllegalArgumentException("Car not available for leasing.");
            }
        }
    }


    /**
     * Finds a car by its unique ID.
     *
     * @param carId the ID of the car to find
     * @return the {@link Car} entity with the specified ID
     * @throws IllegalArgumentException if the car does not exist
     */
//    public Car findCarById(long carId) {
//        Car car = carRepository.read(carId);
//        if (car == null) {
//            throw new IllegalArgumentException("The Car with ID " + carId + " does not exist.");
//        }
//        return car;
//    }
    public Car findCarById(long carId) {
        if (dbCarRepository != null) {
            // Use database repository
            Car car = dbCarRepository.read(carId);
            if (car == null) {
                throw new IllegalArgumentException("The Car with ID " + carId + " does not exist.");
            }
            return car;
        } else if (carRepository != null) {
            // Use file repository
            Car car = carRepository.read(carId);
            if (car == null) {
                throw new IllegalArgumentException("The Car with ID " + carId + " does not exist.");
            }
            return car;
        } else {
            throw new IllegalStateException("No repository initialized.");
        }
    }


    /**
     * Finds cars by their name (brand or model).
     *
     * @param name the name to search for
     * @return a list of {@link Car} entities matching the name
     */
//    public List<Car> findCarsByName(String name) {
//        return carRepository.readAll().stream()
//                .filter(car -> car.getModel().toLowerCase().contains(name.toLowerCase()) ||
//                        car.getBrand().toLowerCase().contains(name.toLowerCase()))
//                .collect(Collectors.toList());
//    }
    public List<Car> findCarsByName(String name) {
        if (dbCarRepository != null) {
            // Database repository logic
            String query = "SELECT * FROM cars WHERE LOWER(brand) LIKE ? OR LOWER(model) LIKE ?";
            return dbCarRepository.executeQueryWithParams(query, "%" + name.toLowerCase() + "%", "%" + name.toLowerCase() + "%");
        } else {
            // File repository logic
            return carRepository.readAll().stream()
                    .filter(car -> car.getModel().toLowerCase().contains(name.toLowerCase()) ||
                            car.getBrand().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }


    /**
     * Retrieves cars newer than the specified year.
     *
     * @param year the minimum year of manufacture
     * @return a list of {@link Car} entities newer than the specified year
     */
    public List<Car> getCarsNewerThan(int year) {
        if (dbCarRepository != null) {
            // Database repository logic
            String query = "SELECT * FROM cars WHERE year > " + year;
            return dbCarRepository.executeQuery(query);
        } else {
            // File repository logic
            return carRepository.readAll().stream()
                    .filter(car -> car.getYear() > year)
                    .collect(Collectors.toList());
        }
    }


    /**
     * Retrieves cars within the specified budget.
     *
     * @param maxBudget the maximum budget
     * @return a list of {@link Car} entities within the budget
     */
    public List<Car> getCarsWithinBudget(float maxBudget) {
        if (dbCarRepository != null) {
            // Database repository logic
            String query = "SELECT * FROM cars WHERE price <= " + maxBudget;
            return dbCarRepository.executeQuery(query);
        } else {
            // File repository logic
            return carRepository.readAll().stream()
                    .filter(car -> car.getPrice() <= maxBudget)
                    .collect(Collectors.toList());
        }
    }


    /**
     * Retrieves cars sorted by their year of manufacture in ascending order.
     *
     * @return a list of {@link Car} entities sorted by year
     */
    public List<Car> getCarsSortedByYearAscending() {
        if (dbCarRepository != null) {
            return dbCarRepository.executeQuery("SELECT * FROM cars ORDER BY year ASC");
        } else {
            List<Car> cars = carRepository.readAll();
            cars.sort((car1, car2) -> Integer.compare(car1.getYear(), car2.getYear()));
            return cars;
        }
    }
    /**
     * Retrieves cars sorted by their price in ascending order.
     *
     * @return a list of {@link Car} entities sorted by price
     */
    public List<Car> getCarsSortedByPriceAscending() {
        if (dbCarRepository != null) {
            return dbCarRepository.executeQuery("SELECT * FROM cars ORDER BY price ASC");
        } else {
            List<Car> cars = carRepository.readAll();
            cars.sort((car1, car2) -> Float.compare(car1.getPrice(), car2.getPrice()));
            return cars;
        }
    }
    public void deleteCarFromDB(long carId) {
        dbCarRepository.delete(carId);
    }


    public void deleteCar(long carId) {
        carRepository.delete(carId);
    }
}
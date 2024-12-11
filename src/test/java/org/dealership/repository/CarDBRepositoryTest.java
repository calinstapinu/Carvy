package org.dealership.repository;

import org.dealership.model.Car;
import org.dealership.model.enums.CarStatus;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class CarDBRepositoryTest {

    private DBRepository<Car> carRepository;

    @Before
    public void setUp() {
        carRepository = new DBRepository<>(Car.class, "cars");
    }

    @Test
    public void testCreateCar() {
        Car car = new Car(16, "Toyota", "Camry", 2022, 25000, 12000, CarStatus.AVAILABLE);
        carRepository.create(car);

        Car retrievedCar = carRepository.read(1);
        assertNotNull(retrievedCar);
        assertEquals("Toyota", retrievedCar.getBrand());
    }

    @Test
    public void testReadCar() {
        Car car = carRepository.read(1);
        assertNotNull(car);
        System.out.println(car);
    }

    @Test
    public void testUpdateCar() {
        Car car = new Car(13, "Toyota", "Camry", 2023, 22000.0f, 10000, CarStatus.AVAILABLE);
        carRepository.update(car);

        Car updatedCar = carRepository.read(1);
        assertEquals("Camry", updatedCar.getModel());
    }

    @Test
    public void testDeleteCar() {
        carRepository.delete(13);
        Car deletedCar = carRepository.read(13);
        assertNull(deletedCar);
    }

    @Test
    public void testGetAllCars() {
        List<Car> cars = carRepository.readAll();
        assertNotNull(cars);
        assertFalse(cars.isEmpty());

        for (Car car : cars) {
            System.out.println(car);
        }
    }
}

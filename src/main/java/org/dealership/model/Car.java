package org.dealership.model;

import org.dealership.model.enums.CarStatus;

public class Car implements HasID{
    private long carId;
    private String brand;
    private String model;
    private int year;
    private float price;
    private int mileage;
    private CarStatus status;

    // No-argument constructor
    public Car() {
    }

    public Car(long carId, String brand, String model, int year, float price, int mileage, CarStatus status) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.status = status;
    }

    public long getId() {
        return carId;
    }

    public void setId(long carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public void markAsSold() {
        this.status = CarStatus.SOLD;
    }

    public void markAsLeased() {
        this.status = CarStatus.LEASED;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", mileage=" + mileage +
                ", status=" + status +
                '}';
    }
}

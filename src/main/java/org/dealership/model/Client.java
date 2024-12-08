package org.dealership.model;

import java.util.ArrayList;
import java.util.List;

public class Client extends Person implements HasID{
    private long clientId;
    private List<Car> purchasedCars = new ArrayList<>();
    private List<Leasing> leasedCars = new ArrayList<>();

    public Client() {
        super();

    }
    public Client(String firstName, String lastName, String CNP, long clientId) {
        super(firstName, lastName, CNP);
        this.clientId = clientId;
    }

    public long getId() {
        return clientId;
    }

    public void setId(long clientId) {
        this.clientId = clientId;
    }

    public List<Car> getPurchasedCars() {
        return purchasedCars;
    }

    public void addPurchasedCar(Car car) {
        purchasedCars.add(car);
    }

    public List<Leasing> getLeasedCars() {
        return leasedCars;
    }

    public void addLeasedCar(Leasing leasing) {
        leasedCars.add(leasing);
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", purchasedCars=" + purchasedCars +
                ", leasedCars=" + leasedCars +
                ", fullName=" + getFullName() +
                '}';
    }
}

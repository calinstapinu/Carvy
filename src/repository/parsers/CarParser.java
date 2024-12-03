package repository.parsers;

import model.Car;
import model.enums.CarStatus;

/**
 * Parser for {@link Car} entities to/from CSV format.
 */
public class CarParser implements EntityParser<Car> {

    @Override
    public String toCSV(Car car) {
        return car.getId() + "," +
                car.getBrand() + "," +
                car.getModel() + "," +
                car.getYear() + "," +
                car.getPrice() + "," +
                car.getMileage() + "," +
                car.getStatus();
    }

    @Override
    public Car fromCSV(String csv) {
        String[] fields = csv.split(",");
        return new Car(
                Long.parseLong(fields[0]),
                fields[1],
                fields[2],
                Integer.parseInt(fields[3]),
                Float.parseFloat(fields[4]),
                Integer.parseInt(fields[5]),
                CarStatus.valueOf(fields[6])
        );
    }

    @Override
    public long getId(Car car) {
        return car.getId();
    }
}
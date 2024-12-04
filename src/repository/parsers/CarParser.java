package repository.parsers;

import model.Car;
import model.enums.CarStatus;

/**
 * Parser for {@link Car} entities to/from CSV format.
 * Provides methods to convert a {@link Car} object into a CSV string
 * and to parse a CSV string into a {@link Car} object.
 */
public class CarParser implements EntityParser<Car> {

    /**
     * Converts a {@link Car} object into a CSV string representation.
     *
     * @param car the {@link Car} object to convert
     * @return a CSV string representation of the car
     */
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

    /**
     * Parses a CSV string to create a {@link Car} object.
     *
     * @param csv the CSV string representing a car
     * @return a {@link Car} object created from the CSV string
     */
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

    /**
     * Retrieves the unique ID of a given {@link Car}.
     */
    @Override
    public long getId(Car car) {
        return car.getId();
    }
}
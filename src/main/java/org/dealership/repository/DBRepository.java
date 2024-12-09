package org.dealership.repository;
import org.dealership.model.HasID;
import org.dealership.model.*;
import java.sql.*;
import org.dealership.exceptions.DatabaseException;
import java.util.*;
import java.lang.reflect.Field;


/**
 * Generic DBRepository for performing CRUD operations on entities.
 * @param <T> The type of the entity extending HasID.
 */
public class DBRepository<T extends HasID> implements IRepository<T> {
    private final Class<T> type;
    private final String tableName;

    // Database connection parameters
    private static final String URL = "jdbc:postgresql://localhost:5432/carvy";
    private static final String USER = "postgres";
    private static final String PASSWORD = "a";

    public DBRepository(Class<T> type, String tableName) {
        this.type = type;
        this.tableName = tableName;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void create(T obj) {
        String sql = null;
        try {
            sql = generateInsertSQL(obj);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating SQL", e);
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParameters(stmt, obj);
            System.out.println("Prepared statement parameters set.");
            stmt.executeUpdate();
            System.out.println("Record inserted successfully into table: " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public T read(long id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getPrimaryKeyColumn() + " = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                T obj = mapResultSetToObject(rs);
                return obj;
            } else {
                System.out.println("No record found for ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void update(T obj) {
        String sql = generateUpdateSQL(obj);
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParameters(stmt, obj);
            stmt.setLong(stmt.getParameterMetaData().getParameterCount(), obj.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + " = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> readAll() {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToObject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Generate INSERT SQL
    private String generateInsertSQL(T obj) throws SQLException {
        String tableName = getTableName();
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");

        List<String> columns = new ArrayList<>();
        List<String> placeholders = new ArrayList<>();
        Set<String> addedColumns = new HashSet<>();

        Class<?> currentClass = obj.getClass();
        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                if (shouldIncludeField(field)) {
                    field.setAccessible(true);
                    String columnName = toSnakeCase(field.getName());

                    // Avoid adding the same column multiple times
                    if (!addedColumns.contains(columnName) &&
                            !field.getName().equalsIgnoreCase("car") &&
                            !field.getName().equalsIgnoreCase("client")) {

                        columns.add(columnName);
                        placeholders.add("?");
                        addedColumns.add(columnName);
                    }
                }
            }
            currentClass = currentClass.getSuperclass();
        }

        if (columns.isEmpty()) {
            throw new SQLException("No fields available for insertion");
        }

        sql.append(String.join(", ", columns));
        sql.append(") VALUES (");
        sql.append(String.join(", ", placeholders));
        sql.append(")");
        return sql.toString();
    }

    // Generate UPDATE SQL
    private String generateUpdateSQL(T obj) {
        StringBuilder sql = new StringBuilder("UPDATE ").append(getTableName()).append(" SET ");

        List<String> columns = new ArrayList<>();
        Class<?> currentClass = obj.getClass();

        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                if (!field.getName().equalsIgnoreCase("id") && shouldIncludeField(field)) {
                    field.setAccessible(true);
                    columns.add(toSnakeCase(field.getName()) + " = ?");
                }
            }
            currentClass = currentClass.getSuperclass();
        }

        if (columns.isEmpty()) {
            throw new RuntimeException("No fields available for updating");
        }

        sql.append(String.join(", ", columns));
        sql.append(" WHERE ").append(getIdColumnName()).append(" = ?");

        return sql.toString();
    }


    // Set Parameters for PreparedStatement
    private void setParameters(PreparedStatement stmt, T obj) throws SQLException {
        int index = 1;
        try {
            Class<?> currentClass = obj.getClass();
            while (currentClass != null) {
                for (Field field : currentClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (!Collection.class.isAssignableFrom(field.getType())) {
                        Object value = field.get(obj);

                        // Skip setting 'car' and 'client' fields
                        if (field.getName().equalsIgnoreCase("car") || field.getName().equalsIgnoreCase("client")) {
                            continue;
                        }
                        // Handle references to other objects by setting their IDs
                        if (value != null && value instanceof HasID) {
                            value = ((HasID) value).getId();
                        }
                        else if (field.getName().equals("car")) {
                            Car car = (Car) value;
                            if (car != null) {
                                stmt.setLong(index++, car.getId());
                            }
                        } else if (field.getName().equals("client")) {
                            Client client = (Client) value;
                            if (client != null) {
                                stmt.setLong(index++, client.getId());
                            }
                        }
                        // Handle enums explicitly
                        if (value instanceof Enum) {
                            String dbValue = capitalizeFirstLetter(value.toString().toLowerCase());
                            stmt.setString(index++, dbValue);
                        }
                        // Handle java.util.Date by converting to java.sql.Timestamp
                        else if (value != null && value instanceof java.util.Date) {
                            stmt.setTimestamp(index++, new java.sql.Timestamp(((java.util.Date) value).getTime()));
                        }
                        // Handle other types
                        else {
                            stmt.setObject(index++, value);
                        }
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
        } catch (IllegalAccessException e) {
            throw new SQLException("Error setting parameters", e);
        }
    }


    // Map ResultSet to Object
//    private T mapResultSetToObject(ResultSet rs) throws SQLException {
//        try {
//            T obj = type.getDeclaredConstructor().newInstance();
//
//            for (Field field : type.getDeclaredFields()) {
//                field.setAccessible(true);
//                String columnName = toSnakeCase(field.getName());
//                // Skip fields that are collections (e.g., List, Set)
//                if (Collection.class.isAssignableFrom(field.getType())) {
//                    continue;
//                }
//
//                Object value = rs.getObject(columnName);
//
//                System.out.println("Mapping field: " + field.getName() + " -> column: " + columnName + " -> value: " + value);
//                if (value != null) {
//                    // Handle enums explicitly
//                    if (field.getType().isEnum()) {
//                        value = Enum.valueOf((Class<Enum>) field.getType(), value.toString().toUpperCase());
//                    }
//
//                    // Handle float fields explicitly (cast Double to float)
//                    if (value != null && field.getType() == float.class && value instanceof Double) {
//                        value = ((Double) value).floatValue();
//                    }
//
//                    // Handle float fields explicitly (cast Double to float)
//                    if (field.getType() == String.class && value instanceof String) {
//                        field.set(obj, value);
//                    }else{
//                        field.set(obj, value);
//                    }
//
//
//                }
//            }
//
//            return obj;
//        } catch (Exception e) {
//            throw new SQLException("Error mapping ResultSet to object", e);
//        }
//    }

    private T mapResultSetToObject(ResultSet rs) throws SQLException {
        try {
            T obj = type.getDeclaredConstructor().newInstance();

            // Iterate through all fields, including those in superclasses
            Class<?> currentClass = type;
            while (currentClass != null) {
                for (Field field : currentClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    String columnName = toSnakeCase(field.getName());

                    // Skip fields that are collections (e.g., List, Set)
                    if (Collection.class.isAssignableFrom(field.getType())) {
//                        System.out.println("Skipping collection field: " + field.getName());
                        continue;
                    }

                    Object value = null;
                    //System.out.println("Mapping field: " + field.getName() + " -> column: " + columnName + " -> value: " + value);
                    try {
                        value = rs.getObject(columnName);
                    } catch (SQLException e) {
                        // If the column doesn't exist in the ResultSet, skip setting this field
                        //System.out.println("Column not found: " + columnName + ", skipping.");
                        continue;
                    }

                    if (value != null) {
                        // Handle enums explicitly
                        if (field.getType().isEnum()) {
                            value = Enum.valueOf((Class<Enum>) field.getType(), value.toString().toUpperCase());
                        }

                        // Handle float fields explicitly (cast Double to float)
                        if (field.getType() == float.class && value instanceof Double) {
                            value = ((Double) value).floatValue();
                        }

                        // Set the field value
                        field.set(obj, value);
                    }
                }
                // Move to the superclass to process inherited fields
                currentClass = currentClass.getSuperclass();
            }

            // Special handling for Car and Client fields
            if (obj instanceof Leasing) {
                Leasing leasing = (Leasing) obj;
                if (leasing.getCarId() != 0) {
                    leasing.setCar(fetchCarById(leasing.getCarId()));
                }
                if (leasing.getClientId() != 0) {
                    leasing.setClient(fetchClientById(leasing.getClientId()));
                }
            }


            return obj;
        } catch (Exception e) {
            throw new SQLException("Error mapping ResultSet to object", e);
        }
    }





    private String getTableName() {
        if (type == Car.class) {
            return "cars";
        } else if (type == Client.class) {
            return "clients";
        } else if (type == Employee.class) {
            return "employees";
        } else if (type == Leasing.class) {
            return "leasings";
        } else if (type == Transaction.class) {
            return "transactions";
        } else {
            throw new IllegalArgumentException("Unknown entity type: " + type.getSimpleName());
        }
    }

    private String getIdColumnName() {
        if (type == Car.class) {
            return "car_id";
        } else if (type == Client.class) {
            return "client_id";
        } else if (type == Employee.class) {
            return "employee_id";
        } else if (type == Leasing.class) {
            return "leasing_id";
        } else if (type == Transaction.class) {
            return "transaction_id";
        } else {
            throw new IllegalArgumentException("Unknown type: " + type);
        }
    }


    private boolean shouldIncludeField(Field field) {
        return !Collection.class.isAssignableFrom(field.getType());
    }

    private String toSnakeCase(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private String getPrimaryKeyColumn() {
        String className = type.getSimpleName().toLowerCase();
        return className + "_id";
    }

    private Car fetchCarById(long carId) {
        DBRepository<Car> carRepo = new DBRepository<>(Car.class, "cars");
        return carRepo.read(carId);
    }

    private Client fetchClientById(long clientId) {
        DBRepository<Client> clientRepo = new DBRepository<>(Client.class, "clients");
        return clientRepo.read(clientId);
    }


}


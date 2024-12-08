//package org.dealership.repository;
//
//import org.dealership.repository.IRepository;
//import org.dealership.repository.parsers.EntityParser;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DBRepository<T> implements IRepository<T> {
//
//    private final String url = "jdbc:mysql://localhost:3306/carvy";
//    private final String user = "root";
//    private final String password = "password";
//    private final String tableName;
//    private final EntityParser<T> parser;
//
//    public DBRepository(String tableName, EntityParser<T> parser) {
//        this.tableName = tableName;
//        this.parser = parser;
//    }
//
//    private Connection connect() throws SQLException {
//        return DriverManager.getConnection(url, user, password);
//    }
//
//    @Override
//    public void create(T entity) {
//        String sql = "INSERT INTO " + tableName + " VALUES (" + parser.toInsertValues(entity) + ")";
//        try (Connection conn = connect();
//             Statement stmt = conn.createStatement()) {
//            stmt.executeUpdate(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public T read(long id) {
//        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
//        try (Connection conn = connect();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setLong(1, id);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                return parser.fromResultSet(rs);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public List<T> readAll() {
//        List<T> entities = new ArrayList<>();
//        String sql = "SELECT * FROM " + tableName;
//        try (Connection conn = connect();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                entities.add(parser.fromResultSet(rs));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return entities;
//    }
//
//    @Override
//    public void update(T entity) {
//        String sql = "UPDATE " + tableName + " SET " + parser.toUpdateValues(entity) + " WHERE id = ?";
//        try (Connection conn = connect();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setLong(1, parser.getId(entity));
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void delete(long id) {
//        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
//        try (Connection conn = connect();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setLong(1, id);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}

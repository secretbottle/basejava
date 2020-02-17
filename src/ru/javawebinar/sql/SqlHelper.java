package ru.javawebinar.sql;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.StorageException;

import java.sql.*;

public class SqlHelper {
    private ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void executePrepStatement(String sqlQuery, ExecutorPrepStatement exPs) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            exPs.execute(ps);
        } catch (SQLException e) {
            if(e.getSQLState().equals("23505"))
                throw new ExistStorageException(e);
            throw new StorageException(e);
        }
    }

    public void executeStatement(String sqlQuery) {
        try (Connection conn = connectionFactory.getConnection();
             Statement st = conn.createStatement()) {
                st.execute(sqlQuery);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void executeResultSet(String sqlQuery, ExecutorResStatement executor) {
        try (Connection conn = connectionFactory.getConnection();
             ResultSet rs = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)
                     .executeQuery(sqlQuery)) {
            executor.execute(rs);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}


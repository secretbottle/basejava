package ru.javawebinar.sql;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.StorageException;

import java.sql.*;

public class SqlHelper {
    private ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executePrepStatement(String sqlQuery, ExecutorStatement<T, PreparedStatement> exPs) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            return exPs.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505"))
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

    public <T> T executeResultSet(String sqlQuery, ExecutorStatement<T, ResultSet> executor) {
        try (Connection conn = connectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sqlQuery);
            return executor.execute(rs);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}


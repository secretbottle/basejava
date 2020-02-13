package ru.javawebinar.sql;

import ru.javawebinar.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlHelper {
    private ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void executePrepStatement(String sqlQuery){
        this.executePrepStatement(sqlQuery, (ps)->{});
    }

    public void executePrepStatement(String sqlQuery, ExecutorPrepStatement exPs) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            exPs.execute(ps);
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void executeStatement(String sqlQuery, ExecutorStatement1 executor) {
        try (Connection conn = connectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            executor.execute(st);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}


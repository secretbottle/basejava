package ru.javawebinar.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ExecutorStatement<T> {
    T execute(PreparedStatement ps) throws SQLException;
}

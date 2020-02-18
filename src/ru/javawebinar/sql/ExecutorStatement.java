package ru.javawebinar.sql;

import java.sql.SQLException;

public interface ExecutorStatement<T, S> {
    T execute(S s) throws SQLException;
}

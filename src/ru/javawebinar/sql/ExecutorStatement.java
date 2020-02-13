package ru.javawebinar.sql;

import java.sql.SQLException;
import java.sql.Statement;

public interface ExecutorStatement {
    void execute(Statement st) throws SQLException;
}

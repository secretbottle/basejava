package ru.javawebinar.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ExecutorStatement {
    void execute(ResultSet rs) throws SQLException;
}

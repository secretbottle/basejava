package ru.javawebinar.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ExecutorPrepStatement {
    void execute(PreparedStatement ps) throws SQLException;
}

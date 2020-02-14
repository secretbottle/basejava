package ru.javawebinar.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ExecutorPrepStatement {
    String execute(PreparedStatement ps) throws SQLException;
}

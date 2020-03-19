package ru.javawebinar.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ConsumerRS {
    void accept(ResultSet rs) throws SQLException;
}

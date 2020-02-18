package ru.javawebinar.storage;

import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() ->
                DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.executeStatement("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.executePrepStatement("UPDATE resume SET full_name =? WHERE uuid = ?",
                (ps) -> {
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, resume.getUuid());
                    if (ps.executeUpdate() == 0)
                        throw new NotExistStorageException(resume.getUuid());
                    return null;
                });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.executePrepStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                (ps) -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    ps.executeUpdate();
                    return null;
                });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executePrepStatement("SELECT * FROM resume r WHERE r.uuid = ?",
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executePrepStatement("DELETE FROM resume WHERE uuid = ?",
                (ps) -> {
                    ps.setString(1, uuid);
                    int exUp = ps.executeUpdate();
                    if (exUp == 0)
                        throw new NotExistStorageException(uuid);
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeResultSet("SELECT * FROM resume ORDER BY full_name, uuid ASC",
                (rs) -> {
                    List<Resume> list = new ArrayList<>();
                    while (rs.next()) {
                        list.add(
                                new Resume(
                                        rs.getString(1),
                                        rs.getString(2)
                                ));
                    }
                    return list;
                });
    }

    @Override
    public int size() {
        return sqlHelper.executeResultSet("SELECT COUNT(*) FROM resume",
                (rs) -> {
                    rs.next();
                    return rs.getInt(1);
                });
    }

}

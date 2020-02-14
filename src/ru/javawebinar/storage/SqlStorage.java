package ru.javawebinar.storage;

import ru.javawebinar.exception.ExistStorageException;
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
        sqlHelper.executePrepStatement(false, "UPDATE resume SET full_name =? WHERE uuid = ?",
                (ps) -> {
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, resume.getUuid());
                    return resume.getUuid();
                });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.executePrepStatement(true, "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                (ps) -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new ExistStorageException(resume.getUuid());
                    }

                    return resume.getUuid();
                });
    }


    //TODO Check that, I think this is bad idea
    @Override
    public Resume get(String uuid) {
        Resume[] resume = new Resume[1];

        sqlHelper.executePrepStatement(false,"SELECT * FROM resume r WHERE r.uuid = ?",
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    resume[0] = new Resume(uuid, rs.getString("full_name"));
                    return uuid;
                });
        return resume[0];
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executePrepStatement(false,"DELETE FROM resume WHERE uuid = ?",
                (ps) -> {
                    ps.setString(1, uuid);
                    return uuid;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        sqlHelper.executeResultSet("SELECT * FROM resume ORDER BY full_name ASC",
                (rs) -> {
                    while (rs.next()) {
                        list.add(
                                new Resume(
                                        rs.getString(1).replaceAll("\\s+", ""),
                                        rs.getString(2)
                                ));
                    }
                });
        return list;
    }

    @Override
    public int size() {
        final int[] size = {0};
        sqlHelper.executeResultSet("SELECT * FROM resume",
                (rs) -> {
                    rs.last();
                    size[0] = rs.getRow();
                });
        return size[0];
    }

}

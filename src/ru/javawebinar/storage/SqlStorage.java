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
                    if(ps.executeUpdate() == 0)
                        throw new NotExistStorageException(resume.getUuid());
                });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.executePrepStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                (ps) -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    ps.executeUpdate();
                });
    }
    
    @Override
    public Resume get(String uuid) {
        final Resume[] resume = new Resume[1];
        sqlHelper.executePrepStatement("SELECT * FROM resume r WHERE r.uuid = ?",
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    resume[0] = new Resume(uuid, rs.getString("full_name"));
                });
        return resume[0];
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executePrepStatement("DELETE FROM resume WHERE uuid = ?",
                (ps) -> {
                    ps.setString(1, uuid);
                    int exUp = ps.executeUpdate();
                    if(exUp == 0)
                        throw new NotExistStorageException(uuid);
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

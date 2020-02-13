package ru.javawebinar;

import ru.javawebinar.exception.ExistStorageException;
import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.sql.ConnectionFactory;
import ru.javawebinar.sql.SqlHelper;
import ru.javawebinar.storage.Storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper  = new SqlHelper(() ->
                DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.executePrepStatement("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.executePrepStatement("UPDATE resume SET full_name =? WHERE uuid = ?",
                (ps)->{
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, resume.getUuid());
                });
    }

    //TODO Method is need throws ExistStorageException
    @Override
    public void save(Resume resume) {
        sqlHelper.executePrepStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                (ps)->{
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                });

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)");) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new ExistStorageException(resume.getUuid(), e);
        } catch (SQLException a){
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
            ps.setString(1, uuid);
            int check = ps.executeUpdate();
            if (check == 0) {
                throw new NotExistStorageException("SQLError: uuid was not delete");
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try (Connection conn = connectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            List<Resume> list = new ArrayList<>();
            ResultSet rs = st.executeQuery("SELECT * FROM resume ORDER BY full_name ASC");
            while (rs.next()) {
                list.add(
                        new Resume(
                                rs.getString(1).replaceAll("\\s+", ""),
                                rs.getString(2)
                        ));
            }
            return list;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        try (Connection conn = connectionFactory.getConnection();
             Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = st.executeQuery("SELECT * FROM resume");
            rs.last();
            return rs.getRow();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}

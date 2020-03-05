package ru.javawebinar.storage;

import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.ContactType;
import ru.javawebinar.model.Resume;
import ru.javawebinar.sql.SqlHelper;

import java.sql.*;
import java.util.*;

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
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name =? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0)
                    throw new NotExistStorageException(resume.getUuid());
            }

            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ps.executeUpdate();
            }
            insContacts(resume, conn);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    insContacts(resume, conn);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executePrepStatement("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid = ? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    getContacts(resume, rs);

                    return resume;
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
        return sqlHelper.executePrepStatement("" +
                        " SELECT * FROM resume r" +
                        " LEFT JOIN contact c ON c.resume_uuid = r.uuid" +
                        " ORDER BY r.full_name, r.uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    Set<Resume> set = new LinkedHashSet<>();
                    while (rs.next()) {
                        Resume resume = new Resume(
                                rs.getString("uuid"),
                                rs.getString("full_name"));
                        getContacts(resume, rs);
                        set.add(resume);
                    }
                    return new ArrayList<>(set);
                });
    }

    @Override
    public int size() {
        return sqlHelper.executePrepStatement("SELECT COUNT(*) FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    return rs.getInt(1);
                });
    }

    private Resume getContacts(Resume resume, ResultSet rs) throws SQLException {
        String uuid = resume.getUuid();
        do {
            String type = rs.getString("type");
            String value = rs.getString("value");
            if (type == null)
                break;
            ContactType contactType = ContactType.valueOf(type);

            if (!uuid.equals(rs.getString("resume_uuid")) ||
                    resume.getContactMap().containsKey(contactType)) {
                rs.previous();
                break;
            }

            if (value.equals(""))
                value = null;

            resume.putContactMap(contactType, value);
        } while (rs.next());

        return resume;
    }

    private void insContacts(Resume resume, Connection conn) throws SQLException {
        if (resume.getContactMap().size() != 0)
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> e : resume.getContactMap().entrySet()) {
                    String value = e.getValue();

                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
                    if (value == null) {
                        ps.setString(3, "");
                    } else {
                        ps.setString(3, value);
                    }
                    ps.addBatch();
                }
                ps.executeBatch();
            }
    }

}

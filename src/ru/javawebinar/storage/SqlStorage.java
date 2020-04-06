package ru.javawebinar.storage;

import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.*;
import ru.javawebinar.sql.ConsumerRS;
import ru.javawebinar.sql.SqlHelper;
import ru.javawebinar.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
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

            delete("DELETE FROM contact WHERE resume_uuid = ?", conn, resume);
            insContacts(resume, conn);

            delete("DELETE FROM section WHERE resume_uuid = ?", conn, resume);
            insSection(resume, conn);

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
                    insSection(resume, conn);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                selector(ps, rs -> getContacts(resume, rs));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                selector(ps, rs -> getSections(resume, rs));
            }

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
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> sortedMap = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = new Resume(uuid,
                            rs.getString("full_name"));
                    sortedMap.put(uuid, resume);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact ORDER BY resume_uuid")) {
                selector(ps, rs -> getContacts(sortedMap.get(rs.getString("resume_uuid")), rs));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section ORDER BY resume_uuid")) {
                selector(ps, rs -> getSections(sortedMap.get(rs.getString("resume_uuid")), rs));
            }

            return new ArrayList<>(sortedMap.values());
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

    private void getContacts(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if(value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            resume.putContactMap(type, value);
        }
    }

    private void getSections(Resume resume, ResultSet rs) throws SQLException {
        String content = rs.getString("content");
        if(content != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            resume.putSectionMap(type, JsonParser.read(content, Section.class));
        }
    }

    private void insContacts(Resume resume, Connection conn) throws SQLException {
        if (resume.getContactMap().size() != 0)
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> e : resume.getContactMap().entrySet()) {
                    writer(ps, resume.getUuid(), e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
    }

    private void insSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSectionMap().entrySet()) {
                writer(ps, resume.getUuid(), e.getKey().name());
                Section section = e.getValue();
                ps.setString(3, JsonParser.write(section, Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void writer(PreparedStatement ps, String value1, String value2) throws SQLException {
        ps.setString(1, value1);
        ps.setString(2, value2);
    }

    private void selector(PreparedStatement ps, ConsumerRS consumer) throws SQLException {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            consumer.accept(rs);
        }
    }

    private void delete(String query, Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }

}
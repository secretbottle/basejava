package ru.javawebinar.storage;

import ru.javawebinar.exception.NotExistStorageException;
import ru.javawebinar.model.*;
import ru.javawebinar.sql.SqlHelper;
import ru.javawebinar.storage.serial.functional.FunctionThrowing;

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
            insContact(resume, conn);

            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ps.executeUpdate();
            }
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
                    insContact(resume, conn);
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
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    getContact(resume, rs);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    getSection(resume, rs);
                }
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
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    getContact(sortedMap.get(rs.getString("resume_uuid")), rs);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    getSection(sortedMap.get(rs.getString("resume_uuid")), rs);
                }
            }


            sort(conn, )

            return new ArrayList<>(sortedMap.values());
        });
    }

    private void sort(PreparedStatement ps, FunctionThrowing func) throws SQLException {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                getSection(sortedMap.get(rs.getString("resume_uuid")), rs);
            }
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

    private void getContact(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        ContactType type = ContactType.valueOf(rs.getString("type"));
        resume.putContactMap(type, value);
    }

    private void insContact(Resume resume, Connection conn) throws SQLException {
        if (resume.getContactMap().size() != 0)
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> e : resume.getContactMap().entrySet()) {
                    writer(ps, resume.getUuid(),
                            e.getKey().name(),
                            e.getValue());
                }
                ps.executeBatch();
            }
    }

    private void getSection(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        SectionType type = SectionType.valueOf(rs.getString("type"));
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                TextSection textSection = new TextSection(value);
                resume.putSectionMap(type, textSection);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> descList = new ArrayList<>(Arrays.asList(value.split("\n")));
                ListSection listSection = new ListSection(descList);
                resume.putSectionMap(type, listSection);
                break;
            case EXPERIENCE:
            case EDUCATION:
                //Place for OrganizationSection
                break;
        }
    }

    private void insSection(Resume resume, Connection conn) throws SQLException {
        if (resume.getSectionMap().size() != 0)
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<SectionType, Section> e : resume.getSectionMap().entrySet()) {
                    SectionType st = e.getKey();
                    switch (st) {
                        case PERSONAL:
                        case OBJECTIVE:
                            TextSection ts = (TextSection) e.getValue();
                            writer(ps, resume.getUuid(),
                                    st.name(),
                                    ts.getText());
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            ListSection ls = (ListSection) e.getValue();
                            writer(ps, resume.getUuid(),
                                    st.name(),
                                    String.join("\n", ls.getDescriptionList()));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            //Place for OrganizationSection
                            break;
                    }

                }
                ps.executeBatch();
            }
    }

    private void writer(PreparedStatement ps, String... values) throws SQLException {
        int i = 1;
        for (String s : values) {
            ps.setString(i, s);
            i++;
        }
        ps.addBatch();
    }

}

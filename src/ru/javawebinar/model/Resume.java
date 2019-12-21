package ru.javawebinar.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private final Map<ContactType, String> contactMap = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sectionMap = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid is null");
        Objects.requireNonNull(fullName, "fullName is null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContactMap() {
        return contactMap;
    }

    public Map<SectionType, Section> getSectionMap() {
        return sectionMap;
    }

    public void putContactMap(ContactType contactType, String field) {
        contactMap.put(contactType, field);
    }

    public void putSectionMap(SectionType sectionType, Section section) {
        sectionMap.put(sectionType, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public String toString() {
        return uuid + " - " + fullName;
    }

    @Override
    public int compareTo(Resume o) {
        return Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid).compare(this, o);
    }

}

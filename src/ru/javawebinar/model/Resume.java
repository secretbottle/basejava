package ru.javawebinar.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.*;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private String uuid;
    private String fullName;

    private final Map<ContactType, String> contactMap = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sectionMap = new EnumMap<>(SectionType.class);

    public Resume() {
    }

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

    public String getContact(ContactType type) {
        return contactMap.get(type);
    }

    public Section getSection(SectionType type) {
        return sectionMap.get(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName) &&
                contactMap.equals(resume.contactMap) &&
                sectionMap.equals(resume.sectionMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contactMap, sectionMap);
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

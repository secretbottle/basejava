package ru.javawebinar.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ListSection implements Section {
    private static final long serialVersionUID = 1L;

    private List<String> descriptionList;

    public ListSection() {
    }

    public ListSection(List<String> descriptionList) {
        Objects.requireNonNull(descriptionList, "Inputed parameter descriptionList is null");
        this.descriptionList = descriptionList;
    }

    public List<String> getDescriptionList() {
        return descriptionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection listSection = (ListSection) o;
        return descriptionList.equals(listSection.descriptionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriptionList);
    }

    @Override
    public String toString() {
        return descriptionList.toString();
    }
}

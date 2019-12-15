package ru.javawebinar.model;

import java.util.List;
import java.util.Objects;

public class ListField implements Section {
    private final List<String> descriptionList;

    public ListField(List<String> descriptionList) {
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
        ListField listField = (ListField) o;
        return descriptionList.equals(listField.descriptionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriptionList);
    }
}

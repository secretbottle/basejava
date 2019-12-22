package ru.javawebinar.model;

import java.util.List;
import java.util.Objects;

public class OrganizationsSection implements Section {
    private final List<Organization> periodSections;

    public OrganizationsSection(List<Organization> periodSections) {
        Objects.requireNonNull(periodSections, "Inputed parameter periodSections is null");
        this.periodSections = periodSections;
    }

    public List<Organization> getPeriodSections() {
        return periodSections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationsSection that = (OrganizationsSection) o;
        return periodSections.equals(that.periodSections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(periodSections);
    }

    @Override
    public String toString() {
        return periodSections.toString();
    }
}

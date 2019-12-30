package ru.javawebinar.model;

import java.util.List;
import java.util.Objects;

public class OrganizationsSection implements Section {
    private final List<Organization> organizations;

    public OrganizationsSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "Inputed parameter organizations is null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationsSection that = (OrganizationsSection) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}

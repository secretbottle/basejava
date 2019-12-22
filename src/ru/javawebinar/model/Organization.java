package ru.javawebinar.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link link;
    private final List<OrganizationDescription> organizationDescriptions;

    public Organization(Link link, List<OrganizationDescription> organizationDescriptions) {
        this.link = link;
        this.organizationDescriptions = organizationDescriptions;
    }

    public Link getLink() {
        return link;
    }

    public List<OrganizationDescription> getOrganizationDescriptions() {
        return organizationDescriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;

        return link.equals(that.link) &&
                organizationDescriptions.equals(that.organizationDescriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, organizationDescriptions);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "link=" + link.toString() +
                ", organizationDescriptions=" + organizationDescriptions +
                '}';
    }
}


package ru.javawebinar.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link link;
    private final List<Position> positions;

    public Organization(Link link, List<Position> positions) {
        this.link = link;
        this.positions = positions;
    }

    public Link getLink() {
        return link;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;

        return link.equals(that.link) &&
                positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, positions);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "link=" + link.toString() +
                ", positions=" + positions +
                '}';
    }
}


package ru.javawebinar.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
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

    public static class Position implements Serializable {
        private final LocalDate startPeriod;
        private final LocalDate endPeriod;
        private final String position;
        private final String description;

        public Position(LocalDate startPeriod, LocalDate endPeriod, String position, String description) {
            Objects.requireNonNull(startPeriod, "Inputed parameter startPeriod is null");
            Objects.requireNonNull(endPeriod, "Inputed parameter endPeriod is null");
            Objects.requireNonNull(position, "Inputed parameter position is null");

            this.startPeriod = startPeriod;
            this.endPeriod = endPeriod;
            this.position = position;
            this.description = description;
        }

        public LocalDate getStartPeriod() {
            return startPeriod;
        }

        public LocalDate getEndPeriod() {
            return endPeriod;
        }

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Position that = (Position) o;

            return startPeriod.equals(that.startPeriod) &&
                    endPeriod.equals(that.endPeriod) &&
                    position.equals(that.position) &&
                    Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startPeriod, endPeriod, position, description);
        }

        @Override
        public String toString() {
            return startPeriod + " - " + endPeriod + " " + position + " : " + description;
        }

    }
}


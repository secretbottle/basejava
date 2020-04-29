package ru.javawebinar.model;

import ru.javawebinar.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.util.DateUtil.NOW;
import static ru.javawebinar.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private Link link;
    private List<Position> positions;

    public static final Organization EMPTY = new Organization("", "", Position.EMPTY);

    public Organization() {
    }

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startPeriod;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endPeriod;
        private String position;
        private String description;

        public static final Position EMPTY = new Position();

        public Position() {
        }

        public Position(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

        public Position(LocalDate startPeriod, LocalDate endPeriod, String position, String description) {
            Objects.requireNonNull(startPeriod, "Inputed parameter startPeriod is null");
            Objects.requireNonNull(endPeriod, "Inputed parameter endPeriod is null");
            Objects.requireNonNull(position, "Inputed parameter position is null");

            this.startPeriod = startPeriod;
            this.endPeriod = endPeriod;
            this.position = position;
            this.description = description == null ? "" : description;
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


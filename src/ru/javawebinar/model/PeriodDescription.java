package ru.javawebinar.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PeriodDescription {
    private final Link link;
    private List<Description> descriptions = new ArrayList<>();

    public PeriodDescription(Link link, List<Description> descriptions) {
        this.link = link;
        this.descriptions = descriptions;
    }

    public Link getLink() {
        return link;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeriodDescription that = (PeriodDescription) o;

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


package ru.javawebinar.model;

import java.time.LocalDate;
import java.util.Objects;

public class PeriodDescription {
    private final LocalDate startPeriod;
    private final LocalDate endPeriod;
    private final String position;
    private final String description;

    public PeriodDescription(LocalDate startPeriod, LocalDate endPeriod, String position, String description) {
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


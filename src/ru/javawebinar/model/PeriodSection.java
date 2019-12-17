package ru.javawebinar.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PeriodSection implements Section {
    private final Map<Link, List<PeriodDescription>> periodSections;

    public PeriodSection(Map<Link, List<PeriodDescription>> periodSections) {
        Objects.requireNonNull(periodSections, "Inputed parameter periodSections is null");
        this.periodSections = periodSections;
    }

    public Map<Link, List<PeriodDescription>> getPeriodSections() {
        return periodSections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeriodSection that = (PeriodSection) o;
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

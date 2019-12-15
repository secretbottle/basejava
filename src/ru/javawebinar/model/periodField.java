package ru.javawebinar.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class periodField implements Section {
    private final HashMap<URL, ArrayList<PeriodDescription>> periodSections;

    public periodField(HashMap<URL, ArrayList<PeriodDescription>> periodSections) {
        Objects.requireNonNull(periodSections, "Inputed parameter periodSections is null");
        this.periodSections = periodSections;
    }

    public HashMap<URL, ArrayList<PeriodDescription>> getPeriodSections() {
        return periodSections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        periodField that = (periodField) o;
        return periodSections.equals(that.periodSections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(periodSections);
    }
}

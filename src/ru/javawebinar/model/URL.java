package ru.javawebinar.model;

import java.util.Objects;

public class URL {
    private final String title;
    private final String urlAdr;

    public URL(String title, String urlAdr) {
        Objects.requireNonNull(title, "Inputed title startPeriod is null");
        Objects.requireNonNull(urlAdr, "Inputed urlAdr startPeriod is null");
        this.title = title;
        this.urlAdr = urlAdr;
    }

    public URL(String title) {
        Objects.requireNonNull(title, "Inputed title startPeriod is null");
        this.title = title;
        this.urlAdr = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URL url = (URL) o;
        return title.equals(url.title) &&
                urlAdr.equals(url.urlAdr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, urlAdr);
    }

    @Override
    public String toString() {
        return "URL{" +
                "title='" + title + '\'' +
                ", urlAdr='" + urlAdr + '\'' +
                '}';
    }
}

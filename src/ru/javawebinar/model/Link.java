package ru.javawebinar.model;

import java.util.Objects;

public class Link {
    private final String title;
    private final String urlAdr;

    public Link(String title, String urlAdr) {
        Objects.requireNonNull(title, "Inputed title is null");
        this.title = title;
        this.urlAdr = urlAdr;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlAdr() {
        return urlAdr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;

        if(!title.equals(link.title))
            return false;

        if (Objects.equals(urlAdr, null)) {
            return false;
        } else {
            return urlAdr.equals(link.urlAdr);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, urlAdr);
    }

    @Override
    public String toString() {
        return "Link{" +
                "title='" + title + '\'' +
                ", urlAdr='" + urlAdr + '\'' +
                '}';
    }

}

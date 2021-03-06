package ru.javawebinar.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String urlAdr;

    public Link() {
    }

    public Link(String title, String urlAdr) {
        Objects.requireNonNull(title, "Inputed title is null");
        this.title = title;
        this.urlAdr = urlAdr == null ? "" : urlAdr;
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

        return title.equals(link.title) && Objects.equals(urlAdr, link.urlAdr);
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

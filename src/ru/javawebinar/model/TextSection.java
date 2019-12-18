package ru.javawebinar.model;

import java.util.Objects;

public class TextSection implements Section {
    private final String text;

    public TextSection(String text) {
        Objects.requireNonNull(text, "Inputed parameter text is null");
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection textSection = (TextSection) o;
        return text.equals(textSection.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return text;
    }
}
package ru.javawebinar.model;

import java.util.Objects;

public class TextField implements Section {
    private final String text;

    public TextField(String text) {
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
        TextField textField = (TextField) o;
        return text.equals(textField.text);
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

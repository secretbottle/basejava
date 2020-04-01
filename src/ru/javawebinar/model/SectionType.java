package ru.javawebinar.model;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование"){

    };

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(Section section) {
        return (section == null) ? "" : toHtml0(section);
    }

    protected String toHtml0(Section section) {
        return title + ": " + section;
    }
}

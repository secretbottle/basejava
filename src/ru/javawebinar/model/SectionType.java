package ru.javawebinar.model;

public enum SectionType {
    PERSONAL("Личные качества"){
        @Override
        protected String toHtml0(Section section) {
            TextSection value = (TextSection) section;
            return getTitle() + "\n" + value.getText();
        }
    },
    OBJECTIVE("Позиция"){

    },
    ACHIEVEMENT("Достижения") {
        @Override
        protected String toHtml0(Section section) {
            ListSection value = (ListSection) section;
            StringBuilder sb = new StringBuilder().append(getTitle());
            for(String s: value.getDescriptionList()){
                sb.append(s).append("\n");
            }
            return sb.toString();
        }
    },
    QUALIFICATIONS("Квалификация") {

    },
    EXPERIENCE("Опыт работы"){
        @Override
        protected String toHtml0(Section section) {
            OrganizationsSection value = (OrganizationsSection) section;
            StringBuilder sb = new StringBuilder().append(getTitle()).append(": ");
            for(Organization s: value.getOrganizations()){
                sb.append(s).append("\n");
            }
            return sb.toString();
        }
    },
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

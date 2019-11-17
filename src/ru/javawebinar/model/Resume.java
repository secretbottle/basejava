package ru.javawebinar.model;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>{

    // Unique identifier
    private String uuid;


    public static void main (String[] args) {
    Resume r1 = new Resume();
    r1.setUuid("Стринги");

    Resume r2 = new Resume();
    r2.setUuid("Стринги1");

    System.out.println("ЪЪЪЪЪЪЪЪЪЙЙЙ " + r1.compareTo(r2));
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }

}

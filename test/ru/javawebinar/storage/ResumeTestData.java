package ru.javawebinar.storage;

import ru.javawebinar.model.*;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    private ResumeTestData() {
    }

    public static void main(String[] args) {
        printResume(generateResume("uuid1", "FULLNAME1"));

    }

    public static Resume generateResume(String uuid, String fullname) {
        Resume resume = new Resume(uuid, fullname);
        //Контакты
        resume.putContactMap(ContactType.PHONE, "+7(921) 855-0482");
        resume.putContactMap(ContactType.EMAIL, "skype:grigory.kislin" + generateRandomString(3));
        resume.putContactMap(ContactType.SKYPE, "gkislin@yandex.ru");
        resume.putContactMap(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin" + generateRandomString(3));
        resume.putContactMap(ContactType.GITHUB, "https://github.com/gkislin" + generateRandomString(3));
        resume.putContactMap(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473" + generateRandomString(3));
        resume.putContactMap(ContactType.HOMEPAGE, "http://gkislin.ru/");

        // Раздел "Позиция"
        TextSection Position = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям" + generateRandomString(10));
        resume.putSectionMap(SectionType.OBJECTIVE, Position);

        // Раздел "Личные качества"
        TextSection personalDesc = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры." + generateRandomString(10));
        resume.putSectionMap(SectionType.PERSONAL, personalDesc);

        // Раздел "Достижения"
        List<String> listAchievements = new ArrayList<>();
        listAchievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников." + generateRandomString(10));
        listAchievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk." + generateRandomString(20));
        listAchievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера." + generateRandomString(20));

        ListSection achievements = new ListSection(listAchievements);
        resume.putSectionMap(SectionType.ACHIEVEMENT, achievements);

        // Раздел "Квалификация"
        List<String> listQualification = new ArrayList<>();
        listQualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 " + generateRandomString(20));
        listQualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce, " + generateRandomString(20));
        listQualification.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle," + generateRandomString(20));

        ListSection qualification = new ListSection(listQualification);
        resume.putSectionMap(SectionType.QUALIFICATIONS, qualification);

        // Раздел "Опыт работы"
        List <Organization> organizationExpList = new ArrayList<>();
        // Первая работа
        Organization.Position javaops = new Organization.Position(createRandomDate(1999, 2019), LocalDate.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        Link javaopsLink = new Link(generateRandomString(20), "http://javaops.ru/");
        List<Organization.Position> firstJob = new ArrayList<>();
        firstJob.add(javaops);
        Organization firstOrganization = new Organization(javaopsLink, firstJob);
        organizationExpList.add(firstOrganization);
        // Вторая работа
        Organization.Position wrike = new Organization.Position(createRandomDate(1999, 2019), LocalDate.of(2016, 1, 1), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        Link wrikeLink = new Link(generateRandomString(20), "https://www.wrike.com/");
        List<Organization.Position> secondJob = new ArrayList<>();
        secondJob.add(wrike);
        Organization secondOrganization = new Organization(wrikeLink, secondJob);
        organizationExpList.add(secondOrganization);

        OrganizationsSection experienceSection = new OrganizationsSection(organizationExpList);

        resume.putSectionMap(SectionType.EXPERIENCE, experienceSection);

        // Раздел "Образование
        List <Organization> educationExpList = new ArrayList<>();
        // Первое место
        Organization.Position Coursera = new Organization.Position(createRandomDate(1999, 2019), createRandomDate(1999, 2019), "\"Functional Programming Principles in Scala\" by Martin Odersky", "");
        Link CourseraLink = new Link(generateRandomString(20), "https://www.coursera.org/course/progfun");
        List<Organization.Position> firstEdu = new ArrayList<>();
        firstEdu.add(Coursera);
        Organization firstEducation = new Organization(CourseraLink, firstEdu);

        educationExpList.add(firstEducation);
        // Второе место
        Organization.Position itmo1 = new Organization.Position(createRandomDate(1999, 2019), createRandomDate(1999, 2019), "Аспирантура (программист С, С++)", "");
        Organization.Position itmo2 = new Organization.Position(createRandomDate(1999, 2019), createRandomDate(1999, 2019), "Инженер (программист Fortran, C)", "");
        Link itmoLink = new Link(generateRandomString(20), "http://www.ifmo.ru/");
        List<Organization.Position> secondEdu = new ArrayList<>();
        secondEdu.add(itmo1);
        secondEdu.add(itmo2);
        Organization secondEducation = new Organization(itmoLink, secondEdu);

        educationExpList.add(secondEducation);

        OrganizationsSection education = new OrganizationsSection(educationExpList);

        resume.putSectionMap(SectionType.EDUCATION, education);

        return resume;
    }

    //Random generator for String
    private static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder strB = new StringBuilder(length);
        for (int i = 0; i < length; i++) {

            int rndCharAt = random.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMN0123456789".length());
            char rndChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMN0123456789".charAt(rndCharAt);

            strB.append(rndChar);

        }
        return strB.toString();
    }

    //Random Generator for LocalDates
    private static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    //Random Generator for LocalDates
    private static LocalDate createRandomDate(int startYear, int endYear) {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(startYear, endYear);
        return LocalDate.of(year, month, day);
    }

    private static void printResume(Resume resume) {
        Map<ContactType, String> contacts = resume.getContactMap();
        Map<SectionType, Section> sections = resume.getSectionMap();

        //Выводим результат
        System.out.println(resume.getFullName());

        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + " : " + contacts.get(type).toString());
        }

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + " : " + sections.get(type).toString());
        }
    }

}

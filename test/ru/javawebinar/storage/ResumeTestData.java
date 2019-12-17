package ru.javawebinar.storage;

import ru.javawebinar.model.SectionType;
import ru.javawebinar.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume RESUME = new Resume("Григорий Кислин");

        // Раздел "Позиция"
        TextSection position = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        // Раздел "Личные качества"
        TextSection personalDesc = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        // Раздел "Достижения"
        List<String> listAchievements = new ArrayList<>();
        listAchievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        listAchievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        listAchievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");

        ListSection achievements = new ListSection(listAchievements);

        // Раздел "Квалификация"
        List<String> listQualification = new ArrayList<>();
        listQualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        listQualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        listQualification.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");

        ListSection qualification = new ListSection(listQualification);

        // Раздел "Опыт работы"
        PeriodDescription javaops = new PeriodDescription(LocalDate.of(2013, 10, 1), LocalDate.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        Link javaopsLink = new Link("Java Online Projects", "http://javaops.ru/");
        List<PeriodDescription> firstJob = new ArrayList<>();
        firstJob.add(javaops);

        PeriodDescription wrike = new PeriodDescription(LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        Link wrikeLink = new Link("Wrike", "https://www.wrike.com/");
        List<PeriodDescription> secondJob = new ArrayList<>();
        secondJob.add(wrike);

        Map<Link, List<PeriodDescription>> experienceMap = new HashMap<>();
        experienceMap.put(javaopsLink, firstJob);
        experienceMap.put(wrikeLink, secondJob);


        PeriodSection experience = new PeriodSection(experienceMap);

        // Раздел "Образование
        PeriodDescription Coursera = new PeriodDescription(LocalDate.of(2013, 3, 1), LocalDate.of(2016, 5, 1), "", "\"Functional Programming Principles in Scala\" by Martin Odersky");
        Link CourseraLink = new Link("Coursera", "https://www.coursera.org/course/progfun");
        List<PeriodDescription> firstEdu = new ArrayList<>();
        firstEdu.add(Coursera);

        PeriodDescription itmo1 = new PeriodDescription(LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1), "", "Аспирантура (программист С, С++)");
        PeriodDescription itmo2 = new PeriodDescription(LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1), "", "Инженер (программист Fortran, C)");
        Link itmoLink = new Link("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/");
        List<PeriodDescription> secondEdu = new ArrayList<>();
        secondEdu.add(itmo1);
        secondEdu.add(itmo2);

        Map<Link, List<PeriodDescription>> educationMap = new HashMap<>();
        educationMap.put(CourseraLink, firstEdu);
        educationMap.put(itmoLink, secondEdu);

        PeriodSection education = new PeriodSection(educationMap);

        //Собираем в мапах
        Map<ContactType, String> contacts = RESUME.getContactMap();
        contacts.put(ContactType.PHONE,"+7(921) 855-0482");
        contacts.put(ContactType.EMAIL,"skype:grigory.kislin");
        contacts.put(ContactType.SKYPE,"gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN,"https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB,"https://github.com/gkislin");
        contacts.put(ContactType.STACKOVERFLOW,"https://stackoverflow.com/users/548473");
        contacts.put(ContactType.HOMEPAGE,"http://gkislin.ru/");

        Map<SectionType, Section> sections = RESUME.getSectionMap();
        sections.put(SectionType.OBJECTIVE, position);
        sections.put(SectionType.PERSONAL,personalDesc);
        sections.put(SectionType.ACHIEVEMENT,achievements);
        sections.put(SectionType.QUALIFICATIONS,qualification);
        sections.put(SectionType.EXPERIENCE,experience);
        sections.put(SectionType.EDUCATION,education);

        //Выводим результат
        System.out.println(RESUME.getFullName());

        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + " : " + contacts.get(type).toString());
        }

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + " : " + sections.get(type).toString());
        }


    }
}

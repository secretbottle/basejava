package ru.javawebinar.storage.util;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.model.*;
import ru.javawebinar.storage.ResumeTestData;
import ru.javawebinar.util.JsonParser;

public class JsonParserTest {
    private Resume RESUME_1 = ResumeTestData.generateResume("UUID_1", "FULLNAME_1");

    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() throws Exception {
        Section section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assert.assertEquals(section1, section2);
    }
}

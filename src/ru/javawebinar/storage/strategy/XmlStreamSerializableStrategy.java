package ru.javawebinar.storage.strategy;

import ru.javawebinar.model.*;
import ru.javawebinar.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializableStrategy implements SerializableStrategy{
    private XmlParser xmlParser;

    public XmlStreamSerializableStrategy() {
        xmlParser = new XmlParser(Resume.class, Organization.class, Link.class,
                OrganizationsSection.class, TextSection.class, ListSection.class, Organization.Position.class);
    }


    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}

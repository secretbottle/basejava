package ru.javawebinar.storage.serial;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataStreamSerializer implements SerializableStrategy {
    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContactMap();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = resume.getSectionMap();
            //dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                Section sectionValue = entry.getValue();

                if (entry.getValue() instanceof TextSection) {
                    dos.writeUTF(((TextSection) sectionValue).getText());
                    continue;
                }

                if (sectionValue instanceof ListSection) {
                    List<String> descriptionList = ((ListSection) sectionValue).getDescriptionList();
                    dos.writeInt(descriptionList.size());

                    for (String s : descriptionList) {
                        dos.writeUTF(s);
                    }
                    continue;
                }

                if (sectionValue instanceof OrganizationsSection) {
                    List<Organization> descriptionList = ((OrganizationsSection) sectionValue).getOrganizations();
                    dos.writeInt(descriptionList.size());

                    for (Organization org : descriptionList) {
                        dos.writeUTF(org.getLink().getTitle());
                        dos.writeUTF(org.getLink().getUrlAdr());

                        List<Organization.Position> orgValue = org.getPositions();
                        dos.writeInt(orgValue.size());

                        for (Organization.Position pos : orgValue) {
                            dos.writeUTF(pos.getStartPeriod().toString());
                            dos.writeUTF(pos.getEndPeriod().toString());
                            dos.writeUTF(pos.getPosition());
                            if (pos.getDescription() == null) {
                                dos.writeUTF("");
                            } else {
                                dos.writeUTF(pos.getDescription());
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            throw new StorageException("lmao", "lmao");
        }
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.putContactMap(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }


            resume.putSectionMap(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));
            resume.putSectionMap(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));

            List<String> descriptionList1 = Stream.of(dis.readUTF()).limit(dis.readInt()).collect(Collectors.toList());
            resume.putSectionMap(SectionType.valueOf(dis.readUTF()),
                    new ListSection(descriptionList1));

            List<String> descriptionList2 = Stream.of(dis.readUTF()).limit(dis.readInt()).collect(Collectors.toList());
            resume.putSectionMap(SectionType.valueOf(dis.readUTF()),
                    new ListSection(descriptionList2));


            resume.putSectionMap(SectionType.valueOf(dis.readUTF()), new OrganizationsSection(dis.readUTF()));
            resume.putSectionMap(SectionType.valueOf(dis.readUTF()), new OrganizationsSection(dis.readUTF()));


            List<Organization> organizations;
            Link link;
            List<Organization.Position> organizationsPos;
            Organization.Position orgPos;


            resume.putSectionMap(SectionType.valueOf(dis.readUTF()), dis.readUTF());


            return resume;
        }
    }

    private <T> List readToResume(DataInputStream dis, Resume resume){
        List<T> description = new ArrayList<>();
        return description;
    }


}

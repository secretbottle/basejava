package ru.javawebinar.storage.serial;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.*;

import java.io.*;
import java.time.LocalDate;
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
            System.out.println(sections.size());
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                Section sectionValue = entry.getValue();

                if (entry.getValue() instanceof TextSection) {
                    dos.writeUTF(((TextSection) sectionValue).getText());
                    System.out.println(((TextSection) sectionValue).getText());
                    continue;
                }

                if (sectionValue instanceof ListSection) {
                    List<String> descriptionList = ((ListSection) sectionValue).getDescriptionList();
                    dos.writeInt(descriptionList.size());

                    System.out.println(((ListSection) sectionValue).getDescriptionList());

                    for (String s : descriptionList) {
                        dos.writeUTF(s);
                    }
                    continue;
                }

                if (sectionValue instanceof OrganizationsSection) {
                    List<Organization> descriptionList = ((OrganizationsSection) sectionValue).getOrganizations();
                    dos.writeInt(descriptionList.size());


                    System.out.println(((OrganizationsSection) sectionValue).getOrganizations());

                    for (Organization org : descriptionList) {
                        dos.writeUTF(org.getLink().getTitle());
                        dos.writeUTF(org.getLink().getUrlAdr());

                        List<Organization.Position> orgValue = org.getPositions();
                        dos.writeInt(orgValue.size());

                        for (Organization.Position pos : orgValue) {
                            dos.writeUTF(pos.getStartPeriod().toString());
                            dos.writeUTF(pos.getEndPeriod().toString());
                            dos.writeUTF(pos.getPosition());
                            dos.writeUTF(pos.getDescription());
                        }
                    }
                }
            }

        } catch (IOException e) {
            throw new StorageException("IOError while writing in file", e);
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

            if(dis.readInt() > 0) {
                resume.putSectionMap(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));
                resume.putSectionMap(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));

                resume.putSectionMap(SectionType.valueOf(dis.readUTF()),
                        new ListSection(
                                Stream.of(dis.readUTF()).limit(dis.readInt()).collect(Collectors.toList())));

                resume.putSectionMap(SectionType.valueOf(dis.readUTF()),
                        new ListSection(
                                Stream.of(dis.readUTF()).limit(dis.readInt()).collect(Collectors.toList())));


                resume.putSectionMap(SectionType.valueOf(dis.readUTF()), new OrganizationsSection(
                                Stream.of(new Organization(
                                                new Link(dis.readUTF(), dis.readUTF()),
                                                Stream.of(new Organization.Position(
                                                                LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()
                                                        )
                                                ).limit(dis.readInt()).collect(Collectors.toList())
                                        )
                                ).limit(dis.readInt()).collect(Collectors.toList())
                        )
                );

            }
            return resume;
        } catch (IOException e) {
            throw new StorageException("Error while reading resume", e);
        }

    }

    private void writer (){

    }

    private void reader (){

    }

    private <T> List readToList(DataInputStream dis, int size) {
        try {
            return Stream.of(dis.readUTF()).limit(size).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("IOError in read operation ",e);
        }

    }


}

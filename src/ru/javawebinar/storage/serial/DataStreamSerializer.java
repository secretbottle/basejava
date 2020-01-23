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
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType sectionType =entry.getKey();
                Section sectionValue = entry.getValue();
                
                dos.writeUTF(sectionType.name());


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

            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());

                if (sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)) {
                    resume.putSectionMap(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));
                    continue;
                }

                if (sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATIONS)) {
                    resume.putSectionMap(SectionType.valueOf(dis.readUTF()),
                            new ListSection(readtoList(dis.readInt(), dis.readUTF())));
                    continue;
                }

                if (sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION))
                    resume.putSectionMap(SectionType.valueOf(dis.readUTF()), new OrganizationsSection(
                                    readtoList(dis.readInt(), new Organization(
                                                    new Link(dis.readUTF(), dis.readUTF()),
                                                    readtoList(dis.readInt(), new Organization.Position(
                                                                    LocalDate.parse(dis.readUTF()),
                                                                    LocalDate.parse(dis.readUTF()),
                                                                    dis.readUTF(),
                                                                    dis.readUTF()
                                                            )
                                                    )
                                            )
                                    )
                            )
                    );
            }

            return resume;
        } catch (IOException e) {
            throw new StorageException("Error while reading resume", e);
        }

    }

    private <T> void writer(DataOutputStream dos, List<T> list) {
        try {
            dos.writeInt(list.size());
            for(T t: list){

            }


        } catch (IOException e) {
            throw new StorageException("IOError while writing in file", e);
        }
    }

    private void reader(DataInputStream dis) {
        try {
            int size = dis.readInt();

            for (int i = 0; i < size; i++) {

            }


        } catch (IOException e) {
            throw new StorageException("IOError in read operation ", e);
        }
    }

    private <T> List<T> readtoList(int size, T t) {
        return Stream.of(t).limit(size).collect(Collectors.toList());
    }


}

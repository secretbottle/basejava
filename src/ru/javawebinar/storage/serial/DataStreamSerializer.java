package ru.javawebinar.storage.serial;

import ru.javawebinar.model.*;
import ru.javawebinar.storage.serial.functional.FunctionThrowing;
import ru.javawebinar.storage.serial.functional.SupplierThrowing;
import ru.javawebinar.storage.serial.functional.ConsumerThrowing;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializableStrategy {
    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContactMap();
            writer(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = resume.getSectionMap();
            writer(dos, sections.entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                Section sectionValue = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) sectionValue).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writer(dos, ((ListSection) sectionValue).getDescriptionList(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writer(dos, ((OrganizationsSection) sectionValue).getOrganizations(), org -> {
                                    dos.writeUTF(org.getLink().getTitle());
                                    dos.writeUTF(org.getLink().getUrlAdr());

                                    writer(dos, org.getPositions(), (pos) -> {
                                                dos.writeUTF(pos.getStartPeriod().toString());
                                                dos.writeUTF(pos.getEndPeriod().toString());
                                                dos.writeUTF(pos.getPosition());
                                                dos.writeUTF(pos.getDescription());
                                            }
                                    );
                                }
                        );
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            reader(dis, () -> {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });

            reader(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.setSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        readToList(dis, dis::readUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.setSection(sectionType, new OrganizationsSection(
                                        readToList(dis, () -> new Organization(
                                                        new Link(dis.readUTF(), dis.readUTF()),
                                                        readToList(dis, () -> new Organization.Position(
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
                        break;
                }
            });
            return resume;
        }
    }

    private <E> void writer(DataOutputStream dos, Collection<E> collection, ConsumerThrowing<E> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (E e : collection) {
            consumer.accept(e);
        }
    }

    private void reader(DataInputStream dis, FunctionThrowing function) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            function.accept();
        }
    }

    private <T> List<T> readToList(DataInputStream dis, SupplierThrowing<T> supplier) throws IOException {
        List<T> descriptionList = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            descriptionList.add(supplier.get());
        }
        return descriptionList;
    }
}

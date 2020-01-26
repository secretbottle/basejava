package ru.javawebinar.storage.serial;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
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
                SectionType sectionType = entry.getKey();
                Section sectionValue = entry.getValue();

                dos.writeUTF(sectionType.name());
                if (sectionValue instanceof TextSection) {
                    dos.writeUTF(((TextSection) sectionValue).getText());
                    continue;
                }

                if (sectionValue instanceof ListSection) {
                    writer(dos, ((ListSection) sectionValue).getDescriptionList(), s -> {
                        try {
                            dos.writeUTF(s);
                        } catch (IOException e) {
                            throw new StorageException("IOError while writing in file", e);
                        }

                    });
                    continue;
                }

                if (sectionValue instanceof OrganizationsSection) {
                    writer(dos, ((OrganizationsSection) sectionValue).getOrganizations(), org -> {
                                try {
                                    dos.writeUTF(org.getLink().getTitle());
                                    dos.writeUTF(org.getLink().getUrlAdr());
                                } catch (IOException e) {
                                    throw new StorageException("IOError while writing in file", e);
                                }

                                writer(dos, org.getPositions(), (pos) -> {
                                            try {
                                                dos.writeUTF(pos.getStartPeriod().toString());
                                                dos.writeUTF(pos.getEndPeriod().toString());
                                                dos.writeUTF(pos.getPosition());
                                                dos.writeUTF(pos.getDescription());
                                            } catch (IOException e) {
                                                throw new StorageException("IOError while writing in file", e);
                                            }
                                        }
                                );
                            }
                    );
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
                    resume.putSectionMap(sectionType, new TextSection(dis.readUTF()));
                    continue;
                }

                if (sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATIONS)) {
                    readToList(dis, () -> {
                        try {
                            dis.readUTF();
                        } catch (IOException e) {
                            throw new StorageException("IOError while writing in file", e);
                        }
                        return null;
                    });
                    continue;
                }


                if (sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)) {
                    resume.putSectionMap(sectionType, new OrganizationsSection(
                                    readToList(dis, () -> {
                                                try {
                                                    new Organization(
                                                            new Link(dis.readUTF(), dis.readUTF()),
                                                            readToList(dis, () -> {
                                                                        try {
                                                                            new Organization.Position(
                                                                                    LocalDate.parse(dis.readUTF()),
                                                                                    LocalDate.parse(dis.readUTF()),
                                                                                    dis.readUTF(),
                                                                                    dis.readUTF()
                                                                            );
                                                                        } catch (IOException e) {
                                                                            throw new StorageException("IOError while reading from file", e);
                                                                        }
                                                                        return null;
                                                                    }
                                                            )
                                                    );
                                                } catch (IOException e) {
                                                    throw new StorageException("IOError while reading from file", e);
                                                }
                                                return null;
                                            }
                                    )
                            )
                    );
                }


            }

            return resume;
        } catch (IOException e) {
            throw new StorageException("Error while reading resume", e);
        }

    }

    private <T> void writer(DataOutputStream dos, List<T> list, Consumer<T> consumer){
        try {
            dos.writeInt(list.size());
            for (T t : list) {
                consumer.accept(t);
                /*
                consumer = x -> {
                utfWriter(dos, (String) x);
                 };
                */
            }
        } catch (IOException e) {
            throw new StorageException("IOError while writing in file", e);
        }


    }

    private <T> void utfWriter(DataOutputStream dos, String s) {
        try {
            dos.writeUTF(s);
        } catch (IOException e) {
            throw new StorageException("IOError while writing in file", e);
        }
    }

    private <T> List<T> readToList(DataInputStream dis, Supplier<T> supplier) throws IOException {
        List<T> descriptionList = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            descriptionList.add(supplier.get());
        }
        return descriptionList;
    }

    private <T> List<T> readToList(DataInputStream dis, T t) {
        try {
            int size = dis.readInt();
            //return Stream.generate(supplier).limit(size).collect(Collectors.toList());
            return Stream.of(t).limit(size).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

package ru.javawebinar.storage.serial;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.*;

import java.io.*;
import java.util.List;
import java.util.Map;
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


            Stream<Map.Entry<SectionType, Section>> sectionss = resume.getSectionMap().entrySet().stream();
            sectionss.filter(x->x.getKey().equals(SectionType.OBJECTIVE))
                    .map(Map.Entry::getValue)
                    .forEach(x -> dos.writeInt(x))                    .



            Map<SectionType, Section> sections = resume.getSectionMap();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {

                String sectionName = entry.getKey().name();
                if (sectionName.equals(SectionType.OBJECTIVE.name()) ||
                        sectionName.equals(SectionType.PERSONAL.name())) {
                    dos.writeUTF(sectionName);
                    dos.writeUTF(entry.getValue().getText());
                    continue;
                }

                if (sectionName.equals(SectionType.ACHIEVEMENT.name()) ||
                        sectionName.equals(SectionType.QUALIFICATIONS.name())) {
                    dos.writeUTF(sectionName);


                    List<String> descriptionList = (ListSection) entry.getValue();
                    dos.writeInt(descriptionList.size());

                    for (String s : descriptionList) {
                        dos.writeUTF(s);
                    }
                    descriptionList.forEach(x -> {
                        try {
                            dos.writeUTF(x);
                        } catch (IOException e) {
                            throw new StorageException("lmao", "lmao");
                        }
                    });
                }


                if (sectionName.equals(SectionType.EXPERIENCE.name()) ||
                            sectionName.equals(SectionType.EDUCATION.name())) {

                }



                }

            } catch(IOException e){
                throw new StorageException("lmao", "lmao");
            }
        }

        @Override
        public Resume doRead (InputStream is) throws IOException {
            try (DataInputStream dis = new DataInputStream(is)) {
                String uuid = dis.readUTF();
                String fullName = dis.readUTF();
                Resume resume = new Resume(uuid, fullName);
                int size = dis.readInt();
                for (int i = 0; i < size; i++) {
                    resume.putContactMap(ContactType.valueOf(dis.readUTF()), dis.readUTF());
                }
                return resume;
            }
        }
    }

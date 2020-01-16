package ru.javawebinar.storage.serial;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.*;

import java.io.*;
import java.util.List;
import java.util.Map;

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

/*

            Stream<Map.Entry<SectionType, Section>> sectionss = resume.getSectionMap().entrySet().stream();
            sectionss.filter(x->x.getKey().equals(SectionType.OBJECTIVE))
                    .map(Map.Entry::getValue)
                    .forEach(x -> dos.writeInt(x));
*/
            Section sec = new Section(){
                public <T extends Section> void forStreamList(List<T> listT){
                    try {
                        dos.writeInt(listT.size());
                        for (T t : listT) {
                            dos.writeUTF(t.toString());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };



            Map<SectionType, Section> sections = resume.getSectionMap();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {

                String sectionName = entry.getKey().name();
                if (sectionName.equals(SectionType.OBJECTIVE.name()) ||
                        sectionName.equals(SectionType.PERSONAL.name())) {
                    if (entry.getValue() instanceof TextSection) {
                        System.out.println("This is TextSection!!");
                        dos.writeUTF(sectionName);
                        dos.writeUTF(((TextSection) entry.getValue()).getText());
                    }

                    continue;
                }

                if (sectionName.equals(SectionType.ACHIEVEMENT.name()) ||
                        sectionName.equals(SectionType.QUALIFICATIONS.name())) {
                    dos.writeUTF(sectionName);
                    if (entry.getValue() instanceof ListSection) {
                        System.out.println("This is ListSection!!");
                        List<String> descriptionList = ((ListSection) entry.getValue()).getDescriptionList();
                        dos.writeInt(descriptionList.size());
                        for (String s : descriptionList) {
                            dos.writeUTF(s);
                        }
                    }

                }


                if (sectionName.equals(SectionType.EXPERIENCE.name()) ||
                            sectionName.equals(SectionType.EDUCATION.name())) {
//                    dos.writeUTF(sectionName);
//                    List<Organization> descriptionList = (List<Organization>) entry.getValue();
//                    dos.writeInt(descriptionList.size());
//                    for(Organization org : descriptionList){
//                        Map<Link, List<Organization.Position>> organizationMap = (Map<Link, List<Organization.Position>>) org;
//
//                        for(Map.Entry<Link, List<Organization.Position>> entryOrganization : organizationMap.entrySet()){
//
//                            List <Organization.Position> sectionList = entryOrganization.getValue();
//
//                            for(Organization.Position pos : sectionList){
//                                dos.writeUTF(pos.getStartPeriod().toString());
//                                dos.writeUTF(pos.getEndPeriod().toString());
//                                dos.writeUTF(pos.getPosition());
//                                dos.writeUTF(pos.getDescription());
//                            }
//
//                        }
//
//                    }
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


    public <T> void forStreamList(DataOutputStream dos, List<T> listT){
        try {
            dos.writeInt(listT.size());


                for (T t : listT) {

                    if(t instanceof List){
                        forStreamList(dos, (List<T>) t);
                    }

                    if(t instanceof Map){
                        forStreamMap(dos, t);
                    }

                    dos.writeUTF(t.toString());
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public <K,V,T> void forStreamMap(DataOutputStream dos, T inputData){


        if (inputData instanceof List ){
            //List<T>
        }

        if (inputData instanceof Map ){
            Map<K, V> organizationMap = (Map<K, V>) inputData;

            for(Map.Entry<K,V> entry: organizationMap.entrySet()){

                V value = entry.getValue();

                if(value instanceof List){
                    //forStreamList(dos, (List<T>) t);
                }

                if(value instanceof Map){
                    forStreamMap(dos, value);
                }
            }
        }




    }


        private static class Noumenon {


            public static class Builder{
                final DataOutputStream dos;

                public Builder(DataOutputStream dos) {
                    this.dos = dos;
                }

                public <T> Builder forStreamList(List<T> listT){
                    try {
                        dos.writeInt(listT.size());

                        for (T t : listT) {

                            dos.writeUTF(t.toString());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return this;
                }



                public <K,V> Builder forStreamMap(Map <K,V> mapKV){

                    return this;
                }

            }

        }

    }

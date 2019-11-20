package ru.javawebinar;

import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.SortedArrayStorage;

/**
 * Test for your ru.javawebinar.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    //private static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();
    private final static SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        //test ArrayStorage.update()
        r2.setUuid("uuid45");
        ARRAY_STORAGE.update(r2);
        System.out.println("\nUpdate r2: " + ARRAY_STORAGE.get(r2.getUuid()));
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());

        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}

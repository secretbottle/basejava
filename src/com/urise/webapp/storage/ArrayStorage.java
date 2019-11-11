package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(resume.toString())) {
                System.out.println("Ошибка: save Объект существует.");
                return;
            }
        }

        if (size < storage.length) {
            storage[size++] = resume;
        } else {
            System.out.println("Ошибка: Массив забит.");
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("Ошибка: Объект '" + uuid + "' не существует в массиве");
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                return;
            }
        }

        System.out.println("Ошибка: delete Объект не существует в массиве");
    }
    //TODO протеститить update
    public void update(Resume resumeOld, Resume resumeNew) {
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(resumeOld)) {
                System.out.println("Ошибка: Объект существует.");
            } else {
                storage[i] = resumeNew;
            }
        }
    }
    //TODO оптимизировать код
    private void optimCode(){

    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }


}

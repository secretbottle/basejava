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
        if (getArrayNum(resume.toString()) != -1) {
            System.out.println("Ошибка: Объект существует.");
            return;
        }

        if (size < storage.length) {
            storage[size++] = resume;
        } else {
            System.out.println("Ошибка: Массив забит.");
        }
    }

    public Resume get(String uuid) {
        int i = getArrayNum(uuid);

        if (i == -1) {
            System.out.println("Ошибка: Объект '" + uuid + "' не существует в массиве");
        } else {
            return storage[i];
        }

        return null;
    }

    public void delete(String uuid) {
        int i = getArrayNum(uuid);

        if (i == -1) {
            System.out.println("Ошибка: Объект '" + uuid + "' не существует в массиве");
        } else {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    public void update(String resumeOld, Resume resumeNew) {
        if (resumeNew.toString().equals(resumeOld)) {
            System.out.println("Ошибка: Объекты совпадают.");
            return;
        }

        int i = getArrayNum(resumeOld);
        if (i == -1) {
            System.out.println("Ошибка: Объект '" + resumeNew.toString() + "' не существует в массиве");
        } else {
            storage[i] = resumeNew;
        }
    }

    private int getArrayNum(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

}

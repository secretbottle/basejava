package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (getIndex(resume.toString()) != -1) {
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
        int index = getIndex(uuid);

        if (index == -1) {
            System.out.println("Ошибка: Объект '" + uuid + "' не существует в массиве");
        } else {
            return storage[index];
        }

        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index == -1) {
            System.out.println("Ошибка: Объект '" + uuid + "' не существует в массиве");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index== -1) {
            System.out.println("Ошибка: Объект '" + resume + "' не существует в массиве");
        } else {
            storage[index] = resume;
        }
    }

    private int getIndex(String uuid) {
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

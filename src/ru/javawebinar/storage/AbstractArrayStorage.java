package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index < 0) {
            System.out.println("Ошибка: Объект '" + resume.getUuid() + "' не существует в массиве");
        } else {
            storage[index] = resume;
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("Ошибка: Объект '" + resume.getUuid() + "' существует.");
            return;
        }

        if (size < storage.length) {
            add(resume, index);
            size++;
        } else {
            System.out.println("Ошибка: Массив забит.");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.println("Ошибка: Объект '" + uuid + "' не существует");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.println("Ошибка: Объект '" + uuid + "' не существует в массиве");
        } else {
            remove(index);
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void add(Resume resume, int index);

    protected abstract void remove(int index);


}

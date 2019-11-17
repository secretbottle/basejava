package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index < 0) {
            System.out.println("Ошибка: Объект '" + resume + "' не существует в массиве");
        } else {
            storage[index] = resume;
        }

        sort(0, size - 1);
    }

    @Override
    public void save(Resume resume) {
        if (getIndex(resume.toString()) > 0) {
            System.out.println("Ошибка: Объект существует.");
            return;
        }

        if (size < storage.length) {
            storage[size++] = resume;
        } else {
            System.out.println("Ошибка: Массив забит.");
        }

        sort(0, size - 1);

    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.println("Ошибка: Объект '" + uuid + "' не существует");
            return null;
        }
        return storage[index];
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.println("Ошибка: Объект '" + uuid + "' не существует в массиве");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }

        sort(0, size - 1);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    private void sort(int leftBorder, int rightBorder){
        //QuickSort
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        Resume pivot = storage[(leftMarker + rightMarker) / 2];
        do {

            while (storage[leftMarker].compareTo(pivot) < 0) {
                leftMarker++;
            }

            while (storage[rightMarker].compareTo(pivot) > 0) {
                rightMarker--;
            }

            if (leftMarker <= rightMarker) {
                if (leftMarker < rightMarker) {
                    Resume tmp = storage[leftMarker];
                    storage[leftMarker] = storage[rightMarker];
                    storage[rightMarker] = tmp;
                }
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);

        if (leftMarker < rightBorder) {
            sort(leftMarker, rightBorder);
        }
        if (leftBorder < rightMarker) {
            sort(leftBorder, rightMarker);
        }
    }
}

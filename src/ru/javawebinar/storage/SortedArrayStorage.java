package ru.javawebinar.storage;

import ru.javawebinar.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void add(Resume resume) {
        storage[size++] = resume;
        sort(0, size -1);
    }

    @Override
    protected void refresh(Resume resume, int index) {
        storage[index] = resume;
        sort(0, size -1);
    }

    @Override
    protected void remove(int index) {
        storage[index] = storage[size - 1];
        storage[--size] = null;
        sort(0, size -1);
    }

    private void sort(int leftBorder, int rightBorder){
        //QuickSort
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        Resume pivot = storage[(leftMarker + rightMarker) / 2];
        System.out.println((leftMarker + rightMarker) / 2);
        System.out.println((leftMarker + rightMarker) >>> 1);
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

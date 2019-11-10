

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++){
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if(size < storage.length) {
            storage[size++] = r;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }

        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                size--;
                for (int j = i; j < size; j++) {
                    storage[j] = storage[j + 1];
                }
            }
        }
    }

    Resume[] getAll() {
        Resume[] resumeAll = new Resume[size];
        System.arraycopy(storage, 0, resumeAll, 0, size);
        return resumeAll;
    }

    int size() { return size; }


}

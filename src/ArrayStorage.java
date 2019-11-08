import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(this.storage, null);
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                return;
            }
        }
    }

    Resume get(String uuid) {
        Resume getResume = null;

        for (int i = 0; i < size(); i++) {
            if (storage[i].toString().equals(uuid)) {
                getResume = storage[i];
            }
        }

        return getResume;
    }

    void delete(String uuid) {
        int size = size();

        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                for (int j = i; j < size; j++) {
                    storage[j] = storage[j + 1];
                }
                return;
            }
        }
    }

    Resume[] getAll() {
        Resume[] resumeAll = new Resume[size()];
        if (size() >= 0) {
            System.arraycopy(storage, 0, resumeAll, 0, resumeAll.length);
        }

        return resumeAll;
    }

    int size() {
        int sizeNum = 0;

        for (Resume resume : storage) {
            if (resume == null) {
                return sizeNum;
            }
            sizeNum++;
        }

        return sizeNum;
    }

}

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < this.size; i++){
            storage[i] = null;
        }
        this.size = 0;
    }

    void save(Resume r) {
        storage[this.size++] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < this.size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return storage[i];
            }
        }

        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < this.size; i++) {
            if (storage[i].toString().equals(uuid)) {
                for (int j = i; j < this.size; j++) {
                    storage[j] = storage[j + 1];
                }
                this.size--;
                return;
            }
        }
    }

    Resume[] getAll() {
        Resume[] resumeAll = new Resume[this.size];
        System.arraycopy(storage, 0, resumeAll, 0, this.size);
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

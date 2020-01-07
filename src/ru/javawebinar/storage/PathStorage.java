package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;
import ru.javawebinar.strategy.SerializableStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private SerializableStrategy serializableStrategy;

    protected PathStorage(String dir, SerializableStrategy serializableStrategy) {
        directory = Paths.get(dir);
        this.serializableStrategy = serializableStrategy;
        Objects.requireNonNull(directory, " directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected void updateElement(Path path, Resume resume) {
        try {
            serializableStrategy.doWrite(new BufferedOutputStream(Files.newOutputStream(path)), resume);
        } catch (IOException e) {
            throw new StorageException("IOError at write operation: ", resume.getUuid(), e);
        }
    }

    @Override
    protected void saveElement(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("IOError Couldn't create Path " + path.toAbsolutePath(), path.getFileName().toString(), e);
        }
        updateElement(path, resume);
    }

    @Override
    protected Resume getElement(Path path) {
        try {
            return serializableStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IOError at read operation: ", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteElement(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("IOError at delete operation: ", path.getFileName().toString());
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected List<Resume> getAsList() {
        return getFileList().map(this::getElement).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFileList().forEach(x -> {
            try {
                Files.delete(x);
            } catch (IOException e) {
                throw new StorageException("IOError at clear operation: ", directory.toString(), e);
            }
        });
    }

    @Override
    public int size() {
        return (int) getFileList().count();
    }

    private Stream<Path> getFileList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("IOError while getting list of Files in directory: ", directory.toString());
        }
    }
}
